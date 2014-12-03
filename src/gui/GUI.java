package gui;

import javax.swing.JFrame;

public class GUI extends JFrame {
	
	public GUI(String name){
		super(name);
		setSize(500,500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setVisible(true);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new GUI("Graph");
	}

}
