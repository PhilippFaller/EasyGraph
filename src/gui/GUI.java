package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.NoSuchElementException;

import graph.Graph;
import graph.Node;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import data.GraphIO;

public class GUI extends JFrame {

	private final JFileChooser fileChooser = new JFileChooser();
	private final char CTRL_L = 12;


	private boolean update;
	private GraphComponent g;
	private int millisBetweenRepaint;
	private JTextField startField, targetField;

	public GUI(String name){
		super(name);
		setSize(400,400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		g = null;
		update = false;
		millisBetweenRepaint = 17;
		
		JPanel southPanel = new JPanel();
		JButton findButton = new JButton("Finde");
		findButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				findWay(startField.getText(), targetField.getText());
			}
		});
		startField = new JTextField(10);
		targetField = new JTextField(10);
		southPanel.setLayout(new FlowLayout());
		southPanel.add(new JLabel("Start:"));
		southPanel.add(startField);
		southPanel.add(new JLabel("Ziel:"));
		southPanel.add(targetField);
		southPanel.add(findButton);
		this.getContentPane().add(southPanel, BorderLayout.SOUTH);
		
		JMenuBar menu = new JMenuBar();
		JMenu file = new JMenu("Datei");
		JMenu edit = new JMenu("Bearbeiten");
		JMenu help = new JMenu("Hilfe");
		JMenuItem newFile = new JMenuItem("Neue Datei");
		JMenuItem loadFile = new JMenuItem("Lade Datei");
		JMenuItem addNode = new JMenuItem("Knoten hinzufügen");
		newFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				newFile();				
			}
		});
		loadFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadGraph();
			}
		});
		addNode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addNode();
			}
		});
		file.add(newFile);
		file.add(loadFile);
		edit.add(addNode);
		menu.add(file);
		menu.add(edit);
		menu.add(help);
		setJMenuBar(menu);

		addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == CTRL_L) loadGraph();
				else if(e.getKeyChar() == ' ') update = !update;
				else if(e.getKeyChar() == 'n') 	addNode(); 
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
	
	private void findWay(String start, String target){
		if(g != null) g.findWay(start, target);
	}

	private void addNode(){
		if(g == null) return;
		String name = JOptionPane.showInputDialog(this, "Geben sie den Name des neuen Knoten ein.", "Neuer Knoten");
		if(name == null) return;
		g.addNewNode(new PhysikNode(new Node(name), Math.random() * getWidth(), Math.random() * getHeight()));
	}

	private void loadGraph(){
		//		int result = fileChooser.showDialog(GUI.this, "Load");
		//		if(result == JFileChooser.APPROVE_OPTION)
		try{
			//				g = new GraphComponent(GraphIO.loadGraph(fileChooser.getSelectedFile().getAbsolutePath()));
			update = false;
			if(g != null) getContentPane().remove(g);
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
	
	private void newFile(){
		if(g != null) getContentPane().remove(g);
		g = new GraphComponent();
		this.getContentPane().add(g);
		this.revalidate();
		update = true;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GUI g = new GUI("Graph");
		g.startLoop();
	}

}

