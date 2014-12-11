package model;

import java.io.IOException;
import java.nio.file.Path;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
 
public interface Backup {

	public abstract void update() throws IOException;
	
	public String toJSON() throws IOException;
	
	public boolean equals(Backup b);
	
	public Path origin();
	
	public Path location();

}