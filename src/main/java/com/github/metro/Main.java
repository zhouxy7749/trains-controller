package com.github.metro;

import com.github.metro.ui.MainForm;

import javax.swing.*;

public class Main {

	public static void main(String[] args){
		JFrame mainFrame = new JFrame("车载控制器仿真");
		mainFrame.setSize(1160, 660);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.add(new MainForm());
		mainFrame.setVisible(true);
	}

}
