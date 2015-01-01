package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import graph.Graph;

import javax.swing.JFrame;

public class GUI extends JFrame {
	
	private GraphComponent g;
	
	public GUI(String name){
		super(name);
		setSize(500,500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		g = new GraphComponent(Graph.loadGraph("rumaenien.graph"));
		this.getContentPane().add(g);
		setVisible(true);
	}
	
	public void startLoop(){
		Thread t = new Thread(){
			public void run(){
				g.sortNodes();
				long t1 = System.currentTimeMillis();
				while(true){
					long deltaT = System.currentTimeMillis() - t1;
					if(deltaT >= 17){
						t1 = System.currentTimeMillis();
						g.update(deltaT);
					}
				}
			}
		};
		t.start();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GUI g = new GUI("Graph");
		g.startLoop();
	}

}

