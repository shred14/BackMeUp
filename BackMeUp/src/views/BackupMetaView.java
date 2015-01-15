package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneLayout;

import model.Backup;

public class BackupMetaView extends JPanel implements View {
	
	private DefaultListModel listModel;
//	private JList contentList;
//	private JScrollPane itemsScroller;
	
	public BackupMetaView(String[] content){
		this.setContent(content);
		this.setLayout(new BorderLayout());
		
		JList contentList = new JList(listModel);
		contentList.setLayoutOrientation(JList.VERTICAL);
		//contentList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		contentList.setVisibleRowCount(-1);
		
		JScrollPane itemsScroller = new JScrollPane(contentList);
		//temsScroller.setPreferredSize(new Dimension(250, 80));
		ScrollPaneLayout layout = new ScrollPaneLayout();
		layout.layoutContainer(itemsScroller);
		itemsScroller.setLayout(layout);
		this.add(itemsScroller, BorderLayout.CENTER);
		setBackground(Color.RED);
	}


	public void setContent(Object[] newContent) {
		if (listModel == null)
			listModel = new DefaultListModel();
		else
			listModel.clear();
		
		for(Object s : newContent)
			listModel.addElement(s);
	}


  @Override
  public void display() {
    // TODO Auto-generated method stub
  }
 
}
