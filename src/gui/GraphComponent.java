package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
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
		Collection<Node> c = g.getAllNodes();
		Iterator<Node> i = c.iterator();
		while(i.hasNext()){
			PhysikNode nc = new PhysikNode(i.next());
			addNode(nc);
		}
		for(int j = 0; j < nodes.size(); j++){
			for(Edge e : nodes.get(j).getNode().edges){
				nodes.get(j).connectTo(nodesMap.get(e.target.name), e.costs);
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
			JOptionPane.showMessageDialog(getParent(), "Es existiert bereits ein Knoten mit diesem Namen",
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
		for(int j = 0; j < nodes.size(); j++){
			for(int i = 0; i < nodes.get(j).getEdges().size(); i++){
				if(nodes.get(j).getEdges().get(i).getEnd() == n) nodes.get(j).getEdges().remove(i);
			}
		}
		nodes.remove(n);
		nodesMap.remove(n.getNode().name);
	}

	public NodeComponent getNodeAt(double x, double y){
		for(int i = 0; i < nodes.size(); i++){
			if(nodes.get(i).isAt(x, y)) return nodes.get(i);
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
			g.getNode(start.getNode().name).connectTo(g.getNode(target.getNode().name), cost);
			start.connectTo(target, cost);
		}
		else JOptionPane.showMessageDialog(this, "Kante existiert bereits.", "Fehler", JOptionPane.ERROR_MESSAGE);
	}
	public void shuffleNodes(){
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

	public Graph getGraph() {
		return g;
	}

}
