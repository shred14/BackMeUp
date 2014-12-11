package view;

import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import model.Backup;

public class BackupsPanel extends JPanel {
	
	public DefaultListModel listModel;
	private JList contentList;
	private JScrollPane itemsScroller;
	
	public BackupsPanel(String[] content){
		changeContent(content);
		
		contentList = new JList(listModel);
		contentList.setLayoutOrientation(JList.VERTICAL);
		contentList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		contentList.setVisibleRowCount(-1);
		itemsScroller = new JScrollPane(contentList);
		itemsScroller.setPreferredSize(new Dimension(250, 80));
		add(itemsScroller);
	}


	public void changeContent(Object[] newContent) {
		if (listModel == null)
			listModel = new DefaultListModel();
		else
			listModel.clear();
		
		for(Object s : newContent)
			listModel.addElement(s);
	}
}
