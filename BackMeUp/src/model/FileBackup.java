package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.*;

public class FileBackup {
	
	private File _origin, _location;
	
	public FileBackup(File original, File destination){
		_origin = original;
		_location = destination;
	}

	public void update() throws IOException {
		Files.copy(Paths.get(_origin.getPath()), Paths.get(_location.getPath()), REPLACE_EXISTING);	
	}

}
