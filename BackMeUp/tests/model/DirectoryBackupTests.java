package model;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DirectoryBackupTests {

	Path correctOrigin, destination = null;
	String originPath, destinationPath = null;
	
	int NUM_OF_FILES = 3;
	
	@Before
	public void setUp() throws Exception {
		originPath = "files";
		createFiles(originPath,NUM_OF_FILES);
		
		correctOrigin = Paths.get(originPath);
		destinationPath = "fileBackup";
		destination = Paths.get(destinationPath);
	}

	@After
	public void tearDown() throws Exception {
		deleteFiles(originPath, NUM_OF_FILES);
		new File(originPath).delete();
		deleteFiles(destination.toString(), NUM_OF_FILES);
		new File(destinationPath).delete();
	}
	
	@Test
	public void testUpdate() throws IOException{
		Backup record = new Backup(correctOrigin, destination);
		record.update();
		assertTrue("A directory should have been created: " + destination.toString() , new File(destination.toString()).exists());
		
		for(int i = 0; i < NUM_OF_FILES; i++) {
			assertTrue("file" + i + " should have been copied", assertFilesAreEqual(
					new File(originPath + "/" + "file" + i + ".txt"),
					new File(destinationPath + "/file" + i + ".txt")));
		}
	}
	
	

	private void createFiles(String path, int n) throws IOException{
		Files.createDirectory(Paths.get(path));
		
		PrintWriter writer = null;
		
		for(int i = 0; i < n; i++){
			writer = new PrintWriter(path + "/file" + i + ".txt");
			writer.println("I'm all about that bass");
			writer.close();
		}
		
		Files.createDirectory(Paths.get(path + "/maFile"));
		writer = new PrintWriter(path + "/maFile/file.txt");
		writer.println("I'm all about that bass");
		writer.close();
	}
	
	private void deleteFiles(String path, int n) {
		
		for(int i = 0; i < n; i ++){
			new File(path + "/file" + i + ".txt").delete();
		}
		
		new File(path + "/maFile/file.txt").delete();
		new File(path + "/maFile").delete();
		
	}
	
	private boolean assertFilesAreEqual(File origin, File copy){
		boolean result = true;
		FileInputStream originalReader = null, copyReader = null;
		
		try{
			originalReader = new FileInputStream(origin.getPath());
			copyReader = new FileInputStream(copy.getPath());
			
			int originByte, copyByte = 0;
			
			while((originByte = originalReader.read()) != -1){
				copyByte = copyReader.read();
				
				if(originByte != copyByte || copyByte == -1)
					result = false;
			}
			
			originalReader.close();
			copyReader.close();
			
		} catch(IOException ex) {
			ex.printStackTrace();
			result = false;
		}
		
		return result;
	}

}
