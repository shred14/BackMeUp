package model;

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

/*
 * A class to manage the backups that exist already. Should retrieve backups that are in need of updating, and possibly
 * execute the updates as well. Can check individual backups as well
 */
public final class BackupManager {
  
  private static BackupManager _manager;
  
  private static BackupMetaManager _metaManager;
	
	private BackupManager() {
	  _metaManager = BackupMetaManager.getInstance();
	}
	
	public static BackupManager getInstance() {
	  
	  if(_manager == null) {
	    _manager = new BackupManager();
	  }
	    
	  return _manager;
	}
	
	public int updateBackups() {
	  int count = 0;
	  
	  for(Backup b : _metaManager.backups) {
	   if (b.update()) {
	     count++;
	   }
	  }
	  return count;
	}
	
	public ArrayList<Backup> getPresenter() throws Exception {
	  throw new Exception("not implemented yet");
	}
	
//	
//	boolean toJSON() throws IOException {
//		
//		FileWriter writer = null;
//		boolean result = true;
//		
//		try {
//		  writer = new FileWriter(PATH);
//			for(Backup bk : currentBackups) {
//				writer.write(bk.toJSON() + System.getProperty("line.separator"));
//			}
//		} catch(IOException ex) {
//		  result = false;
//			System.err.println(ex.getMessage());
//		} finally {
//		  writer.flush();
//		  writer.close();
//		}
//		return result;
//	}11111111111111111
//
	
}


