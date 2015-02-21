package controller;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;

import model.Backup;
import model.HDDBackup;
import views.View;

public class BackupLocationSelector
{
  private View _owner;
  private JFileChooser _originSelector;
  private JFileChooser _locationSelector;
  
  public BackupLocationSelector (View currentView)
  {
    _owner = currentView;
    constructOriginSelector();
    constructDestinationSelector();
  }
  
  public Backup run ()
  {
    int result = _originSelector.showOpenDialog((Component) _owner);
    
    if(result == JFileChooser.APPROVE_OPTION)
    {
      File source = _originSelector.getSelectedFile();
      
      int locationSelectorResult = _locationSelector.showSaveDialog((Component) _owner);
      
      if(locationSelectorResult == JFileChooser.APPROVE_OPTION)
      { 
        File destination = _locationSelector.getSelectedFile();
        HDDBackup bk = new HDDBackup(source.toString(), destination.toString());
        return bk;
      }
    }
    return null;
  }
  
  private void constructOriginSelector ()
  {
    _originSelector = new JFileChooser();
    _originSelector.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    _originSelector.setAcceptAllFileFilterUsed(false);
  }
  
  private void constructDestinationSelector ()
  {
    _locationSelector = new JFileChooser();
    _locationSelector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    _locationSelector.setAcceptAllFileFilterUsed(false);
  }
}
