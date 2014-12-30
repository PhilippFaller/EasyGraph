package gui;	

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;
import graph.Node;

public class NodeComponent {
	
	private Node n;
	private int x, y;
	private int width, height;
	
	public NodeComponent(Node n, int x, int y, int width, int height){
		this.n = n;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public NodeComponent(Node n){
		this(n, 0, 0, 20, 20);
	}
	
	public void paintNode(Graphics g){
		g.setColor(Color.BLUE);
		g.fillOval(x - width/2, y - height/2, width, height);
		g.setColor(Color.BLACK);
		g.drawOval(x - width/2, y - height/2, width, height);
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
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
