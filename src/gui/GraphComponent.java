package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import graph.Edge;
import graph.Graph;
import graph.Node;

public class GraphComponent extends JPanel {
	
	private Graph g;
	private List<PhysikNode> nodes;
	private Map<String, PhysikNode> nodesMap;
//	private List<EdgeComponent> edges;
	private int counterX;
	private int counterY;
	
	public GraphComponent(Graph g){
		super();
		this.g = g;
		nodes = new ArrayList<>();
		nodesMap = new HashMap<>();
//		edges = new ArrayList<>();
//		setPreferredSize(new Dimension(, Integer.MAX_VALUE));
		counterX = 0;
		counterY = 30;
		for(Node n : g.getAllNodes()){
			PhysikNode nc = new PhysikNode(n);
			addNode(nc);
		}
		for(NodeComponent n : nodes){
			for(Edge e : n.getNode().edges){
				n.connectTo(nodesMap.get(e.target.name), e.costs);
			}
		}
//		repaint();
	}
	
	private void addNode(PhysikNode n){
		n.setParent(this);
		nodes.add(n);
		nodesMap.put(n.getNode().name, n);
	}
	
	public void sortNodes(){
//		int i = 0;
//		for(NodeComponent n : nodes){
//			if(counterX + 30 + n.getWidth() > this.getWidth() - 30 || i++ > 5){
//				counterX = 0;
//				i = 0;
//				counterY += 30;	
//			}
//			n.setY(counterY);
//			n.setX(counterX += 30);
//		}
//		counterX = 0;
//		counterY = 30;
		for(NodeComponent n : nodes){
			n.getPosition().setX(Math.random() * getWidth());
			n.getPosition().setY(Math.random() * getHeight());
		}
	}
	
	public void update(){		
		for(PhysikNode n1 : nodes){
			for(PhysikNode n2 : nodes) if(n1 != n2){
				n1.applyForceFrom(n2);
//				System.out.println(n1.getNode().name + " -> " + n2.getNode().name);
			}
		}
		for(NodeComponent n : nodes){
//			System.out.println(n.getNode().name + " " + n.getPosition());
			n.update();
		}
//		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for(NodeComponent n : nodes){
			n.paintEdges(g);
		}
		for(NodeComponent n : nodes){
			n.paintNode(g);
		}		
	}

}
