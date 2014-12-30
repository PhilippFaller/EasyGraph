package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import graph.Graph;

import javax.swing.JFrame;

public class GUI extends JFrame {
	
	public GUI(String name){
		super(name);
		setSize(500,500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
//		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(new GraphComponent(Graph.loadGraph("rumaenien.graph")));
		setVisible(true);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new GUI("Graph");
	}

}

