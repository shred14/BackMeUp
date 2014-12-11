package unknown;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
 




import org.json.simple.JSONObject;

import model.Backup;
import model.HardDiskBackup;

/*
 * A class to manage the backups that exist already. Should retrieve backups that are in need of updating, and possibly
 * execute the updates as well. Can check individual backups as well
 */
public final class BackupManager {
	public final String PATH = ".BackMeUp_Data.json";
	private static BackupManager manager = null;
	private static ArrayList<Backup> currentBackups = null;
	
	private BackupManager() {
	}
	
	public static BackupManager getInstance() {
		if(manager == null) {
			manager = new BackupManager();
			currentBackups = new ArrayList<Backup>();
		}
		return manager;
	}
	
	public void addBackup(Backup bk) {
	  currentBackups.add(bk);
	}
	
	public int countOfBackups() {
	  return currentBackups.size();
	}

	public boolean readBackups() {
	  if(currentBackups == null || countOfBackups() == 0)
      return false;
		else
			return true;
	}
	
	public boolean writeBackups() throws IOException {
		if(currentBackups == null || countOfBackups() == 0)
			return false;
		else {
		  return toJSON();
		}
	}
	
	public boolean updateBackups() {
		return false;
	}
	
	boolean toJSON() throws IOException {
		
		FileWriter writer = null;
		boolean result = true;
		
		try {
		  writer = new FileWriter(PATH);
			for(Backup bk : currentBackups) {
				writer.write(bk.toJSON() + System.getProperty("line.separator"));
			}
		} catch(IOException ex) {
		  result = false;
			System.err.println(ex.getMessage());
		} finally {
		  writer.flush();
		  writer.close();
		}
		return result;
	}

  public boolean removeBackup(HardDiskBackup bk) {
    boolean result = false;
    
    for(int i = 0; i < currentBackups.size(); i++) {
      Backup current = currentBackups.get(i);
      if (current.equals(bk)) {
        currentBackups.remove(i);
        result = true;
        break;
      }
    }
    
    return result;
  }

  public void clear() {
    currentBackups.clear();
  }
	
	
}


