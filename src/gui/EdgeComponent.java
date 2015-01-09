package gui;

import java.awt.Graphics;

public class EdgeComponent {
	
	private NodeComponent start, end;
	private double value;
	
	public EdgeComponent(NodeComponent start, NodeComponent end, double value){
		this.start = start;
		this.end = end;
		this.value = value;
	}
	
	public double getValue() {
		return value;
	}

	public void paintEdge(Graphics g){
		g.drawLine((int)start.getPosition().getX(),(int) start.getPosition().getY(),
				(int) end.getPosition().getX(),(int) end.getPosition().getY());
	}

	public NodeComponent getStart() {
		return start;
	}

	public NodeComponent getEnd() {
		return end;
	}
	

}
