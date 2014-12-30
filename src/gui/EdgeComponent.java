package gui;

import java.awt.Graphics;

public class EdgeComponent {
	
	private NodeComponent start, end;
	
	public EdgeComponent(NodeComponent start, NodeComponent end){
		this.start = start;
		this.end = end;
	}
	
	public void paintEdge(Graphics g){
		g.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
	}
	

}
