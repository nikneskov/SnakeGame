package model;

import java.util.ArrayList;

public class Snake implements Cloneable{

	private int bodySize = 1;
	private ArrayList<SnakePart> snakeParts = new ArrayList<SnakePart>();

	public int getBodySize() {
		return bodySize;
	}
	public void setBodySize(int bodySize) {
		this.bodySize = bodySize;
	}

	public Snake(){
		snakeParts.add(new SnakePart(new Location (2, 1), CardinalDirection.EAST, CardinalDirection.NONE));
		snakeParts.add(new SnakePart(new Location (1, 1), CardinalDirection.EAST, CardinalDirection.EAST));
	}

	public ArrayList<Location> getLocations(){
		ArrayList<Location> locations = new ArrayList<Location>();
		for(SnakePart part : snakeParts){
			locations.add(part.getLocation());
		}
		return locations;
	}

	public void updateDirection(CardinalDirection direction){
		for(int i = snakeParts.size() - 1; i > 0; i--){

			//set the direction of the tail parts to the direction of the head parts (tail follows head).
			snakeParts.get(i).setDirection(snakeParts.get(i-1).getDirection());
		}
		snakeParts.get(0).setDirection(direction);
	}

	public void move(){
		for(SnakePart part : snakeParts){
			switch(part.getDirection()){
			case NORTH:
				part.getLocation().setY(part.getLocation().getY() - 1);
				break;
			case SOUTH:
				part.getLocation().setY(part.getLocation().getY() + 1);
				break;
			case EAST:
				part.getLocation().setX(part.getLocation().getX() + 1);
				break;
			case WEST:
				part.getLocation().setX(part.getLocation().getX() - 1);
				break;
			default:
				break;
			}
		}
	}

	public void eatApple(){
		SnakePart tail = snakeParts.get(snakeParts.size()-1);
		Location newLocation = null;
		//TODO fix this so that it won't add a part if there is a wall near the end
		switch(tail.getDirection()){
		case NORTH:
			newLocation = new Location(tail.getLocation().getX(), tail.getLocation().getY() + 1);
			snakeParts.add(new SnakePart(newLocation, CardinalDirection.NORTH, CardinalDirection.NORTH));
			break;
		case SOUTH:
			newLocation = new Location(tail.getLocation().getX(), tail.getLocation().getY() - 1);
			snakeParts.add(new SnakePart(newLocation, CardinalDirection.SOUTH, CardinalDirection.SOUTH));
			break;
		case EAST:
			newLocation = new Location(tail.getLocation().getX() - 1, tail.getLocation().getY());
			snakeParts.add(new SnakePart(newLocation, CardinalDirection.EAST, CardinalDirection.EAST));
			break;
		case WEST:
			newLocation = new Location(tail.getLocation().getX() + 1, tail.getLocation().getY());
			snakeParts.add(new SnakePart(newLocation, CardinalDirection.WEST, CardinalDirection.WEST));
			break;
		default:
			break;
		}
		System.out.println(this.getClass() + ": " + newLocation.toString());
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
