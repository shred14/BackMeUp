package model;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.CopyOption;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.*;
import java.nio.file.attribute.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.json.simple.JSONObject;

import static java.nio.file.FileVisitResult.*;
import static java.nio.file.StandardCopyOption.*;


public class HardDiskBackup implements Backup {
	
	private Path _origin, _location;
	
	public HardDiskBackup(Path original, Path destination) throws IOException{
		_origin = original;
		_location = destination;
		Files.createDirectory(destination);
	}
	
	public HardDiskBackup(String o, String d) throws IOException {
		_origin = Paths.get(o);
		_location = Paths.get(d);
		Files.createDirectory(_location);
	}

	/* (non-Javadoc)
	 * @see model.Backup#update()
	 */
	@Override
	public void update() throws IOException {
		Files.walkFileTree(_origin, new DirCopier());
	}
	
	void copyFile(Path source, Path target) {
		CopyOption[] options =  new CopyOption[] { COPY_ATTRIBUTES, REPLACE_EXISTING };
	        
	    try {
	    	Files.copy(source, target, options);
	    } catch (IOException x) {
	        System.err.format("Unable to copy: %s: %s%n", source, x);
	    }
	 }
	
	class DirCopier implements FileVisitor<Path> {

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


	@Override
	public String toJSON() throws IOException {
		JSONObject obj = new JSONObject();

	      obj.put("_origin", _origin);
	      obj.put("_location", _location);

	      StringWriter out = new StringWriter();
	      obj.writeJSONString(out);
	      return out.toString();
	}

  @Override
  public boolean equals(Backup b) {
    if (_origin.equals(b.origin()))
      if (_location.equals(b.location()))
          return true;
    
    return false;
  }

  @Override
  public Path origin() {
    return _origin;
  }

  @Override
  public Path location() {
    // TODO Auto-generated method stub
    return _location;
  }
}
