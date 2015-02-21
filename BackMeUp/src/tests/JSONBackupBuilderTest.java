package tests;

import static org.junit.Assert.*;
import model.HDDBackup;
import model.JSONBackupBuilder;

import org.junit.*;


public class JSONBackupBuilderTest
{
  @Test
  public void testBuildHDDBackup () throws Exception
  {
    String expectedJSON = "{\"_location\":\"destination\",\"_origin\":\"origin\"}";
    JSONBackupBuilder builder =  new JSONBackupBuilder( expectedJSON );
    HDDBackup bk = builder.buildHDDBackup();
    
    String result = new String( bk.original().toString() );
    assertTrue( "original() should have been origin but was " + result,
                result.equals("origin"));
    result = bk.location().toString();
    assertTrue( "location() should have been destination but was " + result,
                result.equals("destination"));
  }
}