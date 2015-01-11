package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import graph.Edge;
import graph.Graph;
import graph.Node;
import graph.SearchAlgorithm;
import gui.NodeComponent.State;

class GraphComponent extends JPanel {

	private Graph g;
	private List<PhysikNode> nodes;
	private Map<String, PhysikNode> nodesMap;

	public GraphComponent(Graph g){
		super();
		this.g = g;
		nodes = new CopyOnWriteArrayList<>();
		nodesMap = new ConcurrentHashMap<>();
		for(Node n : g.getAllNodes()){
			PhysikNode nc = new PhysikNode(n);
			addNode(nc);
		}
		for(NodeComponent n : nodes){
			for(Edge e : n.getNode().edges){
				n.connectTo(nodesMap.get(e.target.name), e.costs);
			}
		}
	}

	public GraphComponent(){
		this(new Graph());
	}

	public void addNode(PhysikNode n){
		if(nodesMap.containsKey(n.getNode().name)){
			JOptionPane.showMessageDialog(this, "Es existiert bereits ein Knoten mit diesem Namen",
					"Fehler", JOptionPane.ERROR_MESSAGE);
		}else{
			n.setParent(this);
			nodes.add(n);
			nodesMap.put(n.getNode().name, n);
		}
	}

	public void addNewNode(PhysikNode n){
		if(nodesMap.containsKey(n.getNode().name)){
			JOptionPane.showMessageDialog(this, "Es existiert bereits ein Knoten mit diesem Namen",
					"Fehler", JOptionPane.ERROR_MESSAGE);
		}else{
			n.setParent(this);
			nodes.add(n);
			nodesMap.put(n.getNode().name, n);
			g.newNode(n.getNode().name);
		}		
	}
	
	public void removeNode(NodeComponent n){
		g.removeNode(n.getNode().name);
		for(NodeComponent n1 : nodes){
			for(int i = 0; i < n1.getEdges().size(); i++){
				if(n1.getEdges().get(i).getEnd() == n) n1.getEdges().remove(i);
			}
		}
		nodes.remove(n);
		nodesMap.remove(n.getNode().name);
	}

	public NodeComponent getNodeAt(double x, double y){
		for(NodeComponent n : nodes){
			if(n.isAt(x, y)) return n;
		}
		return null;
	}

	public void findWay(String start, String target){
		for(NodeComponent n : nodes) n.setState(State.NORMAL);
		if(nodesMap.containsKey(start) && nodesMap.containsKey(target)){
			SearchAlgorithm a = new SearchAlgorithm(g);
			a.search(g.getNode(start), g.getNode(target));
			for(String s : a.getPath())	nodesMap.get(s).setState(State.WAYPOINT);
			nodesMap.get(start).setState(State.START);
			nodesMap.get(target).setState(State.TARGET);
		}
		//TODO ausgabe wenn knoten nich existiert
	}

	public void connetct(NodeComponent start, NodeComponent target){
		if(!start.getNode().isConnectedWith(target.getNode())){
			String input = JOptionPane.showInputDialog(this, "Geben sie die Kosten der neuen Kante ein." );
			double cost = 0;
			try{
				cost = Double.parseDouble(input);
			}catch(NumberFormatException e){
				JOptionPane.showMessageDialog(this, "Eingabe war keine Zahl.", "Fehler", JOptionPane.ERROR_MESSAGE);
				return;
			}
			start.getNode().connectTo(target.getNode(), cost);
			start.connectTo(target, cost);
		}
		else JOptionPane.showMessageDialog(this, "Kante existiert bereits.", "Fehler", JOptionPane.ERROR_MESSAGE);
	}
	public void sortNodes(){
		for(NodeComponent n : nodes){
			n.getPosition().setX(Math.random() * getWidth());
			n.getPosition().setY(Math.random() * getHeight());
		}
	}

	public void update(){		
		for(PhysikNode n1 : nodes){
			for(PhysikNode n2 : nodes) if(n1 != n2){
				n1.applyForceFrom(n2);
			}
		}
		for(NodeComponent n : nodes){
			n.update();
		}
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		for(NodeComponent n : nodes){
			n.paintEdges(g);
		}
		for(NodeComponent n : nodes){
			n.paintNode(g);
		}		
	}

}
