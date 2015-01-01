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
	private GVector velocity, acceleration;
	private List<EdgeComponent> edges;
	
	public NodeComponent(Node n, int x, int y, int width, int height){
		this.n = n;
		pos = new GVector(x, y);
		velocity = new GVector();
		acceleration = new GVector();
		edges = new ArrayList<>();
		this.width = width;
		this.height = height;
	}

	public NodeComponent(Node n){
		this(n, 0, 0, 20, 20);
	}
	
	public void update(long deltaT){
		getVelocity().add(getAcceleration().mulV(0.01 * deltaT));
		getPosition().add(getVelocity().mulV(0.01 * deltaT));
//		System.out.println(acceleration + " a");
//		System.out.println(velocity + " v");
//		System.out.println(pos + " pos");
	}
	
	public void applyForceTo(NodeComponent n){
		GVector diff = this.getPosition().subV(n.getPosition());
		diff.div(Math.pow(diff.length(), 3));
		diff.mul(-1);
		n.getAcceleration().add(diff);
//		System.out.println(this.getPosition() + " thisPos");
//		System.out.println(n.getPosition() + " nPos");
//		System.out.println(this.getPosition().subV(n.getPosition()) + this.getNode().name + " -> " + n.getNode().name);
//		System.out.println(diff);
//		System.out.println(this.getAcceleration().getX() + " this x");
//		System.out.println(n.getAcceleration().getX() + " n x");
	}

	public void paintNode(Graphics g){
		g.setColor(Color.BLUE);
		g.fillOval((int)pos.getX() - width/2,(int) pos.getY() - height/2, width, height);
		g.setColor(Color.BLACK);
//		System.out.println((int)pos.getX() - width/2 + " x");
//		System.out.println((int) pos.getY() - height/2 + " y");
		g.drawOval((int) pos.getX() - width/2, (int) pos.getY() - height/2, width, height);
	}
	
	public void paintEdges(Graphics g){
		for(EdgeComponent e : edges) e.paintEdge(g);
	}
	
	public void connectTo(NodeComponent n, double cost){
		edges.add(new EdgeComponent(this, n, cost));
	}

	public GVector getVelocity() {
		return velocity;
	}

	public void setVelocity(GVector v) {
		this.velocity = v;
	}

	public GVector getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(GVector a) {
		this.acceleration = a;
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
