package model;

import java.awt.event.KeyEvent;
import java.util.Observable;

import view.View;

public class Model extends Observable{

	public static int DEFAULT_BOARD_SIZE = 12;
	private Board board;
	private Apple apple;
	private Snake snake;
	private GameState gameState;

	public GameState getGameState() {
		return gameState;
	}

	public Board getBoard(){
		return board;
	}

	public Snake getSnake(){
		return snake;
	}

	public Apple getApple(){
		return apple;
	}

	public Model (View v){
		gameState = GameState.RUNNING;

		this.addObserver(v);
		board = new Board(DEFAULT_BOARD_SIZE);

		snake = new Snake();

		apple = new Apple();

		apple.eaten(snake, board.getBoardSize());

		updateBoard();

		updateView();
	}

	public void updateView(){
		this.setChanged();
		this.notifyObservers(board);
	}

	public void pressedKey(int keyCode){
		switch(keyCode){
		case KeyEvent.VK_DOWN:
			processKeyCommand(CardinalDirection.SOUTH);
			break;
		case KeyEvent.VK_UP:
			processKeyCommand(CardinalDirection.NORTH);
			break;
		case KeyEvent.VK_RIGHT:
			processKeyCommand(CardinalDirection.EAST);
			break;
		case KeyEvent.VK_LEFT:
			processKeyCommand(CardinalDirection.WEST);
			break;
		default:
			break;
		}
	}

	private void updateBoard(){
		board.updateSnakeLocation(snake);
		board.updateAppleLocation(apple);
	}

	public void processKeyCommand(CardinalDirection direction){
		snake.updateDirection(direction);
		snake.move();
		if(board.checkCollisions(snake, apple)){
			gameState = GameState.DONE;
		}
		updateBoard();
		this.updateView();
	}
}
