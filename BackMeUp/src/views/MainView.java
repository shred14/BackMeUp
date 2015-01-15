package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Backup;

public class MainView extends JFrame  implements View,ControlPanel {
	
  private BackupMetaView _backupsStatus;
  private BackupControls _controls;

    public MainView(String[] files) {        
        _controls = new BackupControls();
        _backupsStatus = new BackupMetaView(files);
        
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        layout.setHorizontalGroup(
            layout.createSequentialGroup()
               .addComponent(_backupsStatus)
               .addComponent(_controls)
         );
        
        layout.setVerticalGroup(
            layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(_backupsStatus)
                    .addComponent(_controls))
         );
        
        setTitle("BackMeUp - Main");
        this.pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
     }
    
     

    
    public void setBackupMetaContent(Object[] content){
    	_backupsStatus.setContent(content);
    }

    @Override
    public void display() {
      // TODO needs to be essentially the main entry point  
    }

    @Override
    public void addSubscriber(ActionListener handler) {
      _controls.addSubscriber(handler);
      // TODO needs to add whatever deals with the events the view raises
    }   
}
