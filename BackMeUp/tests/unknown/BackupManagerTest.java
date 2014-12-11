package unknown;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import model.Backup;
import model.HardDiskBackup;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BackupManagerTest {
	
	BackupManager manager;
	String origin, destination, badOrigin, badDestination;
	HardDiskBackup bk, uk;

	@Before
	public void setUp() throws Exception {
	  manager = BackupManager.getInstance();
		origin = "pap.txt";
		destination = "pop";
		
		badOrigin = "badMan.txt";
		badDestination = "death";
		
		
		Files.createFile(Paths.get(origin));
		bk = new HardDiskBackup(origin, destination);
		uk = new HardDiskBackup(badOrigin, badDestination);
	}

	@After
	public void tearDown() throws Exception {
	  new File(origin).delete();
	  new File(destination).delete();
	  
	  new File(badOrigin).delete();
    new File(badDestination).delete();
	  
	  new File(manager.PATH).delete();
	}
	
	@Test
	public void testAddBackup() throws IOException {
	  manager.addBackup(bk);
	  assertTrue("A backup should have been added", manager.countOfBackups() == 1);
	  manager.clear();
	}
	
	@Test
	public void testRemoveBackup() throws IOException {
	  boolean result = manager.removeBackup(uk);
	  assertFalse("removeBackup() should return false when not removed", result);
	  
	  manager.addBackup(bk);
	  result = manager.removeBackup(bk);
	  assertTrue("removeBackup() should return true when it is removed", result);
	}
	
	@Test
	public void testClearBackups() throws IOException {
	  manager.addBackup(bk);
	  manager.addBackup(uk);
	  assertTrue("backups should be two in this test", manager.countOfBackups() == 2);
	  
	  manager.clear();
	  assertTrue("clear() should remove all backups", manager.countOfBackups() == 0);
	}
	
	@Test
	public void testWriteData() throws IOException {
	  manager.clear();
		//test for a bad write first
		boolean result = manager.writeBackups();
		assertFalse("writeBackups() should retun false when it does not write", result);
		
		//test for a good write, and the file is created as expected
		manager.addBackup(bk);
		result = manager.writeBackups();
		assertTrue("writeBackups() should create a file", new File(manager.PATH).exists());
		assertTrue("writerBackups() should return true when successful", result);
		manager.clear();
	}

	@Test
	public void testReadBackups() throws IOException {
		//test for a bad read first
	  manager.clear();
	  new File(manager.PATH).delete();
	  
		boolean result = manager.readBackups();
		assertFalse("readBackups() should return false when there is nothing to read",result);
		
		//test for a good result
		manager.addBackup(bk);
		manager.writeBackups();
		result = manager.readBackups();
		assertTrue("readBackups() should return true when the data is read correctly", result);
	}

}
