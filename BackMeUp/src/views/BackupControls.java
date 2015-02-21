package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class BackupControls extends JPanel implements ControlPanel
{  
  private static final long serialVersionUID = -1902444532690820725L;
  private ArrayList<AbstractButton> _buttonComponents = new ArrayList<AbstractButton>();
  
  public BackupControls ()
  {    
    this.setLayout( new BoxLayout( this, BoxLayout.PAGE_AXIS ) );
    populateControls();
    
    //for layout debugging purposes
    setBackground(Color.GREEN);
  }

  @Override
  public void addSubscriber (ActionListener handler)
  {
    for( AbstractButton b : _buttonComponents )
    {
      b.addActionListener( handler );
    }
  }
  
  private void populateControls ()
  {
    constructButton( "Add Backup", "add" );
    constructButton( "Remove Backup", "remove" );
    constructButton( "Update Backups", "update" );
    
    for( AbstractButton b : _buttonComponents )
    {
      this.add( b );
      this.add( Box.createRigidArea( new Dimension(0,15) ) );
    }
  }
  
  private void constructButton (String displayText, String eventText)
  {
    JButton b = new JButton( displayText );
    b.setActionCommand( eventText );
    _buttonComponents.add( b );
  }
  
}