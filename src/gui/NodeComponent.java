package gui;	

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import graph.Node;

public class NodeComponent {

	private Node n;
	private GVector pos;
	private int width, height;
	private List<EdgeComponent> edges;
	private JComponent parent;

	public NodeComponent(Node n, int x, int y, int width, int height){
		this.n = n;
		pos = new GVector(x, y);
		edges = new ArrayList<>();
		this.width = width;
		this.height = height;
	}

	public NodeComponent(Node n){
		this(n, 0, 0, 20, 20);
	}

	public void update(){

	}

	public void paintNode(Graphics g){
		g.setColor(Color.BLUE);
		g.fillOval((int)pos.getX() - width/2,(int) pos.getY() - height/2, width, height);
		g.setColor(Color.BLACK);
		g.drawOval((int) pos.getX() - width/2, (int) pos.getY() - height/2, width, height);
	}

	public void paintEdges(Graphics g){
		for(EdgeComponent e : edges) e.paintEdge(g);
	}

	public void connectTo(NodeComponent n, double cost){
		edges.add(new EdgeComponent(this, n, cost));
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
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
}
