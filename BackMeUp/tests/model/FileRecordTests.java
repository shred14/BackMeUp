package model;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;





public class FileRecordTests {

	File correct_origin, bad_origin, destination = null;
	String origin_path= null;
	
	
	@Before
	public void setUp() throws Exception {
		origin_path = "file.txt";
		createFile(origin_path);
		
		correct_origin = new File("file.txt");
		bad_origin = new File( "non-existentFile.txt");
		destination = new File("fileBackup.txt");
	}

	@After
	public void tearDown() throws Exception {
		correct_origin.delete();
		destination.delete();
	}
	
	 @Rule
	 public ExpectedException exception_thrown = ExpectedException.none();

	
	@Test
	public void testConstructor(){
		new FileBackup(correct_origin, destination);
	}
	
	@Test
	public void testUpdate() throws IOException{
		FileBackup record = new FileBackup(correct_origin, destination);
		record.update();
		assertTrue( "A file should have been created", destination.exists());
		assertFilesAreEqual(correct_origin,destination);
	}
	
	

	private void createFile(String path) throws FileNotFoundException{
		PrintWriter writer = new PrintWriter(path);
		writer.println("I'm all about that bass");
		writer.close();
	}
	
	private boolean assertFilesAreEqual(File origin, File copy){
		boolean result = true;
		FileInputStream originalReader = null, copyReader = null;
		
		try{
			originalReader = new FileInputStream(origin.getPath());
			copyReader = new FileInputStream(copy.getPath());
			
			int originByte, copyByte = 0;
			
			while((originByte = originalReader.read()) != -1 || (copyByte = copyReader.read()) != -1){
				if(originByte != copyByte)
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



