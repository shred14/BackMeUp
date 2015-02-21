package views;

import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JFrame;

public class MainView extends JFrame  implements View,ControlPanel
{	
  private static final long serialVersionUID = -2269516929789560867L;
  private BackupMetaView _backupsView;
  private BackupControls _controls;

  public MainView( Object[] backups )
  {            
    _controls = new BackupControls();
    _backupsView = new BackupMetaView( backups );    
    constructLayout();    
    setTitle( "BackMeUp - Main" );    
    this.pack();
    setDefaultCloseOperation( EXIT_ON_CLOSE );   
  }
      
  public void update ( Object[] content )
  {	
    _backupsView.setContent( content );  
  }
      
  public int[] getSelectedBackupsIndices ()  
  {  
    return _backupsView.getSelectedBackupsIndices();  
  }
  
  @Override  
  public void addSubscriber ( ActionListener handler )
  {  
    _controls.addSubscriber( handler );  
  }
      
  private void constructLayout ()  
  {  
    GroupLayout layout = new GroupLayout( this.getContentPane() );  
    this.getContentPane().setLayout( layout );  
    layout.setAutoCreateGaps( true );  
    layout.setAutoCreateContainerGaps( true );
    
    layout.setHorizontalGroup(  
                                layout.createSequentialGroup()     
                                  .addComponent( _backupsView )     
                                  .addComponent( _controls )
                             );
        
    layout.setVerticalGroup(
                              layout.createSequentialGroup()
                                .addGroup( layout.createParallelGroup( GroupLayout.Alignment.BASELINE )
                                           .addComponent( _backupsView )
                                           .addComponent( _controls ) 
                                         )
                           );  
  }
  
}