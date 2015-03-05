package model;

import java.util.ArrayList;
import java.util.Random;

public class Board {

	private int boardSize;
	private char[][] board; //TODO REMEMBER IT'S [y][x] and not [x][y]
	public static final char impassableBlockCharacter = 'O';
	public static final char emptySpaceCharacter = '_';
	public static final char snakeCharacter = 'S';
	public static final char appleCharacter = 'X';
	ArrayList<Location> impassableBlocks;

	public char[][] getBoard() {
		return board;
	}
	
	public Board (int boardSize){
		if(boardSize > 5){this.boardSize = boardSize;}
		else{this.boardSize = Model.DEFAULT_BOARD_SIZE;}

		board = new char[boardSize][boardSize];
		impassableBlocks = new ArrayList<Location>();
		this.buildFirstBoard();
	}

	private void buildFirstBoard(){
		char cellCharacter = Board.emptySpaceCharacter;

		//border on the board
		for(int x = 0; x < boardSize; x ++){

			for(int y = 0; y < boardSize; y++){

				if(x == 0 || y == 0 || x == boardSize - 1 || y == boardSize - 1){cellCharacter = Board.impassableBlockCharacter;}
				else{cellCharacter = Board.emptySpaceCharacter;}
				
				// 10% of terrain is impassable
//				Random rand = new Random();
//				int randomNum1 = rand.nextInt(10) + 1;
//				if(randomNum1 < 2){
//					impassableBlocks.add(new Location(x, y));
//					cellCharacter = Board.impassableBlockCharacter;
//				}

				board[y][x] = cellCharacter;
			}
		}
	}
	
	private void buildBoard(){
		char cellCharacter = Board.emptySpaceCharacter;

		//border on the board
		for(int x = 0; x < boardSize; x ++){

			for(int y = 0; y < boardSize; y++){

				if(x == 0 || y == 0 || x == boardSize - 1 || y == boardSize - 1){cellCharacter = Board.impassableBlockCharacter;}
				else{cellCharacter = Board.emptySpaceCharacter;}
				
				for(Location location : impassableBlocks){
					if(location.equals(new Location(x, y))){
						cellCharacter = Board.impassableBlockCharacter;
					}
				}
				

				board[y][x] = cellCharacter;
			}
		}
	}

	public int getBoardSize() {
		return boardSize;
	}
	
	public void updateAppleLocation(Apple apple){
		int x = apple.getLocation().getX();
		int y = apple.getLocation().getY();
		board[y][x] = Board.appleCharacter;
	}

	public void updateSnakeLocation(Snake snake){
		this.buildBoard();
		int x;
		int y;
		ArrayList<Location> locations = snake.getLocations();
		int i = 0;
		for(Location location : locations){
			x = location.getX();
			y = location.getY();
			if(i == 0){
				board[y][x] = 'H';
			}else{
				board[y][x] = Board.snakeCharacter;
			}
			i++;
			
		}
	}

	public boolean checkCollisions(Snake snake, Apple apple){
		ArrayList<Location> locations = snake.getLocations();
		Location headLocation = locations.get(0);
		String gameOver = this.getClass() + ": GAME OVER!";
		if(headLocation.getX() == 0 || headLocation.getY() == 0 || headLocation.getX() == boardSize -1 || headLocation.getY() == boardSize - 1){
			System.out.println(gameOver);
			return true;
		}else if(headLocation.equals(apple.getLocation())){
			snake.eatApple();
			apple.eaten(snake, boardSize);
			return false;
		}else{
			for(int i = 1; i < locations.size(); i++){
				if(headLocation.equals(locations.get(i))){
					System.out.println(gameOver);
					return true;
				}
			}
		}
		return false;
	}
}
