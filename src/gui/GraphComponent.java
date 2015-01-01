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
	private List<NodeComponent> nodes;
	private Map<String, NodeComponent> nodesMap;
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
			NodeComponent nc = new NodeComponent(n);
			addNode(nc);
		}
		for(NodeComponent n : nodes){
			for(Edge e : n.getNode().edges){
				n.connectTo(nodesMap.get(e.target.name), e.costs);
			}
		}
//		repaint();
	}
	
	private void addNode(NodeComponent n){
		nodes.add(n);
		nodesMap.put(n.getNode().name, n);
	}
	
	public void sortNodes(){
		for(NodeComponent n : nodes){
			if(counterX + 30 + n.getWidth() > this.getWidth() - 30){
				counterX = 0;
				counterY += 30;	
			}
			n.setY(counterY);
			n.setX(counterX += 30);
		}
		counterX = 0;
		counterY = 30;
	}
	
	public void update(long deltaT){
		for(NodeComponent n1 : nodes){
			for(NodeComponent n2 : nodes) if(n1 != n2){
				n1.applyForceTo(n2);
//				System.out.println(n1.getNode().name + " -> " + n2.getNode().name);
			}
		}
		for(NodeComponent n : nodes){
			n.update(deltaT);
		}
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
//		sortNodes();
		for(NodeComponent n : nodes){
			n.paintEdges(g);
		}
		for(NodeComponent n : nodes){
			n.paintNode(g);
		}		
	}

}
