package gui;

public class GVector {
	
	private double x, y;
	
	public GVector() {
		this.setX( 0.0 );
		this.setY( 0.0 );
	}
	
	public GVector( double x, double y ) {
		this.setX( x );
		this.setY( y );
	}
	
	public void add( GVector v ) {
		this.setX( this.x + v.x );
		this.setY( this.y + v.y );
	}
	
	public GVector addV( GVector v ) {
		return new GVector( this.x + v.x, this.y + v.y );
	}
	
	public void sub( GVector v ) {
		setX( this.x - v.x );
		setY( this.y - v.y );
	}
	
	public GVector subV( GVector v ) {
		return new GVector( this.x - v.x, this.y - v.y );
	}
	
	public void mul( double s ) {
		setX( this.x * s );
		setY( this.y * s );
	}
	
	public GVector mulV( double s ) {
		return new GVector( this.x * s, this.y * s );
	}
	
	public void mul( GVector v ) {
		this.setX(this.x * v.getX());
		this.setY(this.y * v.getY());
	}
	
	public GVector mulV( GVector v ) {
		return new GVector( this.x * v.getX(), this.y * v.getX() );
	}
	
	public void div( double s ) {
		setX( this.x / s );
		setY( this.y / s );
	}
	
	public GVector divV( double s ) {
		return new GVector( this.x / s, this.y / s );
	}
	
	public void pow( double s ) {
		setX( Math.pow( x, s ) );
		setY( Math.pow( y, s ) );
	}
	
	public GVector powV( double s ) {
		return new GVector( Math.pow( this.x, s ), Math.pow( this.y, s ) );
	}
	
	public double getDotProduct(GVector v) {
		return (this.x * v.x + this.y * v.y);
	}
	
	public double length() {
		return Math.sqrt( this.lengthSqrt() );
	}
	
	public double lengthSqrt() {
		return ( this.x * this.x + this.y * this.y );
	}
	
	public void normalize() {
		this.div( this.length() );
	}
	
	public GVector normalizeV() {
		return this.divV( this.length() );
	}
	
	public double getX() {
		return this.x;
	}
	
	public void setX( double x ) {
		this.x = x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public void setY( double y ) {
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "{"+x+";"+y+"]";
	}
}