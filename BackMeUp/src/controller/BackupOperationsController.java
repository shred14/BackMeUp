package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.*;
import views.MainView;

public class BackupOperationsController implements ActionListener
{
	
  private MainView _currentView;
  private BackupManager _manager;
  private BackupMetaManager _metaManager;
  
  public BackupOperationsController ()
  {
    _manager = BackupManager.getInstance();
    _metaManager = BackupMetaManager.getInstance();
  }

  @Override
  public void actionPerformed (ActionEvent event)
  {
    String message = event.getActionCommand();
    
    switch(message)
    {
      case "add":
        createBackup();
        break;
      case "remove":
        removeBackup();
        break;
      case "update":
        updateBackups();
        break;
      default:
        System.err.println( "Unknown event: " + message );
        System.exit( 1 );
        break;
    }
  }
  
  public void displayView ()
  {
    if( _currentView == null )
    {
      _currentView = new MainView( _metaManager.backups.toArray() );
    }
    else
    {
      _currentView.update( _metaManager.backups.toArray() );
    }
    _currentView.addSubscriber( this );
    _currentView.setVisible( true );
  }
  
  private void updateBackups ()
  {
    String title = "Confirm: Update";
    String message = "Are you sure you wish to update all backups?";
    
    if( userConfirms( title, message ) )
    {
      _manager.updateBackups();
    }
  }
  
  private void removeBackup ()
  {
    String title = "Warning: Backup removal";
    String message =
        "Warning! You are about to remove the selected Backups." + System.lineSeparator() 
        + "This will not delete any files but Back Me Up will not track it anymore." + System.lineSeparator()
        + "Are you sure wish to do this?";
    
    if( userConfirms( title, message ) )
    {
      int[] indices = _currentView.getSelectedBackupsIndices();
      
      if( indices.length == 0)
      {
        return;
      }
      
      _manager.removeBackups( indices );
    }
  }
  
  private void createBackup ()
  {
    BackupLocationSelector selector = new BackupLocationSelector( _currentView );
    HDDBackup bk = (HDDBackup) selector.run();
    _manager.addBackup( bk );
    _currentView.update( _metaManager.backups.toArray() );
  }
  
  private boolean userConfirms (String title, String message)
  {
    int result = JOptionPane.showConfirmDialog( _currentView, message, title, JOptionPane.YES_NO_OPTION );
    return result == JOptionPane.YES_OPTION;
  }
  
}

