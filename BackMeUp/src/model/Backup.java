package model;

import java.nio.file.Path;
 
public interface Backup
{
  
  public boolean equals (Backup b);
  
  public Path location ();

  public Path original ();
  
  public boolean outModed ();
  
  public String toJSON ();
  
	public boolean update ();

}