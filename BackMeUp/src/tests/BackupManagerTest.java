package tests;

import static org.junit.Assert.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import model.BackupManager;
import model.BackupMetaManager;
import model.HDDBackup;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tests.helpers.TestHelper;
import static org.mockito.Mockito.*;


public class BackupManagerTest {
  
  private BackupManager _manager;
  private BackupMetaManager _metaManager;
  private String _metaFileLocation, _metaFileDirectory;
  private TestHelper _helper;
  
  @Before
  public void setUp() {
    _helper = new TestHelper();
    _manager = BackupManager.getInstance();
    _metaManager = BackupMetaManager.getInstance();
    _metaFileLocation = "data/bk.dat";
    _metaFileDirectory = "data";
  }

  @After
  public void tearDown() throws IOException {
  }
  
  @Test
  public void testGetInstance() {
    assertTrue("getInstance() should never return null", _manager != null);
    assertTrue("getInstance() should return only a single non-changing object",
        BackupManager.getInstance().equals(_manager));
  }

  @Test
  public void testUpdateBackups() {
    int countOfUpdated = 2;
    
    HDDBackup updateableBK = mock(HDDBackup.class);
    when(updateableBK.update()).thenReturn(true);
    
    HDDBackup currentBK = mock(HDDBackup.class);
    when(currentBK.update()).thenReturn(false);
    
    for(int i = 0; i < countOfUpdated; i++) {
      _metaManager.backups.add(updateableBK);
    }
    
    _metaManager.backups.add(currentBK);
    
    int result = _manager.updateBackups();
    assertTrue("updateBackups() should have returned\n" + countOfUpdated + "\n but returned \n " + result,
        result == countOfUpdated);
  }
  
}
