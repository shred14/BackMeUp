package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class BackupControls extends JPanel implements ControlPanel {
  
  private JButton addButton, removeButton;
  
  public BackupControls() {
    
    addButton = new JButton("Add Backup");
    addButton.setActionCommand("add");
    removeButton = new JButton("Remove Backup");
    removeButton.setActionCommand("remove");
    this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    
    
    
    
    this.add(addButton);
    this.add(Box.createRigidArea(new Dimension(0,15)));
    this.add(removeButton);
    this.add(Box.createVerticalGlue());
    
    setBackground(Color.GREEN);
  }

  @Override
  public void addSubscriber(ActionListener handler) {
    // TODO Auto-generated method stub
  }
}
