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
		g = new GraphComponent(Graph.loadGraph("test.graph"));
		this.getContentPane().add(g);
		setVisible(true);
	}
	
	//TODO überarbeiten sortNodes()
	public void skipLoop(){
		g.sortNodes();
		for(int i = 0; i < 180000 ; i ++){
			g.update(17);
		}
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
						repaint();
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
//		g.skipLoop();
		g.startLoop();
	}

}

