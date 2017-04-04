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

/**
 * Class that renames the files or the tags, depending of the kind of action the
 * user selected
 * 
 * @author d.veredas
 *
 */
public class RenameFiles {

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

			String author = null;
			String title = null;

			
			// Lets check which kind of tags the File has and save the information we need
			ID3V1Tag id3v1Tag = mediaFile.getID3V1Tag();
			if (id3v1Tag == null) {
				ID3V2Tag id3v2Tag = mediaFile.getID3V2Tag();
				if (id3v2Tag != null) {
					author = id3v2Tag.getArtist();
					title = id3v2Tag.getTitle();
				}
				else {
					logger.log(Level.WARNING, MessageFormat.format("The file {0} has no tags", musicFile.getName()));
					return false;
				}
			} else {
				author = id3v1Tag.getArtist();
				title = id3v1Tag.getTitle();
			}

			// Now, lets rename the file
			String finalName = MessageFormat.format("{0} - {1}.mp3", author, title);
			File renamedFile = new File(musicFile.getParentFile() + "/" + finalName);
			musicFile.renameTo(renamedFile);

			return true;
		} catch (ID3Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			return false;
		}

	}
	
	public static boolean NameToTags(File musicFile){
		throw new UnsupportedOperationException("This operation is not implemented yet!");
	}

}
