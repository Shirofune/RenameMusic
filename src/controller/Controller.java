package controller;

import java.awt.EventQueue;
import java.io.File;

import negocio.RenameFiles;
import pantallas.Menu;

public class Controller {
	
	
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
	public static void invokeRename(String directory) {
		File directoryPath = new File(directory);
		File[] filesInDirectory = directoryPath.listFiles();
		
		int numberRenames = 0;
		for (File file : filesInDirectory) {
			// We check whether the file is a .mp3 or not
			if (file.getName().endsWith(".mp3")) {
				boolean resultRename = RenameFiles.TagsToFile(file);
				if (resultRename) numberRenames++;
			}
		}
				
		Menu.showResultsWindow(numberRenames);
		
		
	}

}
