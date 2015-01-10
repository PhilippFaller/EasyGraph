package gui;	

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import graph.Node;

public class NodeComponent {
	
	private static int diameter = 40;

	private Node n;
	private GVector pos;
	private List<EdgeComponent> edges;
	private JComponent parent;
	private boolean dragged;


	public NodeComponent(Node n){
		this(n, 0, 0);
	}
	
	public NodeComponent(Node n, double x, double y){
		this.n = n;
		pos = new GVector(x, y);
		edges = new ArrayList<>();
		dragged = false;
	}

	public void update(){

	}

	public void paintNode(Graphics g){
		
		g.setColor(Color.BLACK);
		g.fillOval((int) pos.getX() - diameter/2, (int) pos.getY() - diameter/2, diameter, diameter);
		g.setColor(Color.BLUE);
		g.fillOval((int)pos.getX() - diameter/2 + diameter/10,(int) pos.getY() - diameter/2 + diameter/10,
				diameter - diameter/5, diameter - diameter/5);
		g.setColor(Color.BLACK);
		if(n.name.length() > 3)
			g.drawString(n.name.substring(0, 3),(int) pos.getX() - diameter / 3,(int) pos.getY());
		else g.drawString(n.name,(int) pos.getX() - diameter / 3,(int) pos.getY());
	}

	public void paintEdges(Graphics g){
		for(EdgeComponent e : edges) e.paintEdge(g);
	}

	public void connectTo(NodeComponent n, double cost){
		edges.add(new EdgeComponent(this, n, cost));
	}
	
	public boolean isAt(double x, double y){
		if(getPosition().subV(new GVector(x, y)).length() < diameter/2) return true;
		return false;
	}

	public JComponent getParent() {
		return parent;
	}

	public void setParent(JComponent parent) {
		this.parent = parent;
	}

	public GVector getPosition() {
		return pos;
	}

	public void setPosition(GVector pos) {
		this.pos = pos;
	}

	public Node getNode(){
		return n;
	}

	public double getX() {
		return pos.getX();
	}
	public void setX(double x) {
		pos.setX(x);
	}
	public double getY() {
		return pos.getY();
	}
	public void setY(double y) {
		pos.setY(y);
	}

	public boolean isDragged() {
		return dragged;
	}

	public void setDragged(boolean dragged) {
		this.dragged = dragged;
	}

	public static int getDiameter() {
		return diameter;
	}

	public static void setDiameter(int diameter) {
		NodeComponent.diameter = diameter;
	}
}
