package tests.helpers;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import model.HDDBackup;

public class TestHelper {
  
  private String _metaFileDirectory, _metaFileLocation;
  private String JSON;
  
  public TestHelper() {
    JSON = "{\"_location\":\"destination\",\"_origin\":\"origin\"}";
    _metaFileLocation = "data/bk.dat";
    _metaFileDirectory = "data";
  }
  
  public HDDBackup generateHDDBackup() {
    return new HDDBackup("origin", "destination");
  }
  
  
  public void createMetaFile(int countOfBackups) throws IOException {
    assert(countOfBackups >= 1);
    
    Files.createDirectory(Paths.get(_metaFileDirectory));
    FileWriter writer = new FileWriter(_metaFileLocation);
  
    writer.write(JSON);
    
    for(int i = 1; i < countOfBackups; i++) {  
      writer.write(System.lineSeparator());
      writer.write(JSON);
    }
    
    writer.flush();
    writer.close();
  }
  
  public void destroyMetaFile() throws IOException {
      Files.delete(Paths.get(_metaFileLocation));
      Files.delete(Paths.get(_metaFileDirectory));
  }
}
