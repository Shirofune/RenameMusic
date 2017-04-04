package negocio;

import java.io.File;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.blinkenlights.jid3.ID3Exception;
import org.blinkenlights.jid3.MP3File;
import org.blinkenlights.jid3.MediaFile;
import org.blinkenlights.jid3.v1.ID3V1Tag;
import org.blinkenlights.jid3.v2.ID3V2Tag;
import org.blinkenlights.jid3.v2.ID3V2_3_0Tag;

/**
 * Class that renames the files or the tags, depending of the kind of action the
 * user selected
 * 
 * @author d.veredas
 *
 */
public abstract class RenameFiles {

	private static Logger logger = Logger.getLogger(RenameFiles.class.getName());

	/**
	 * Tries to rename the file to the format "Artist - Title", getting those
	 * values from the ID3Tags of the file
	 * 
	 * @param musicFile
	 *            File to rename
	 * @return Result of the operation
	 */
	public static boolean TagsToName(File musicFile) {

		try {
			MediaFile mediaFile = new MP3File(musicFile);

			String artist = null;
			String title = null;

			// Lets check which kind of tags the File has and save the
			// information we need
			ID3V1Tag id3v1Tag = mediaFile.getID3V1Tag();
			if (id3v1Tag == null) {
				ID3V2Tag id3v2Tag = mediaFile.getID3V2Tag();
				if (id3v2Tag != null) {
					artist = id3v2Tag.getArtist();
					title = id3v2Tag.getTitle();
				} else {
					logger.log(Level.WARNING, MessageFormat.format("The file {0} has no tags", musicFile.getName()));
					return false;
				}
			} else {
				artist = id3v1Tag.getArtist();
				title = id3v1Tag.getTitle();
			}

			// Now, lets rename the file
			String finalName = MessageFormat.format("{0} - {1}.mp3", artist, title);
			File renamedFile = new File(musicFile.getParentFile() + "/" + finalName);
			musicFile.renameTo(renamedFile);

			return true;
		} catch (ID3Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			return false;
		}

	}

	/**
	 * Tries to update the tags of the file by getting the info from the
	 * filename
	 * 
	 * @param musicFile
	 *            File to rename
	 * @return Result of the operation
	 */
	public static boolean NameToTags(File musicFile) {
		try {
			String fileName = musicFile.getName();
			String[] split = fileName.split("\\-");
			if (split.length != 2) {
				logger.log(Level.WARNING, MessageFormat.format("The format of the filename {0} is not accepted by the application.", musicFile.getName()));
				return false;
			}
			
			String artist = split[0].trim();
			String title = split[1].split("\\.")[0].trim();
			
			// We check whether the file had previous tags or not. Depending on that, we update the info or we create new tags
			MediaFile mediaFile = new MP3File(musicFile);
			ID3V1Tag id3v1Tag = mediaFile.getID3V1Tag();
			if (id3v1Tag == null) {
				ID3V2Tag id3v2Tag = mediaFile.getID3V2Tag();
				if (id3v2Tag != null) {
					// The file has ID3V2 tags
					id3v2Tag.setArtist(artist);
					id3v2Tag.setTitle(title);
					mediaFile.setID3Tag(id3v2Tag);	
				} else {
					// The file doesn't have any tags
					 ID3V2_3_0Tag newTag = new ID3V2_3_0Tag();
					 newTag.setArtist(artist);
					 newTag.setTitle(title);
					 mediaFile.setID3Tag(newTag);				
				}
			} else {
				// The file has ID3V1 tags
				id3v1Tag.setArtist(artist);
				id3v1Tag.setTitle(title);
				mediaFile.setID3Tag(id3v1Tag);	
			}
			
			mediaFile.sync();
			return true;
			
		} catch (ID3Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			return false;
		}
	}

}
