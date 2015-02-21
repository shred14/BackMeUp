package tests;

import model.*;
import org.junit.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BackupManagerTest
{  
  private BackupManager _manager;
  private BackupMetaManager _metaManager;
  
  @Before
  public void setUp ()
  {
    _manager = BackupManager.getInstance();
    _metaManager = BackupMetaManager.getInstance();
  }
  
  @Test
  public void testGetInstance ()
  {
    assertTrue( "getInstance() should never return null", _manager != null );
    assertTrue( "getInstance() should return only a single non-changing object",
                BackupManager.getInstance().equals(_manager) );
  }

  @Test
  public void testUpdateBackups ()
  {
    int countOfUpdated = 2;
    
    HDDBackup updateableBK = mock( HDDBackup.class );
    when( updateableBK.update() ).thenReturn( true );
    
    HDDBackup currentBK = mock( HDDBackup.class );
    when( currentBK.update() ).thenReturn( false );
    
    for( int i = 0; i < countOfUpdated; i++ )
    {
      _metaManager.backups.add( updateableBK );
    }
    
    _metaManager.backups.add( currentBK );
    
    int result = _manager.updateBackups();
    assertTrue( "updateBackups() should have returned\n" + countOfUpdated + "\n but returned \n " + result,
                result == countOfUpdated );
  }
}
