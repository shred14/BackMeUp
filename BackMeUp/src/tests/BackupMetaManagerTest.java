package tests;


import java.io.*;
import java.nio.file.*;
import model.*;
import org.json.simple.parser.ParseException;
import org.junit.*;
import tests.helpers.TestHelper;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


public class BackupMetaManagerTest
{
  private String _metaFileLocation = null;
  private String _metaFileDirectory = null;
  private BackupMetaManager _manager = null;
  private String JSONOrigin = "\"original\":\"origin\"";
  private String JSONLocation = "\"location\":\"destination\"";
  private TestHelper _helper;

  @Before
  public void setUp ()
  {
    _helper = new TestHelper();
    _manager = BackupMetaManager.getInstance();
    _metaFileLocation = "data/bk.dat";
    _metaFileDirectory = "data";
  }

  @After
  public void tearDown () throws IOException
  { 
    try
    {
      _helper.destroyMetaFile();
    }
    catch(IOException ex) {
      //do nothing
    }
  }

  @Test
  public void testPATH ()
  {
    assertTrue( "PATH should always be the specifed location",
                BackupMetaManager.PATH.equals(_metaFileLocation) );
  }

  @Test
  public void testGetInstance ()
  {
    assertTrue( "getInstance() should never return null", _manager != null );
    assertTrue( "getInstance() should return only single non-changing object",
                BackupMetaManager.getInstance().equals(_manager));
    assertTrue( "_backups should be intialized", _manager.backups != null );
  }

  @Test
  public void testSaveData () throws IOException
  {
    _manager = BackupMetaManager.getInstance();
    HDDBackup bk = mock( HDDBackup.class );
    when( bk.toJSON() ).thenReturn( JSONOrigin + "\n" + JSONLocation );
    _manager.backups.add( bk );
    _manager.backups.add( bk );
    _manager.saveData();
    assertTrue( "saveData() should create a meta file", new File( _metaFileLocation ).exists() );
    
    BufferedReader reader = new BufferedReader( new FileReader(_metaFileLocation) );
    
    String line = reader.readLine();
    assertTrue( "the file should contain information", line != null );
    
    while( line != null )
    {
      assertTrue( "first line written is wrong \n" + line + "\n should be \n" + JSONOrigin,
                  line.equals(JSONOrigin));
      line = reader.readLine();
      assertTrue( "second line written is wrong\n" + line + "\n should be \n" + JSONLocation,
                  line.equals(JSONLocation));
      line = reader.readLine();
    }
    reader.close();
  }

  @Test
  public void testReadData () throws IOException
  {
    int expectedCount = 3;
    
    _manager.backups.clear();
    try
    {
      assertFalse( "readData() should not read when directory does not exist",
                   _manager.readData());
    }
    catch ( ParseException e )
    {
      e.printStackTrace();
    }
    
    assertTrue( "readData() should not be populated with anything when no data is present",
                _manager.backups.size() == 0);
    
    _helper.createMetaFile( expectedCount );
    
    assertTrue( "directory exists", Files.exists( Paths.get( _metaFileDirectory ) ) );
    assertTrue( "file exists", Files.exists( Paths.get( _metaFileLocation ) ) );
    
    try
    {
      assertTrue( "readData() should read when a file is present", _manager.readData() );
    }
    catch ( ParseException e )
    {
      e.printStackTrace();
    }
    
    assertTrue( "readData() should have populated backups, \n it should be " + expectedCount + " but is " +_manager.backups.size(),
                _manager.backups.size() == expectedCount );
  }

}