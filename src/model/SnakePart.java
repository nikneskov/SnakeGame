package model;

public class SnakePart extends GameElement{
	
	CardinalDirection nextDirection;
	
	public CardinalDirection getNextDirection() {
		return nextDirection;
	}

	public void setNextDirection(CardinalDirection nextDirection) {
		this.nextDirection = nextDirection;
	}

	public SnakePart(Location location, CardinalDirection direction, CardinalDirection nextDirection){
		super(location, direction);
		this.nextDirection = nextDirection;
	}
}
