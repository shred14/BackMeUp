package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
 
public interface Backup {
  
  public boolean equals(Backup b);
  
  public Path location();

  public Path original();
  
  public boolean outModed();
  
  public String toJSON();
  
	public boolean update();

}