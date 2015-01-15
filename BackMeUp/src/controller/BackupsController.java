package controller;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import model.BackupManager;
import views.BackupMetaView;
import views.MainView;
import views.View;

public class BackupsController implements ActionListener {
	
  private View _currentView;
  private BackupManager _manager;
  
	public static void main(String[] args) {
		String[] test = {"one", "two"};
		MainView ex = new MainView(test);
		ex.setVisible(true);
		String[] muppet = {"three", "four"};
		ex.setBackupMetaContent(muppet);
  }

  @Override
  public void actionPerformed(ActionEvent event) {
    String message = event.getActionCommand();
    
    if ("add".equals(message)) {  
      System.out.println("add");
    } else if ("remove".equals(message)) {
      System.out.println("remove");
    }
  }
}

