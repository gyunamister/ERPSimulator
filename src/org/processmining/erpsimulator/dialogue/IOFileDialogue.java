package org.processmining.erpsimulator.dialogue;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


public class IOFileDialogue {
  /**
	 * 
	 */

	private File selectedFile = null;
	private JFileChooser fileChooser = new JFileChooser();  

	public IOFileDialogue() {
		
	}

	/**
	 * pop up a dialogue to import a file
	 * @param description 
	 * @param extension
	 * @param fileType indicate it is a file or directory
	 * @param dialogueName
	 * @param lastFile record the directory of the last selected file
	 */
	public void show(String description, String extension, int fileType, String dialogueName, File lastFile){
    
		fileChooser.setMultiSelectionEnabled(true);
		if(lastFile != null){
			System.out.println("last file is not empty");
			fileChooser.setCurrentDirectory(lastFile);
		}		
		else{
			System.out.println("last file is empty");
		}
		
		//add file filter
		FileFilter filter = new FileNameExtensionFilter(description, extension);
		fileChooser.addChoosableFileFilter(filter);
		fileChooser.setFileFilter(filter); // Initial filter setting						    
		fileChooser.setFileSelectionMode(fileType);  
//		fileChooser.setSelectedFile(new File("log.txt"));
		int result = fileChooser.showDialog(new JLabel(), dialogueName);  

		if (result == JFileChooser.APPROVE_OPTION) {
			System.out.println("Select was selected");
			selectedFile = fileChooser.getSelectedFile();		
		   
		} else if (result == JFileChooser.CANCEL_OPTION) {
		    System.out.println("Cancel was selected");
		    selectedFile = null;
		}
	}
	
	public File getSelectedFile(){
		return selectedFile;
	}
	
	public JFileChooser getFileChooser(){
		return fileChooser;
	}
}
