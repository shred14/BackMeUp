package model;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import org.json.simple.parser.ParseException;

public class BackupMetaManager
{
  
  private final static String _directory = "data";
  private final static String _filePath = "bk.dat";
  public final static String PATH = _directory + "/" + _filePath;
  public ArrayList<Backup> backups;
  
  private static BackupMetaManager _manager;
  
  private BackupMetaManager ()
  {
    backups = new ArrayList<Backup>();
  }
  
  public static BackupMetaManager getInstance ()
  {  
    if( _manager == null )
    {
      _manager = new BackupMetaManager();
    }
    
    return _manager;
  }
  
  public void saveData () throws IOException
  {  
    if ( metaDirectoryAbsent() )
    {  
      Files.createDirectory( Paths.get(_directory) );
    }
    
    BufferedWriter writer = new BufferedWriter( new FileWriter(PATH) );
    try
    {
      for( Backup bk : backups )
      {
        writer.write( bk.toJSON() );
        writer.newLine();
      }
    }
    finally
    {
      writer.flush();
      writer.close();
    }
    
  }
  
  public boolean readData () throws IOException, ParseException
  {
    if( metaDirectoryAbsent() )
    {
      return false;
    }
    
    BufferedReader reader = new BufferedReader( new FileReader(PATH) );
    try
    {
      String line = reader.readLine();
      
      while( line != null )
      {
        JSONBackupBuilder builder = new JSONBackupBuilder( line );
        HDDBackup bk = builder.buildHDDBackup();
        backups.add( bk );
        line = reader.readLine();
      }
      
    }
    finally
    {
      reader.close();
    }
    
    return true;
  }
  
  private boolean metaDirectoryAbsent ()
  {
    return ! Files.exists( Paths.get( _directory ) );
  }
  
}