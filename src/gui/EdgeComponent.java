package gui;

import java.awt.Graphics;

public class EdgeComponent {
	
	private static boolean showValues = false;
	
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
		GVector pos = end.getPosition().subV(start.getPosition());
		pos.mul(0.5);
		pos.add(start.getPosition());
		if(showValues) g.drawString(String.valueOf(value),(int) pos.getX(),(int) pos.getY());
	}

	public static boolean showValues() {
		return showValues;
	}

	public static void setShowValues(boolean showValues) {
		EdgeComponent.showValues = showValues;
	}

	public NodeComponent getStart() {
		return start;
	}

	public NodeComponent getEnd() {
		return end;
	}
	

}
