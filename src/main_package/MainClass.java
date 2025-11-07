package main_package;


import java.awt.GridLayout;

import javax.swing.JFrame;

public class MainClass extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public MainClass() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Neural Network");
		setResizable(false);
		
		init();
	}
	
	public void init() {
		setLayout(new GridLayout());
		
		setLocationRelativeTo(null);
		setVisible(true);
		
		Screen s = new Screen();
		add(s);
		
		pack();
		
	}
	
	public static void main(String[] args) {
		new MainClass();
	}

}
