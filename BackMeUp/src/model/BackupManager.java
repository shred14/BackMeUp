package model;

import java.util.Arrays;

/*
 * A class to manage the backups that exist already. Should retrieve backups that are in need of updating, and possibly
 * execute the updates as well. Can check individual backups as well
 */
public final class BackupManager 
{ 
  private static BackupManager _manager; 
  private static BackupMetaManager _metaManager;
	
	private BackupManager() 
	{
	  _metaManager = BackupMetaManager.getInstance();
	}
	
	public static BackupManager getInstance() 
	{  
	  if( _manager == null )
	  {
	    _manager = new BackupManager();
	  }
	    
	  return _manager;
	}
	
	public int updateBackups()
	{
	  int count = 0;
	  
	  for( Backup b : _metaManager.backups )
	  {
	    if ( b.update() )
	    {
	      count++;
	    }
	  }
	  
	  return count;
	}
	
	public void addBackup(Backup bk)
	{
	  _metaManager.backups.add(bk);
	}
	
	public void removeAt(int index)
	{
	  _metaManager.backups.remove(index);
	}
	
	public void removeBackups(int[] indices)
	{
	  Arrays.sort( indices );
    int offset = 0;
    
    for ( int index : indices )
    {
      _manager.removeAt( index - offset );
      offset++;
    }
    
	}
	
	public boolean removeBackup(Backup bk)
	{
	  boolean result = false;
	  
	  for( Backup b : _metaManager.backups )
	  {
	    if( b.equals(bk) )
	    {
	      _metaManager.backups.remove( b );
	      result = true;
	      break;
	    }
	  }
	  
	  return result;
	}
	
}