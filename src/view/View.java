package view;

import java.awt.Dimension;
import java.awt.event.WindowEvent;

import controller.Controller;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import model.Board;


public class View implements Observer{
	
	private JFrame frame;
	
	public View (Controller controller){
		frame = new JFrame();
		frame.setSize(new Dimension(500,500));
		
		frame.setVisible(true);
		frame.addKeyListener(controller);	
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void close(){
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}
	
	@Override
	public void update(Observable model, Object b) {
		Board board = null;
		if(b instanceof Board){board = (Board)b;}
		else{return;}
		
		char[][] boardLetters = board.getBoard();
		
		for(int i = 0; i < boardLetters.length; i++){
			for(int j = 0; j < boardLetters[i].length; j++){
				System.out.print(boardLetters[i][j]);
				System.out.print(" ");
			}
			System.out.println("");
		}
	}
}
