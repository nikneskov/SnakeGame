package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import model.Board;
import model.CardinalDirection;
import model.GameState;
import model.Location;
import model.Model;
import view.View;

public class Controller implements KeyListener {

	View view;
	Model model;
	private int euclidian;
	private ArrayList<Location> path;
	private LinkedList<Location> applePaths;
	private LinkedList<Location> queue;
	private PriorityQueue<Location> frontier;

	public Controller(){
		this.view = new View(this);
		this.model = new Model(view);
		euclidian = 0;
	}

	private void resetGame(){
		this.view = new View(this);
		this.model = new Model(view);
	}



	private Location breadthFirstSearchInitialization(){

		queue = new LinkedList<Location>();
		Board charBoard = model.getBoard();
		char[][] board = charBoard.getBoard();
		int[][] visited = new int[charBoard.getBoardSize()][charBoard.getBoardSize()];
		Location headLocation = model.getSnake().getLocations().get(0);
		Location searchLocation = new Location(headLocation);
		queue.add(searchLocation);

		while(!queue.isEmpty()){

			searchLocation = queue.pop();
			int x = searchLocation.getX();
			int y = searchLocation.getY();
			visited[y][x] = 1;
			if(board[y][x] == Board.appleCharacter){
				System.out.println(this.getClass() + " ==> APPLE!");
				for (int i = 0; i < visited.length; i++) {
					for (int j = 0; j < visited[i].length; j++) {
						System.out.print(visited[i][j] + " ");
					}
					System.out.println();
				}
				return searchLocation;
			}

			//SOUTH
			if(visited[y+1][x] == 0 && board[y+1][x] != Board.impassableBlockCharacter && board[y+1][x] != Board.snakeCharacter){
				Location tempLocation = new Location(x, y+1);
				tempLocation.setPreviousLocation(searchLocation);
				queue.add(tempLocation);
				visited[y+1][x] = 1;
			}
			//NORTH
			if(visited[y-1][x] == 0 && board[y-1][x] != Board.impassableBlockCharacter && board[y-1][x] != Board.snakeCharacter){
				Location tempLocation = new Location(x, y-1);
				tempLocation.setPreviousLocation(searchLocation);
				queue.add(tempLocation);
				visited[y-1][x] = 1;
			} 
			//EAST
			if(visited[y][x+1] == 0 && board[y][x+1] != Board.impassableBlockCharacter && board[y][x+1] != Board.snakeCharacter){
				Location tempLocation = new Location(x+1, y);
				tempLocation.setPreviousLocation(searchLocation);
				queue.add(tempLocation);
				visited[y][x+1] = 1;
			} 
			//WEST
			if(visited[y][x-1] == 0 && board[y][x-1] != Board.impassableBlockCharacter && board[y][x-1] != Board.snakeCharacter){
				Location tempLocation = new Location(x-1, y);
				tempLocation.setPreviousLocation(searchLocation);
				queue.add(tempLocation);
				visited[y][x-1] = 1;
			}
		}
		return null;
	}

	private void depthFirstSearchInitialization(){
		Board board = model.getBoard();
		Location headLocation = model.getSnake().getLocations().get(0);
		path = new ArrayList<Location>();
		int[][] visited = new int[board.getBoardSize()][board.getBoardSize()];
		char[][] letters = board.getBoard();
		depthFirstSearchRecursive(letters, visited, headLocation);
	}

	private boolean depthFirstSearchRecursive(char[][] board, int[][] visited, Location snakeHeadLocation){

		Location headLocation = new Location(snakeHeadLocation.getX(), snakeHeadLocation.getY());

		int x = headLocation.getX();
		int y = headLocation.getY();
		visited[y][x] = 1;
		if(board[y][x] == Board.appleCharacter){
			System.out.println(this.getClass() + " ==> APPLE!");
			for (int i = 0; i < visited.length; i++) {
				for (int j = 0; j < visited[i].length; j++) {
					System.out.print(visited[i][j] + " ");
				}
				System.out.println();
			}
			return true;
		}

		//SOUTH
		if(visited[y+1][x] == 0 && board[y+1][x] != Board.impassableBlockCharacter && board[y+1][x] != Board.snakeCharacter){
			headLocation.setY(y+1);
			if(depthFirstSearchRecursive(board, visited, headLocation)){
				path.add(0, headLocation);
				return true;
			}
		}
		//NORTH
		if(visited[y-1][x] == 0 && board[y-1][x] != Board.impassableBlockCharacter && board[y-1][x] != Board.snakeCharacter){
			headLocation.setY(y-1);
			if(depthFirstSearchRecursive(board, visited, headLocation)){
				path.add(0, headLocation);
				return true;
			}
		} 
		//EAST
		if(visited[y][x+1] == 0 && board[y][x+1] != Board.impassableBlockCharacter && board[y][x+1] != Board.snakeCharacter){
			headLocation.setX(x+1);
			if(depthFirstSearchRecursive(board, visited, headLocation)){
				path.add(0, headLocation);
				return true;
			}
		} 
		//WEST
		if(visited[y][x-1] == 0 && board[y][x-1] != Board.impassableBlockCharacter && board[y][x-1] != Board.snakeCharacter){
			headLocation.setX(x-1);
			if(depthFirstSearchRecursive(board, visited, headLocation)){
				path.add(0, headLocation);
				return true;
			}
		}
		return false;
	}

