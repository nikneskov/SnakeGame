package model;

public class Location {
	
	private int x;
	private int y;
	private Location previousLocation;
	private double value;
	
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public Location getPreviousLocation() {
		return previousLocation;
	}
	public void setPreviousLocation(Location previousLocation) {
		this.previousLocation = previousLocation;
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
	
	@Override
	public String toString(){
		return "x: " + x + " y: " + y;
	}
	
	@Override
	public boolean equals(Object obs){
		Location location ;
		if(obs instanceof Location){location = (Location)obs;}
		else{return false;}
		
		if(this.getX() == location.getX() && this.getY() == location.getY()){
			return true;
		}
		return false;
	}
	
	public Location(Location location){
		this(location.getX(), location.getY());
	}
	
	public Location (int x, int y){
		this.x = x; 
		this.y = y;
	}
}
