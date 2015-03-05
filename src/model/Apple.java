package model;

import java.util.ArrayList;
import java.util.Random;

public class Apple extends GameElement {
	
	public Apple(){
		super(new Location (1, 1), CardinalDirection.NONE);		
	}
	
	public void eaten(Snake snake, int boardSize){
		ArrayList<Location> locations = snake.getLocations();
		
		Random rand = new Random();
		int randomNum1 = rand.nextInt(boardSize - 2) + 1;
		int randomNum2 = rand.nextInt(boardSize - 2) + 1;
		this.setLocation(new Location(randomNum1, randomNum2));
		
		boolean didItWork = false;
		
		//Basically just keeps generating random locations until it finds one that is NOT on top of the snake.
		while(didItWork == false){
			for(Location location : locations){
				if(location.equals(this.getLocation())){
					rand = new Random();
					randomNum1 = rand.nextInt(boardSize - 2) + 1;
					randomNum2 = rand.nextInt(boardSize - 2) + 1;
					this.setLocation(new Location(randomNum1, randomNum2));
					continue;
				}else{didItWork = true;}
			}
		}
		
		System.out.println(this.getClass() + this.getLocation().toString());
	}
}
