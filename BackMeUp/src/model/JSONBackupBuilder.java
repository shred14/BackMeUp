package model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONBackupBuilder
{  
  private String _JSON;
  
  public JSONBackupBuilder ( String j )
  {
    _JSON = j;
  }
  
  public HDDBackup buildHDDBackup () throws ParseException
  {
    JSONParser parser = new JSONParser();
    
    Object obj = parser.parse( _JSON );
    JSONObject hash = ( JSONObject ) obj;
    String origin = (String) hash.get( "_origin" );
    String location = (String) hash.get( "_location" );
    
    return new HDDBackup( origin, location ); 
  }
}
