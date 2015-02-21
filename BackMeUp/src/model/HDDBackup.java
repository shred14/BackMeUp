package model;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;

import org.json.simple.JSONObject;

import static java.nio.file.FileVisitResult.*;
import static java.nio.file.StandardCopyOption.*;


public class HDDBackup implements Backup
{
	
	private Path _origin, _location;
	
	public HDDBackup (String original, String destination)
	{
		_origin = Paths.get( original );
		_location = Paths.get( destination );
	}

	@Override
	public boolean update ()
	{
	  boolean successful = false;
	  
	  if ( this.outModed() )
	  {
	    try
	    {
	      Files.walkFileTree( _origin, new DirCopier() );
	      successful = true;
	    }
	    catch ( IOException e )
	    {
	      e.printStackTrace();
	    }
	  }
		
		return successful;
	}
	
	protected void copyFile ( Path source, Path target )
	{
		CopyOption[] options =  new CopyOption[] 
		    { 
		      COPY_ATTRIBUTES,
		      REPLACE_EXISTING
		    };
	        
	    try
	    {
	    	Files.copy( source, target, options );
	    }
	    catch ( IOException x )
	    {
	      System.err.format( "Unable to copy: %s: %s%n", source, x );
	    }
	 }
	
	protected class DirCopier implements FileVisitor<Path>
	{
	  @Override
    public FileVisitResult preVisitDirectory (Path dir, BasicFileAttributes attrs)
	  {
	    CopyOption[] options = new CopyOption[] { COPY_ATTRIBUTES, REPLACE_EXISTING };
      Path newdir = _location.resolve( _origin.relativize( dir ) );
      try
      {
        Files.copy( dir, newdir, options );
      }
      catch ( IOException x )
      {
        System.err.format( "Unable to create: %s: %s%n", newdir, x );
        return SKIP_SUBTREE;
      }
      
      return CONTINUE;
    }

    @Override
    public FileVisitResult visitFile ( Path file, BasicFileAttributes attrs )
    {
      copyFile( file, _location.resolve( _origin.relativize( file ) ) );
      return CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory( Path dir, IOException exc )
    {
    // fix up modification time of directory when done
      if ( exc == null )
      {
        Path newdir = _location.resolve( _origin.relativize( dir ) );
        try
        {
          FileTime time = Files.getLastModifiedTime( dir );
          Files.setLastModifiedTime( newdir, time );
        }
        catch ( IOException x)
        {
          System.err.format( "Unable to copy all attributes to: %s: %s%n", newdir, x );
        }
      }
      return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed( Path file, IOException exc )
    {
      if ( exc instanceof FileSystemLoopException )
      {
        System.err.println( "cycle detected: " + file );
      }
      else
      {
        System.err.format( "Unable to copy: %s: %s%n", file, exc );
      }
      return CONTINUE;
    }
	}


	@SuppressWarnings( "unchecked" )
  @Override
	public String toJSON ()
	{
		JSONObject obj = new JSONObject();
	  obj.put( "_origin", _origin.toString() );
	  obj.put( "_location",_location.toString() );

	  StringWriter out = new StringWriter();
	  try
	  {
	    obj.writeJSONString( out );
    }
	  catch ( IOException e )
	  {
	    e.printStackTrace();
    }
	  
	  return out.toString();
	}

  @Override
  public boolean equals ( Backup b )
  {
    if ( original().equals( b.original() ) )
    {
      if ( location().equals( b.location() ) )
      {
        return true;
      }
    }
    
    return false;
  }

  @Override
  public Path location ()
  {
    return _location;
  }

  @Override
  public Path original ()
  {
    return _origin;
  }

  @Override
  public boolean outModed ()
  {
    if ( !Files.exists( _location ) )
    {
      return true;
    }
    else
    {
      try
      {
        FileTime bk = Files.getLastModifiedTime( _location );
        FileTime o = Files.getLastModifiedTime( _origin );
      
        if ( bk.compareTo( o ) < 0 )
        {
          return true;
        }
      }
      catch ( IOException e )
      {
        e.printStackTrace();
      }    
    }
    return false;
  }
  
  @Override
  public String toString ()
  {
    return _location + " \t " + _origin + "\t";
  }

}