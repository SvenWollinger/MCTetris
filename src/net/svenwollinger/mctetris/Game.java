package net.svenwollinger.mctetris;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Random;

import javax.swing.JFrame;

public class Game extends JFrame{
	private static final long serialVersionUID = -5590427638167990749L;
	
	public Block board[][];
	private GamePanel gamePanel = new GamePanel(this);
	private KBInput input = new KBInput(this);
	
	public final int INITIAL_SCREEN_WIDTH = 512;
	public final int INITIAL_SCREEN_HEIGHT = 512;
	
	public final int BOARD_WIDTH = 10;
	public final int BOARD_HEIGHT = 20;
	
	Piece cPiece;
	
	public boolean running = true;
	public boolean isPaused = false;
	
	public Game() throws IOException {
		this.setTitle("MCTetris");
		this.add(gamePanel);
		this.addKeyListener(input);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		gamePanel.setPreferredSize(new Dimension(INITIAL_SCREEN_WIDTH, INITIAL_SCREEN_HEIGHT));
		this.pack();
		start();
		loop();
	}
	
	int fallSpeed = 0;
	int fallSpeedMax = 300;
	public void loop() {
		while(running) {
			final int ts = getTileSize();
			this.setMinimumSize(new Dimension(BOARD_WIDTH *ts, BOARD_HEIGHT * ts));
			if(!isPaused) {
				input();
				boolean isHit = false;
				if(cPiece != null)
					isHit = cPiece.update();
				
				if(fallSpeed >= fallSpeedMax) {
					if(isHit) cPiece.hit();
					
					if(cPiece != null)
						cPiece.moveDown();
					fallSpeed = 0;
				} else {
					fallSpeed += 10;
		        }
				
				int rows = checkRows();
				if(rows != 0)
					fallSpeedMax-= 10 * rows;
			}
			
			this.gamePanel.paintImmediately(new Rectangle(0,0,this.getWidth(),this.getHeight()));
	        
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) { 
				e.printStackTrace();
			}
		}
	}
	
	public int checkRows() {
		int rowsCleared = 0;
		for(int y = 0; y < BOARD_HEIGHT; y++) {
			boolean hasFull = true;
			for(int x = 0; x < BOARD_WIDTH; x++) {
				if(board[x][y] == null) hasFull = false;
			}
			if(hasFull) {
				rowsCleared++;
				for(int x = 0; x < BOARD_WIDTH; x++) {
					board[x][y] = null;
				}
				
				for(int z = y; z > 0; z--) {
					for(int x = 0; x < BOARD_WIDTH; x++) {
						board[x][z] = board[x][z-1];
					}
				}
			}
		}
		return rowsCleared;
	}
	
	public static int randomRange(int min, int max) {
	    Random r = new Random();
	    return r.nextInt((max - min) + 1) + min;
	}
	
	public void spawnPiece() {
		cPiece = new Piece(this);
	}
	
	public int getTileSize() {
		return gamePanel.getHeight() / BOARD_HEIGHT;
	}
	
	public void start() {
		board = new Block[BOARD_WIDTH][BOARD_HEIGHT];
		spawnPiece();
	}
	
	int dropCooldown = 0;
	int dropCooldownMax = 25;
	public void input() {
		if(dropCooldown > 0)
			dropCooldown--;

		if(cPiece != null) {
			if(input.isPressed(KeyEvent.VK_SPACE) && dropCooldown == 0) {
				for(int i = 0; i < this.BOARD_HEIGHT; i++) {
					if(cPiece.moveDown())
						break;
				}
				dropCooldown = dropCooldownMax;
			}
			
			if(input.isPressed(KeyEvent.VK_E))
				cPiece.rotate(1);
			if(input.isPressed(KeyEvent.VK_Q))
				cPiece.rotate(-1);
			
			if(input.isPressed(KeyEvent.VK_A))
				cPiece.move(-1);
			if(input.isPressed(KeyEvent.VK_D))
				cPiece.move(1);
			
			if(input.isPressed(KeyEvent.VK_R))
				start();
			
			if(input.isPressed(KeyEvent.VK_S)) {
				cPiece.moveDown();
				if(cPiece.checkCollision()) cPiece.hit();
			}
		}
	}
}
