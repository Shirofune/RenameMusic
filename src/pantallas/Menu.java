package pantallas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.MessageFormat;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controller.Controller;
import java.awt.Color;

public class Menu {

	private JFrame frmMusicRenamer;
	private JTextField directorySelectorTextField;
	private JButton btnRename;



	/**
	 * Create the application.
	 */
	public Menu() {
		
		initialize();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setFrame(new JFrame());
		getFrame().setBounds(100, 100, 450, 300);
		getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getFrame().getContentPane().setLayout(null);
		
		setDirectorySelectorTextField(new JTextField());
		getDirectorySelectorTextField().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fileChooser.showOpenDialog(getFrame());
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					File selectedDirectory = fileChooser.getSelectedFile();
					getDirectorySelectorTextField().setText(selectedDirectory.getAbsolutePath());
				}
			}
		});
		getDirectorySelectorTextField().setEditable(false);
		getDirectorySelectorTextField().setBounds(10, 53, 296, 20);
		getFrame().getContentPane().add(getDirectorySelectorTextField());
		getDirectorySelectorTextField().setColumns(10);
		
		JButton btnExplore = new JButton("Explore...");
		btnExplore.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fileChooser.showOpenDialog(getFrame());
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					File selectedDirectory = fileChooser.getSelectedFile();
					getDirectorySelectorTextField().setText(selectedDirectory.getAbsolutePath());
				}
			}
		});
		btnExplore.setBounds(316, 52, 89, 23);
		getFrame().getContentPane().add(btnExplore);
		{
			btnRename = new JButton("Rename!");
			btnRename.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (directorySelectorTextField.getText().isEmpty()) {
						JOptionPane.showMessageDialog(frmMusicRenamer, "A directory is necessary!");
					}
					else {
						Controller.invokeRename(directorySelectorTextField.getText());
					}
				}
			});
			btnRename.setBounds(181, 204, 107, 31);
			frmMusicRenamer.getContentPane().add(btnRename);
		}
	}

	public JFrame getFrame() {
		return frmMusicRenamer;
	}

	public void setFrame(JFrame frame) {
		this.frmMusicRenamer = frame;
		frmMusicRenamer.getContentPane().setBackground(Color.WHITE);
		frmMusicRenamer.setBackground(Color.WHITE);
		frmMusicRenamer.setTitle("Music Renamer");
		frame.setResizable(false);
	}

	public JTextField getDirectorySelectorTextField() {
		return directorySelectorTextField;
	}

	public void setDirectorySelectorTextField(JTextField directorySelectorTextField) {
		this.directorySelectorTextField = directorySelectorTextField;
	}
	
	public static void showResultsWindow(int numberOfRenames){
		String message;
		switch (numberOfRenames) {
			case 1: message = MessageFormat.format("{0} file was successfully renamed!", numberOfRenames); break;
			default: message = MessageFormat.format("{0} files were successfully renamed!", numberOfRenames); break;
			}
		JOptionPane.showMessageDialog(null, message);
	}
}
