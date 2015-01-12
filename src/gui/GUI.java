package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import java.util.NoSuchElementException;

import graph.Graph;
import graph.Node;

import javax.swing.ImageIcon;
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

	private boolean update;
	private boolean newFile;
	private GraphComponent g;
	private int millisBetweenRepaint;
	private JTextField startField, targetField;
	private NodeComponent selectedNode;
	private JLabel nameLabel;
	private JMenuItem movement;
	private JMenuItem removeNode;
	private JMenuItem showValue;

	public GUI(String name){
		super(name);
		setSize(400,400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		g = new GraphComponent();
		getContentPane().add(g);
		update = true;
		newFile = true;
		millisBetweenRepaint = 17;
		selectedNode = null;

		nameLabel = new JLabel("");
		nameLabel.setHorizontalAlignment(JLabel.RIGHT);
		getContentPane().add(nameLabel, BorderLayout.NORTH);
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
		JMenuItem saveFile = new JMenuItem("Datei speichern");
		JMenuItem addNode = new JMenuItem("Knoten hinzufügen");
		removeNode = new JMenuItem("Knoten entfernen");
		removeNode.setEnabled(false);
		JMenuItem about = new JMenuItem("Über");
		movement = new JMenuItem("Sortieren anhalten");
		JMenuItem saveFileAs = new JMenuItem("Speichern unter");
		showValue = new JMenuItem("Kantenwerte anzeigen");
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
		removeNode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				removeNode();
			}
		});
		saveFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveFile();
			}
		});
		movement.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				alterMovement();
			}
		});
		about.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(GUI.this, "Geschrieben von Philipp Faller", "Über", JOptionPane.PLAIN_MESSAGE);
			}
		});
		saveFileAs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveFileAs();
			}
		});
		showValue.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				alterShowValues();
			}
		});
		file.add(newFile);
		file.add(loadFile);
		file.add(saveFile);
		file.add(saveFileAs);
		edit.add(addNode);
		edit.add(removeNode);
		edit.add(movement);
		edit.add(showValue);
		menu.add(file);
		menu.add(edit);
		menu.add(help);
		help.add(about);
		setJMenuBar(menu);

		//		addKeyListener(new KeyListener() {
		//
		//			@Override
		//			public void keyTyped(KeyEvent e) {
		//				if(e.getKeyChar() == CTRL_L) loadGraph();
		//				else if(e.getKeyChar() == ' ') update = !update;
		//				else if(e.getKeyChar() == 'n') 	addNode(); 
		//			}
		//
		//			@Override
		//			public void keyReleased(KeyEvent e) {
		//				// You do nuthin', John Snow!				
		//			}
		//
		//			@Override
		//			public void keyPressed(KeyEvent e) {
		//				// You do nuthin', John Snow!
		//
		//			}
		//		});
		getContentPane().addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				NodeComponent n = null;
				if(g != null) n = g.getNodeAt(e.getX(), e.getY());
				if(n != null) n.setDragged(false);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1){
					NodeComponent n = null;
					if(g != null) n = g.getNodeAt(e.getX(), e.getY());
					if(n != null) n.setDragged(true);
				}
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
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON3){
					NodeComponent n = null;
					if(g != null) n = g.getNodeAt(e.getX(), e.getY());
					if(selectedNode != null){
						if(e.isControlDown() && n != null){
							newEdge(selectedNode, n);
							return;
						}
						selectedNode.setSelected(false);
					}
					if(n != null){
						n.setSelected(true);
						nameLabel.setText(n.getNode().name);
						removeNode.setEnabled(true);
					}
					else{
						nameLabel.setText("");
						removeNode.setEnabled(false);
					}
					selectedNode = n;
				}

			}
		});

		setIconImage((new ImageIcon(getClass().getResource("icon.png")).getImage()));
//		System.out.println(this.getIconImages());
		setVisible(true);
	}



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
	}

	private void findWay(String start, String target){
		if(g != null) g.findWay(start, target);
	}

	private void addNode(){
		if(g == null) return;
		String name = JOptionPane.showInputDialog(this, "Geben sie den Name des neuen Knoten ein.", "Neuer Knoten");
		if(name == null) return;
		update = false;
		g.addNewNode(new PhysikNode(new Node(name), Math.random() * g.getWidth(), Math.random() * g.getHeight()));
		update = true;
	}

	private void removeNode(){
		nameLabel.setText("");
		g.removeNode(selectedNode);
	}

	private void alterMovement(){
		if(PhysikNode.isForceActive()){
			PhysikNode.setForceActive(false);
			movement.setText("Starte sortieren");
		}
		else{
			PhysikNode.setForceActive(true);
			movement.setText("Sortieren anhalten");
		}
	}

	private void newEdge(NodeComponent start, NodeComponent target){
		g.connetct(start, target);
	}

	private void loadGraph(){
		int result = fileChooser.showDialog(GUI.this, "Load");
		if(result == JFileChooser.APPROVE_OPTION){
			try{
				update = false;
				if(g != null) getContentPane().remove(g);
				g = new GraphComponent(GraphIO.loadGraph(fileChooser.getSelectedFile().getAbsolutePath()));
				this.getContentPane().add(g);
				this.revalidate();
				g.shuffleNodes();
				update = true;
				newFile = false;
			}

			catch(Exception e){
				JOptionPane.showMessageDialog(this, "Fehler beim lesen der Datei \""
						+ fileChooser.getSelectedFile().getAbsolutePath() + "\".",
						"Fehler", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void newFile(){
		if(g != null) getContentPane().remove(g);
		g = new GraphComponent();
		this.getContentPane().add(g);
		this.revalidate();
		update = true;
		newFile = true;
	}

	private void saveFile(){
		try{
			if(g.getGraph().amountOfNodes() <= 1 ){
				JOptionPane.showMessageDialog(this, "Es werden mindestens 2 verbundene Knoten zu speichern benötigt.",
						"Speichern", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			int result = 0;
			if(newFile)fileChooser.showSaveDialog(this);
			if(result == JFileChooser.APPROVE_OPTION || !newFile) 
				GraphIO.saveGraph(g.getGraph(), fileChooser.getSelectedFile().getAbsolutePath());
			newFile = false;
		}catch (Exception e){
			JOptionPane.showMessageDialog(this, "Fehler beim speichern der Datei.",
					"Fehler", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void saveFileAs(){
		try{
			if(g.getGraph().amountOfNodes() <= 1 ){
				JOptionPane.showMessageDialog(this, "Es werden mindestens 2 verbundene Knoten zu speichern benötigt.",
						"Speichern", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			int result = 0;
			fileChooser.showSaveDialog(this);
			if(result == JFileChooser.APPROVE_OPTION) 
				GraphIO.saveGraph(g.getGraph(), fileChooser.getSelectedFile().getAbsolutePath());
			newFile = false;
		}catch (Exception e){
			JOptionPane.showMessageDialog(this, "Fehler beim speichern der Datei.",
					"Fehler", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void alterShowValues(){
		if(EdgeComponent.showValues()){
			EdgeComponent.setShowValues(false);
			showValue.setText("Kantenwerte anzeigen");
		}else{
			EdgeComponent.setShowValues(true);
			showValue.setText("Kantenwerte verstecken");
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

