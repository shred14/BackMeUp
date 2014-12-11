package view;

import java.awt.EventQueue;
import java.awt.TextField;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JTextField;

import model.Backup;

public class MainView extends JFrame {
	
	public BackupsPanel fileList;

    public MainView(String[] files) {
        setTitle("BackMeUp - Main");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JTextField tF = new JTextField("Hello World");
        add(tF);
        fileList = new BackupsPanel(files);
        add(fileList);
        setVisible(true);
     }
    
    public void changePanelContent(Object[] content){
    	fileList.changeContent(content);
    }
     

     
}
