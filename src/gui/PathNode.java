package gui;

import java.awt.Color;
import java.awt.Graphics;

import graph.Node;

public class PathNode extends PhysikNode {

	public PathNode(PhysikNode n) {
		super(n.getNode());
		this.setAcceleration(n.getAcceleration());
		this.setDragged(n.isDragged());
		this.setParent(n.getParent());
		this.setPosition(n.getPosition());
		this.setSelected(n.isSelected());
		this.setVelocity(n.getVelocity());
	}
	
	@Override
	public void paintNode(Graphics g){
		g.setColor(Color.BLACK);
		g.fillOval((int) getPosition().getX() - getDiameter()/2,
				(int) getPosition().getY() - getDiameter()/2, getDiameter(), getDiameter());
		g.setColor(Color.ORANGE);
		g.fillOval((int)getPosition().getX() - getDiameter()/2 + getDiameter()/10,
				(int) getPosition().getY() - getDiameter()/2 + getDiameter()/10,
				getDiameter() - getDiameter()/5, getDiameter() - getDiameter()/5);
		g.setColor(Color.BLACK);
		if(getNode().name.length() > 3)
			g.drawString(getNode().name.substring(0, 3),
					(int) getPosition().getX() - getDiameter() / 3,(int) getPosition().getY());
		else g.drawString(getNode().name,(int) getPosition().getX() - getDiameter() / 3,(int) getPosition().getY());
	}

}
