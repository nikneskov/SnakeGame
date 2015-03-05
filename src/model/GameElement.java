package model;

public abstract class GameElement {
	
	Location location;
	CardinalDirection direction;
	
	public CardinalDirection getDirection() {
		return direction;
	}
	public void setDirection(CardinalDirection direction) {
		this.direction = direction;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public GameElement(Location location, CardinalDirection direction){
		this.location = location;
		this.direction = direction;
	}
}
