package gui;

import graph.Node;

public class PhysikNode extends NodeComponent {

	private GVector velocity, acceleration;
	private static boolean forceActive = true;;

	public PhysikNode(Node n) {
		super(n);
		velocity = new GVector();
		acceleration = new GVector();
	}

	public PhysikNode(Node n, double x, double y){
		super(n, x, y);
		velocity = new GVector();
		acceleration = new GVector();
	}

	@Override
	public void update(){
		if(isDragged()){
			try{
				getPosition().setX(getParent().getMousePosition().getX());
				getPosition().setY(getParent().getMousePosition().getY());
			}catch(NullPointerException e){};
			setAcceleration(new GVector());
		} else {
			if(forceActive){
				if(isOutOfBorderX()) turnAroundX();
				if(isOutOfBorderY()) turnAroundY();

				applyRepelFrom(new GVector(this.getX(), 0));
				applyRepelFrom(new GVector(this.getX(), getParent().getHeight()));
				applyRepelFrom(new GVector(0, this.getY()));
				applyRepelFrom(new GVector(getParent().getWidth(), this.getY()));
				getAcceleration().add(getVelocity().mulV(-0.25));
				getVelocity().add(getAcceleration());
				getPosition().add(getVelocity());
			}
			setAcceleration(new GVector());
		}
	}

	public void applyForceFrom(NodeComponent n){
		if(n.isConnectedTo(this)){
			applyAttractionFrom(n.getPosition());  
		}
		applyRepelFrom(n.getPosition());
	}

	private void applyRepelFrom(GVector position){
		GVector diff = position.subV(this.getPosition());
		diff.div(Math.pow(diff.length(), 3));
		diff.mul(-200);
		this.getAcceleration().add(diff);
	}

	private void applyAttractionFrom(GVector position){
		GVector diff = position.subV(this.getPosition());
		diff.pow(3);
		diff.div(diff.length() * 15);
		this.getAcceleration().add(diff);
	}

	private boolean isOutOfBorderX(){
		return getPosition().getX() + getDiameter()/2 > getParent().getWidth() || getPosition().getX() - getDiameter()/2 < 0;
	}

	private boolean isOutOfBorderY(){
		return getPosition().getY() + getDiameter()/2 > getParent().getHeight() || getPosition().getY() - getDiameter()/2 < 0;
	}

	private void turnAroundX(){
		getAcceleration().setX(getAcceleration().getX() * -1);
		getVelocity().setX(getVelocity().getX() * -1);
	}
	private void turnAroundY(){
		getAcceleration().setY(getAcceleration().getY() * -1);
		getVelocity().setY(getVelocity().getY() * -1);
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

	public static boolean isForceActive() {
		return forceActive;
	}

	public static void setForceActive(boolean forceActive) {
		PhysikNode.forceActive = forceActive;
	}
}