	private Location aStarSearch(){
		System.out.println(this.getClass() + ": aStarSearch!!");
		frontier = new PriorityQueue<Location>();
		Board charBoard = model.getBoard();
		char[][] board = charBoard.getBoard();
		int[][] visited = new int[charBoard.getBoardSize()][charBoard.getBoardSize()];
		Location appleLocation = model.getApple().getLocation();
		Location headLocation = model.getSnake().getLocations().get(0);

		headLocation.setValue(getHeuristicValue(headLocation, appleLocation));
		frontier.enqueue(headLocation, headLocation.getValue());

		while(!frontier.isEmpty()){
			headLocation = frontier.dequeue();
			System.out.println("HELLO: ==> " + headLocation.toString());
			int x = headLocation.getX();
			int y = headLocation.getY();
			visited[y][x] = 1;
			if(board[y][x] == Board.appleCharacter){
				System.out.println(this.getClass() + " ==> APPLE! ==> Eucldian: " + euclidian);
				if(euclidian > 2){
					euclidian = 0;
				} else {
					euclidian ++;
				}
				for (int i = 0; i < visited.length; i++) {
					for (int j = 0; j < visited[i].length; j++) {
						System.out.print(visited[i][j] + " ");
					}
					System.out.println();
				}
				return headLocation;
			}

			//SOUTH
			if(visited[y+1][x] == 0 && board[y+1][x] != Board.impassableBlockCharacter && board[y+1][x] != Board.snakeCharacter){
				Location tempLocation = new Location(x, y+1);
				tempLocation.setPreviousLocation(headLocation);
				tempLocation.setValue(getHeuristicValue(tempLocation, appleLocation));
				frontier.enqueue(tempLocation, tempLocation.getValue());
				visited[y+1][x] = 1;
			}
			//NORTH
			if(visited[y-1][x] == 0 && board[y-1][x] != Board.impassableBlockCharacter && board[y-1][x] != Board.snakeCharacter){
				Location tempLocation = new Location(x, y-1);
				tempLocation.setPreviousLocation(headLocation);
				tempLocation.setValue(getHeuristicValue(tempLocation, appleLocation));
				frontier.enqueue(tempLocation, tempLocation.getValue());
				visited[y-1][x] = 1;
			} 
			//EAST
			if(visited[y][x+1] == 0 && board[y][x+1] != Board.impassableBlockCharacter && board[y][x+1] != Board.snakeCharacter){
				Location tempLocation = new Location(x+1, y);
				tempLocation.setPreviousLocation(headLocation);
				tempLocation.setValue(getHeuristicValue(tempLocation, appleLocation));
				frontier.enqueue(tempLocation, tempLocation.getValue());
				visited[y][x+1] = 1;
			} 
			//WEST
			if(visited[y][x-1] == 0 && board[y][x-1] != Board.impassableBlockCharacter && board[y][x-1] != Board.snakeCharacter){
				Location tempLocation = new Location(x-1, y);
				tempLocation.setPreviousLocation(headLocation);
				tempLocation.setValue(getHeuristicValue(tempLocation, appleLocation));
				frontier.enqueue(tempLocation, tempLocation.getValue());
				visited[y][x-1] = 1;
			}
		}
		return null;
	}

	private double getHeuristicValue(Location searchLocation, Location appleLocation){
		if(euclidian == 0){
			return euclidianDistance(searchLocation, appleLocation);
		} else if (euclidian == 1){
			return manhattanDistance(searchLocation, appleLocation);
		}else {
			double average = euclidianDistance(searchLocation, appleLocation) + manhattanDistance(searchLocation, appleLocation);
			return average;
		}
	}

	private double euclidianDistance (Location headLocation, Location appleLocation){
		int dx = Math.abs(headLocation.getX() - appleLocation.getX());
		int dy = Math.abs(headLocation.getY() - appleLocation.getY());
		return Math.sqrt(dx * dx + dy * dy);
	}

