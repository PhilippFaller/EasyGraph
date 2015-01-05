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
	private JComponent parent;

	public NodeComponent(Node n, int x, int y, int width, int height, JComponent parent){
		this.n = n;
		pos = new GVector(x, y);
		velocity = new GVector();
		acceleration = new GVector();
		edges = new ArrayList<>();
		this.width = width;
		this.height = height;
		this.parent = parent;
	}

	public NodeComponent(Node n, JComponent parent){
		this(n, 0, 0, 20, 20, parent);
	}

	public void update(long deltaT){
		if(isOutOfBorderX()) turnAroundX();
		if(isOutOfBorderY()) turnAroundY();
		//		else{
		//			System.out.println(acceleration + " a " + n.name);
		//			System.out.println(velocity + " v " + n.name);
		//			getVelocity().add(getAcceleration().mulV(0.01 * deltaT));
		//			getPosition().add(getVelocity().mulV(0.01 * deltaT));
		applyRepelFrom(new GVector(this.getX(), 0));
		applyRepelFrom(new GVector(this.getX(), parent.getWidth()));
		applyRepelFrom(new GVector(0, this.getY()));
		applyRepelFrom(new GVector(parent.getHeight(), this.getY()));
		getAcceleration().add(getVelocity().mulV(-0.25));
		getVelocity().add(getAcceleration());
		getPosition().add(getVelocity());
		setAcceleration(new GVector());
		//		}
		//		System.out.println(acceleration + " a");
		//		System.out.println(velocity + " v");
		//		System.out.println(pos + " pos");
	}

	public void applyForceFrom(NodeComponent n){
//		System.out.println(this.n.name);
//		System.out.println("\t" + n.getNode().name);
//		System.out.println("\ta0: " + acceleration);
//		System.out.println("\tv0: " + velocity);
				if(n.getNode().isConnectedWith(getNode())){
					applyAttractionFrom(n.getPosition());  
//					System.out.println("\taAttract: " + acceleration);
//					System.out.println("\tvAttract: " + velocity);
				}
				applyRepelFrom(n.getPosition());
//				System.out.println("\taRepel: " + acceleration);
//				System.out.println("\tvRepel: " + velocity);
	}

	private void applyRepelFrom(GVector position){
		GVector diff = position.subV(this.getPosition());
//		System.out.println(diff + " abstand");
		diff.div(Math.pow(diff.length(), 3));
		/*if(!this.getNode().isConnectedWith(n.getNode()))*/diff.mul(-200);
		this.getAcceleration().add(diff);
		//		System.out.println(this.getPosition() + " thisPos");
//				System.out.println(n.getPosition() + " nPos");
		//		System.out.println(this.getPosition().subV(n.getPosition()) + this.getNode().name + " -> " + n.getNode().name);
		//		System.out.println(diff);
		//		System.out.println(this.getAcceleration().getX() + " this x");
		//		System.out.println(n.getAcceleration().getX() + " n x");

	}

	private void applyAttractionFrom(GVector position){
		GVector diff = position.subV(this.getPosition());
//		System.out.println(diff + " r");
		diff.pow(3);
		diff.div(diff.length() * 15);
//		System.out.println(diff + " a");
		this.getAcceleration().add(diff);
	}

	private boolean isOutOfBorderX(){
		return pos.getX() + width/2 > parent.getWidth() || pos.getX() - width/2 < 0;
		//				|| pos.getY() + width/2 > parent.getHeight() || pos.getY() - width/2 < 0;
	}

	private boolean isOutOfBorderY(){
		return pos.getY() + width/2 > parent.getHeight() || pos.getY() - width/2 < 0;
	}

	private void turnAroundX(){
		getAcceleration().setX(getAcceleration().getX() * -1);
		getVelocity().setX(getVelocity().getX() * -1);
	}
	private void turnAroundY(){
		getAcceleration().setY(getAcceleration().getY() * -1);
		getVelocity().setY(getVelocity().getY() * -1);
	}

	public void paintNode(Graphics g){
		//		System.out.println("repaint()");
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
