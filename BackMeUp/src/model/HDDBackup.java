package model;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.CopyOption;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.*;
import java.nio.file.attribute.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.json.simple.JSONObject;

import static java.nio.file.FileVisitResult.*;
import static java.nio.file.StandardCopyOption.*;


public class HDDBackup implements Backup {
	
	private Path _origin, _location;
	
	public HDDBackup(String original, String destination) {
		_origin = Paths.get(original);
		_location = Paths.get(destination);
	}

	/* (non-Javadoc)
	 * @see model.Backup#update()
	 */
	@Override
	public boolean update() {
	  
	  // if backup is old, should update. if backup does not exist it should update
	  boolean result = false;
	  
	  if (this.outModed()) {
	    result = true;
	  
	    try {
	      Files.walkFileTree(_origin, new DirCopier());
	    } catch (IOException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }
	  }
		
		return result;
	}
	
	protected void copyFile(Path source, Path target) {
		CopyOption[] options =  new CopyOption[] { COPY_ATTRIBUTES, REPLACE_EXISTING };
	        
	    try {
	    	Files.copy(source, target, options);
	    } catch (IOException x) {
	        System.err.format("Unable to copy: %s: %s%n", source, x);
	    }
	 }
	
	protected class DirCopier implements FileVisitor<Path> {

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
            // before visiting entries in a directory we copy the directory
            // (okay if directory already exists).
            CopyOption[] options = new CopyOption[] { COPY_ATTRIBUTES };
            Path newdir = _location.resolve(_origin.relativize(dir));
            try {
                Files.copy(dir, newdir, options);
            } catch (FileAlreadyExistsException x) {
                // ignore
            } catch (IOException x) {
                System.err.format("Unable to create: %s: %s%n", newdir, x);
                return SKIP_SUBTREE;
            }
            return CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            copyFile(file, _location.resolve(_origin.relativize(file)));
            return CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
            // fix up modification time of directory when done
            if (exc == null) {
                Path newdir = _location.resolve(_origin.relativize(dir));
                try {
                    FileTime time = Files.getLastModifiedTime(dir);
                    Files.setLastModifiedTime(newdir, time);
                } catch (IOException x) {
                    System.err.format("Unable to copy all attributes to: %s: %s%n", newdir, x);
                }
            }
            return CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) {
            if (exc instanceof FileSystemLoopException) {
                System.err.println("cycle detected: " + file);
            } else {
                System.err.format("Unable to copy: %s: %s%n", file, exc);
            }
            return CONTINUE;
        }
	}


	@SuppressWarnings("unchecked")
  @Override
	public String toJSON() {
		JSONObject obj = new JSONObject();

	  obj.put("_origin", _origin.toString());
	  obj.put("_location",_location.toString() );

	  StringWriter out = new StringWriter();
	      try {
          obj.writeJSONString(out);
        } catch (IOException e) {
          e.printStackTrace();
        }
	      return out.toString();
	}

  @Override
  public boolean equals(Backup b) {
    if (original().equals(b.original()))
      if (location().equals(b.location()))
          return true;
    
    return false;
  }

  @Override
  public Path location() {
    return _location;
  }

  @Override
  public Path original() {
    return _origin;
  }

  @Override
  public boolean outModed() {
    boolean result = false;
    if (!Files.exists(_location)) {
      result = true;
    } else {
      try {
        FileTime bk = Files.getLastModifiedTime(_location);
        FileTime o = Files.getLastModifiedTime(_origin);
      
        if (bk.compareTo(o) < 0) { //old
          result = true;
        }
      } catch (IOException e) {
        e.printStackTrace();
      }    
    }
    return result;
  }

}
