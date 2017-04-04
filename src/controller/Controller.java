package controller;

import java.awt.EventQueue;
import java.io.File;

import negocio.RenameFiles;
import pantallas.Menu;

/**
 * Class in charge of handling the information flow between the views and the operations
 * @author d.veredas
 *
 */
public class Controller {
	
	public final static int RENAME_TAGS_TO_NAME = 0;
	public final static int RENAME_NAME_TO_TAGS = 1;
	
	
	/**
	 * Initializes the Menu view, from which the program is used
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu window = new Menu();
					window.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	/**
	 * Method called whenever the user presses "Rename!" in the window
	 * @param directory Selected directory
	 */
	public static void invokeRename(String directory, int typeOfRename) {
		File directoryPath = new File(directory);
		File[] filesInDirectory = directoryPath.listFiles();
		
		int numberRenames = 0;
		for (File file : filesInDirectory) {
			// We check whether the file is a .mp3 or not
			if (file.getName().endsWith(".mp3")) {
				
				// And now, depending of which kind of rename the user has selected...
				boolean resultRename = false;
				switch (typeOfRename) {
					case RENAME_TAGS_TO_NAME: resultRename = RenameFiles.TagsToName(file); break;
					case RENAME_NAME_TO_TAGS: resultRename = RenameFiles.NameToTags(file); break;
				}
				
				if (resultRename) numberRenames++;
			}
		}
				
		Menu.showResultsWindow(numberRenames);
		
		
	}

}
