package views;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class BackupMetaView extends JPanel implements View
{
  private static final long serialVersionUID = 1L;
  private DefaultListModel<Object> _listModel;
	private JTable _table;
	
	public BackupMetaView( Object[] content )
	{
		this.setContent( content );
		this.setLayout( new BorderLayout() );	
		_table = new JTable( new MyTableModel() );
		setCellRenderers();
		constructLayout();
		setBackground( Color.RED );		
	}
	
	private void setCellRenderers () 
	{
	  _table.getColumnModel().getColumn( 2 ).setCellRenderer( new ImageRenderer() );
	}
	
	private void constructLayout ()
	{
	  JScrollPane itemsScroller = new JScrollPane( _table );
    ScrollPaneLayout layout = new ScrollPaneLayout();
    layout.layoutContainer( itemsScroller );
    itemsScroller.setLayout( layout );
    this.add( itemsScroller, BorderLayout.CENTER );
	}


	public void setContent ( Object[] newContent ) 
	{
		if (_listModel == null)
		{
			_listModel = new DefaultListModel<Object>();
		}
		else
		{
			_listModel.clear();
		}
		
		for( Object s : newContent )
			_listModel.addElement( s );
	}
	
	public int[] getSelectedBackupsIndices()
  {
    return _table.getSelectedRows();
  }
		
	class MyTableModel extends AbstractTableModel
	{  
    private static final long serialVersionUID = 1L;

    String[] columnNames = { "Original File",
                             "Backup File",
                             "Outmoded" };
	  
	  Object[][] rowData = {
          { "Kathy", "Smith", new Boolean( false ) },
          { "John", "Doe", new Boolean( true ) },
          { "Sue", "Black", new Boolean( false ) },
          { "Jane", "White", new Boolean( true ) },
          { "Joe", "Brown", new Boolean( false )
        }
    };
	  
    public String getColumnName( int col )
    {  
      return columnNames[col].toString();
    }
    
    public int getRowCount () { return rowData.length; }
    
    public int getColumnCount () { return columnNames.length; }
    
    public Object getValueAt ( int row, int col ) { return rowData[row][col]; }
    
    public boolean isCellEditable ( int row, int col ) { return false; }
    
    public void setValueAt( Object value, int row, int col )
    {
        rowData[row][col] = value;
        fireTableCellUpdated( row, col );
    }
	}
	
	class ImageRenderer extends DefaultTableCellRenderer
  {
    private static final long serialVersionUID = 1L;
    JLabel lbl = new JLabel();
    ImageIcon icon = new ImageIcon( getClass().getResource( "test.png" ) );

    public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected,
                                                    boolean hasFocus, int row, int column )
    {
      lbl.setText( "hello" );
      lbl.setIcon( icon );
      return lbl;
    }
  }

}