	private int manhattanDistance(Location headLocation, Location appleLocation){
		int dx = Math.abs(headLocation.getX() - appleLocation.getX());
		int dy = Math.abs(headLocation.getY() - appleLocation.getY());
		return dy + dx;
	}

	private CardinalDirection changeDirection (CardinalDirection direction){
		switch(direction){
		case SOUTH:
			return CardinalDirection.EAST;
		case EAST:
			return CardinalDirection.NORTH;
		case NORTH:
			return CardinalDirection.WEST;
		case WEST:
		default:
			System.out.println("Snake head is stuck");
			break;
		}
		return null;
	}

	@Override
	public void keyPressed(KeyEvent e) {	
		if(model.getGameState() == GameState.DONE){
			resetGame();
		}
		int keyPressed = e.getKeyCode();
		long before;
		long after;
		switch(keyPressed){
		case KeyEvent.VK_DOWN:
			model.pressedKey(keyPressed);
			break;
		case KeyEvent.VK_UP:
			model.pressedKey(keyPressed);
			break;
		case KeyEvent.VK_RIGHT:
			model.pressedKey(keyPressed);
			break;
		case KeyEvent.VK_LEFT:
			model.pressedKey(keyPressed);
			break;
		case KeyEvent.VK_F1:
			before = System.nanoTime();
			Location appleLocation = breadthFirstSearchInitialization();
			after = System.nanoTime();
			System.out.println("Time for breadthFirst is: " + (after - before));
			applePaths = new LinkedList<Location>();
			applePaths.push(appleLocation);
			while(appleLocation.getPreviousLocation() != null){
				applePaths.push(appleLocation.getPreviousLocation());
				appleLocation = appleLocation.getPreviousLocation();
			}
			for(Location location : applePaths){
				try {
					Thread.sleep(200);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				Location headLocation = model.getSnake().getLocations().get(0);
				if(headLocation.getY() - location.getY() == 1){
					model.pressedKey(KeyEvent.VK_UP);
				} else if (headLocation.getY() - location.getY() == -1){
					model.pressedKey(KeyEvent.VK_DOWN);
				} else if(headLocation.getX() - location.getX() == 1){
					model.pressedKey(KeyEvent.VK_LEFT);
				} else if(headLocation.getX() - location.getX() == -1){
					model.pressedKey(KeyEvent.VK_RIGHT);
				}
			}
			break;
		case KeyEvent.VK_F2:
			before = System.nanoTime();
			depthFirstSearchInitialization();
			after = System.nanoTime();
			System.out.println("Time for depthFirst is: " + (after - before));
			ArrayList<Location> locationsToApple2 = path;
			for(Location location : locationsToApple2){
				try {
					Thread.sleep(200);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				Location headLocation = model.getSnake().getLocations().get(0);
				System.out.println(this.getClass() + ": " + location.toString());
				if(headLocation.getY() - location.getY() == 1){
					model.pressedKey(KeyEvent.VK_UP);
				} else if (headLocation.getY() - location.getY() == -1){
					model.pressedKey(KeyEvent.VK_DOWN);
				} else if(headLocation.getX() - location.getX() == 1){
					model.pressedKey(KeyEvent.VK_LEFT);
				} else if(headLocation.getX() - location.getX() == -1){
					model.pressedKey(KeyEvent.VK_RIGHT);
				}
			}
			break;
		case KeyEvent.VK_F3:
			before = System.nanoTime();
			Location appleLocation2 = aStarSearch();
			after = System.nanoTime();
			System.out.println("Time for A* #" + (euclidian - 1) + " is: " + (after - before));
			applePaths = new LinkedList<Location>();
			applePaths.push(appleLocation2);
			while(appleLocation2.getPreviousLocation() != null){
				applePaths.push(appleLocation2.getPreviousLocation());
				appleLocation2 = appleLocation2.getPreviousLocation();
			}
			System.out.println(this.getClass() + ": VK_F1 ==> " + applePaths.toString());
			for(Location location : applePaths){
				try {
					Thread.sleep(200);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				Location headLocation = model.getSnake().getLocations().get(0);
				if(headLocation.getY() - location.getY() == 1){
					model.pressedKey(KeyEvent.VK_UP);
				} else if (headLocation.getY() - location.getY() == -1){
					model.pressedKey(KeyEvent.VK_DOWN);
				} else if(headLocation.getX() - location.getX() == 1){
					model.pressedKey(KeyEvent.VK_LEFT);
				} else if(headLocation.getX() - location.getX() == -1){
					model.pressedKey(KeyEvent.VK_RIGHT);
				}
			}
			break;
		default:
			break;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {// TODO Auto-generated method stub
	}
	@Override
	public void keyTyped(KeyEvent e) {// TODO Auto-generated method stub
	}
}
