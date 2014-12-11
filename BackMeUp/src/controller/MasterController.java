package controller;

import java.awt.EventQueue;

import javax.swing.JTextField;

import view.BackupsPanel;
import view.MainView;

public class MasterController {
	
	public static void main(String[] args) {
		String[] test = {"one", "two"};
		MainView ex = new MainView(test);
		String[] muppet = {"three", "four"};
		ex.changePanelContent(muppet);
     }
}

