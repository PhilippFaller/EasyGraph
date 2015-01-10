package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
	private int millisBetweenRepaint;
	
	public GUI(String name){
		super(name);
		setSize(400,400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
//		try{g = new GraphComponent(GraphIO.loadGraph("rumaenien.graph"));
//		this.getContentPane().add(g);
//		}catch(Exception e){e.printStackTrace();}
		g = null;
		update = false;
		millisBetweenRepaint = 17;
		addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == CTRL_L){
					loadGraph();
				}
				else if (e.getKeyChar() == ' ') update = !update;
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
		getContentPane().addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				NodeComponent n = null;
				if(g != null) n = g.getNodeAt(e.getX(), e.getY());
				if(n != null) n.setDragged(false);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				NodeComponent n = null;
				if(g != null) n = g.getNodeAt(e.getX(), e.getY());
				if(n != null) n.setDragged(true);
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		setVisible(true);
	}
	
	//TODO überarbeiten sortNodes()
	
	
	public void startLoop(){
		long t0 = System.currentTimeMillis();
		while(true){
			if(update) g.update();
			long deltaT = System.currentTimeMillis() - t0;
			if(deltaT >= millisBetweenRepaint){
				t0 = System.currentTimeMillis();
				repaint();
			}
		}
//		g.sortNodes();
	}
//	
//	public synchronized void setGraphComponent(GraphComponent g){
//		this.g = g;
//	}
//	
//	public synchronized GraphComponent getGraphComponent(){
//		return g;
//	}
	
//	public synchronized void setUpdateEnabled(boolean b){
//		update = b;
//	}
//	
//	
//	public synchronized boolean isUpdateEnabled(){
//		return update;
//	}
	
	private void loadGraph(){
//		int result = fileChooser.showDialog(GUI.this, "Load");
//		if(result == JFileChooser.APPROVE_OPTION)
			try{
//				g = new GraphComponent(GraphIO.loadGraph(fileChooser.getSelectedFile().getAbsolutePath()));
				g = new GraphComponent(GraphIO.loadGraph("test.graph"));
				this.getContentPane().add(g);
				this.revalidate();
				g.sortNodes();
				update = true;
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
		g.startLoop();
	}

}

