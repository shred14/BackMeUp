package tests;

import static org.junit.Assert.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.*;

import model.HDDBackup;

import org.junit.*;

public class HDDBackupTest
{
  Path _origin;
  Path _destination;
  ArrayList<Path> _directoryOrigin, _directoryDestination;
  
  @Before
  public void setUp () throws Exception
  {  
    _directoryOrigin = new ArrayList<Path>( Arrays.asList( Paths.get("data"),
                                                           Paths.get("data/one.txt"),
                                                           Paths.get("data/subDir"),
                                                           Paths.get("data/subDir/two.txt")
                                                          ) );
    
    _directoryDestination = new ArrayList<Path>( Arrays.asList( Paths.get("bkData"),
                                                                Paths.get("bkData/one.txt"),
                                                                Paths.get("bkData/subDir"),
                                                                Paths.get("bkData/subDir/two.txt" )
                                                              ) );
    
    Files.createDirectory( Paths.get( "data" ) );
    Files.createFile( Paths.get( "data/one.txt" ) );
    Files.createDirectory( Paths.get( "data/subDir" ) );
    Files.createFile( Paths.get( "data/subDir/two.txt" ) );
    
    _origin = Paths.get( "origin.txt" );
    _destination = Paths.get( "destination.txt" );
    Files.createFile( _origin );
  }

  @After
  public void tearDown () throws Exception
  {
    try
    { 
      try
      {
        Files.delete( _origin );
        Files.delete( _destination );
      }
      catch( Exception ex ) {}
      
      
      Collections.reverse( _directoryOrigin );
      Collections.reverse( _directoryDestination );
      
      try
      {
        for( Path p : _directoryOrigin )
        {
          Files.delete( p );
        }
      
        for( Path p : _directoryDestination )
        {
          Files.delete( p );
        }
      }
      catch( Exception ex) {}
    }
    catch( Exception ex ) {}
  }

  @Test
  public void testEquals () throws Exception
  {
    HDDBackup first = new HDDBackup( "one", "two" );
    HDDBackup second = new HDDBackup( "three","four" );
    HDDBackup third = new HDDBackup( "one", "two" );
    assertTrue( "equals() should return true when HDDBackups are equal", first.equals( third ) );
    assertFalse( "equals() should return fasle when HDDBackups are not equal", first.equals( second ) ); 
  }
  
  @Test
  public void testUpdate () throws IOException
  {  
    HDDBackup bk = new HDDBackup( "origin.txt", "destination.txt" );
    bk.update();
    assertTrue( "update() should create a file when none exists", Files.exists( _destination ) );
    
    HDDBackup dirBK = new HDDBackup( "data", "bkData" );
    dirBK.update();
    
    for( Path p : _directoryDestination )
      assertTrue( "update() should copy a directory and it's contents:\n" + p.toString() + "\ndoesn't exist",
                  Files.exists( p ) );
    
    FileTime t = FileTime.fromMillis( 100 );
    Files.setLastModifiedTime( _destination,t );
    assertTrue( "update() should update and return true when backup is outmoded", bk.update() );
    assertFalse( "update() should return false when the backups is not outmoded", bk.update() );
    assertTrue( "FileTime should not have differed since last update",
                Files.getLastModifiedTime( _destination ).compareTo( Files.getLastModifiedTime( _origin ) ) == 0 );
  }
  
  @Test
  public void testToJSON ()
  {
    String expectedJSON = "{\"_location\":\"destination\",\"_origin\":\"origin\"}";
    HDDBackup bk = new HDDBackup( "origin", "destination" );
    String result = bk.toJSON();
    assertTrue( "expected toJSON() to return\n" + expectedJSON + "\nbut returned\n" + result,
                result.equals( expectedJSON) );
  }
  
  @Test
  public void testLocation ()
  {
    HDDBackup bk = new HDDBackup( "origin", "destination" );
    String location = bk.location().toString();
    assertTrue( "expected location() to return destination but was " + location, location.equals( "destination" ) );
  }
  
  @Test
  public void testOriginal ()
  {
    HDDBackup bk = new HDDBackup( "origin", "destination" );
    String origin = bk.original().toString();
    assertTrue( "expected original() to return origin but was " + origin,
                origin.equals("origin") );
  }
  
  @Test
  public void testOutModed() throws IOException
  {
    HDDBackup bk = new HDDBackup( _origin.toString(), _destination.toString() );
    assertTrue( "outModed() should return true when the backup hasn't been updated yet", bk.outModed() );
    Files.createFile( _destination );
    FileTime t = FileTime.fromMillis( 100 );
    Files.setLastModifiedTime( _destination, t );
    assertTrue( "outModed() should return true when out of date", bk.outModed() );
    Files.setLastModifiedTime( _origin, t );
    Files.setLastModifiedTime( _destination, t );
    assertFalse( "outModed() should return false when the backup is up to date", bk.outModed() );
  }
}