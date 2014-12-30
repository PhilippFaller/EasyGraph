package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import graph.Graph;
import graph.Node;

public class GraphComponent extends JPanel {
	
	private Graph g;
	private List<NodeComponent> nodes;
	private List<EdgeComponent> edges;
	private int counterX;
	private int counterY;
	
	public GraphComponent(Graph g){
		super();
		this.g = g;
		nodes = new ArrayList<>();
		edges = new ArrayList<>();
//		setPreferredSize(new Dimension(, Integer.MAX_VALUE));
		counterX = 0;
		counterY = 0;
		for(Node n : g.getAllNodes()){
			NodeComponent nc = new NodeComponent(n);
			addNode(nc);
			
		}
//		repaint();
	}
	
	private void addNode(NodeComponent n){
		nodes.add(n);
		//TODO Add Edges
	}
	
	private void sortNodes(){
		for(NodeComponent n : nodes){
			if(counterX + 30 + n.getWidth() > this.getWidth() - 30){
				counterX = 0;
				counterY += 30;
				n.setY(counterY);	
			}
			n.setX(counterX += 30);
		}
		counterX = 0;
		counterY = 0;
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		sortNodes();
		for(NodeComponent n : nodes){
			n.paintNode(g);
		}
	}

}
