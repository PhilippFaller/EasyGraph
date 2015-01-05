package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.NoSuchElementException;

import graph.Graph;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import data.GraphIO;

public class GUI extends JFrame {
	
	private final JFileChooser fileChooser = new JFileChooser();
	private final char CTRL_L = 12;
	
	
	private boolean update;
	private GraphComponent g;
	private Thread repaintThread;
	private int millisBetweenRepaint;
	
	public GUI(String name){
		super(name);
		setSize(800,800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		try{g = new GraphComponent(GraphIO.loadGraph("rumaenien.graph"));
		this.getContentPane().add(g);
		}catch(Exception e){e.printStackTrace();}
//		g = null;
		update = false;
		millisBetweenRepaint = 17;
		repaintThread =  new Thread(){
			public void run(){
				long t0 = System.currentTimeMillis();
				while(true){
					long deltaT = System.currentTimeMillis() - t0;
					if(deltaT >= millisBetweenRepaint){
						t0 = System.currentTimeMillis();
						repaint();
					}
				}
			}
		};
		addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == CTRL_L){
					loadGraph();
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// You do nuthin', John Snow!				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// You do nuthin', John Snow!
				
			}
		});
		setVisible(true);
	}
	
	//TODO überarbeiten sortNodes()
	
	
	public void startLoops(){
		repaintThread.start();
		g.sortNodes();
		while(true)	if(update) g.update(0);
	}
	
	private synchronized void setGraphComponent(GraphComponent g){
		this.g = g;
	}
	
	private synchronized GraphComponent getGraphComponent(){
		return g;
	}
	
	private void loadGraph(){
//		int result = fileChooser.showDialog(GUI.this, "Load");
//		if(result == JFileChooser.APPROVE_OPTION)
			try{
//				g = new GraphComponent(GraphIO.loadGraph(fileChooser.getSelectedFile().getAbsolutePath()));
				g = new GraphComponent(GraphIO.loadGraph("test.graph"));
				System.out.println("Graph loaded");
				this.getContentPane().add(g);
				System.out.println("Graph added");
				g.sortNodes();
				System.out.println("Graph sorted");
				update = true;
				System.out.println("update true");
			}
		catch(Exception e){
			JOptionPane.showMessageDialog(this, "Fehler beim lesen der Datei \""
					+ fileChooser.getSelectedFile().getAbsolutePath() + "\".",
					"Fehler", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GUI g = new GUI("Graph");
		g.startLoops();
	}

}

