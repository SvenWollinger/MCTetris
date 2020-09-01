package net.svenwollinger.mctetris;

public class Piece {
	private Game game;
	
	public int[][] figure;
	private int posX = 0;
	private int posY = 1;
	
	private int rotationCooldown = 0;
	private int rotationCooldownMax = 10;
	
	private int moveCooldown = 0;
	private int moveCooldownMax = 10;
	
	public int index = 0;
	
	public Piece(Game _game) {
		index = Game.randomRange(0,Resources.pieces.size()-1);
		//index = 0;
		figure = Resources.pieces.get(index);
		game = _game;
		
		posX = (game.BOARD_WIDTH/2) - figure[0].length/2;
		
		if(game.board[posX][posY] != null)
			System.exit(0);
		
		//Important:
		//When looping through int[][] figure you need to do figure[y][x]
	}
	
	public boolean update() {
		if(rotationCooldown > 0)
			rotationCooldown--;
		if(moveCooldown > 0)
			moveCooldown--;
		
		return checkCollision();
	}
	
	public boolean moveDown() {
		if(!checkCollision()) {
			posY++;
			return false;
		}
		return true;
	}
	
	//returns true if the piece hit something
	public boolean checkCollision() {
		for(int y = 0; y < figure[0].length; y++) {
			for(int x = 0; x < figure.length; x++) {
				if(figure[y][x] != 0) {
					final int nPosX = posX + x;
					final int nPosY = posY + y;
					if(!(nPosX <= -1) && !(nPosX >= game.BOARD_WIDTH) && !(nPosY <= 0) && !(nPosY >= game.BOARD_HEIGHT - 1)) {
						Block cBlock = game.board[posX + x][posY + y + 1];
						if(cBlock != null)
							return true;
					}	
					if(posY + y >= game.BOARD_HEIGHT-1)
						return true;
				}
			}
		}
		return false;
	}
	
	public void hit() {
		for(int y = 0; y < figure[0].length; y++) {
			for(int x = 0; x < figure.length; x++) {
				if(figure[y][x] != 0) {
					final int nPosX = posX + x;
					final int nPosY = posY + y;
					if(!(nPosX < 0 || nPosX >= game.BOARD_WIDTH || nPosY < 0 || nPosY >= game.BOARD_HEIGHT))
						game.board[posX + x][posY + y] = new Block(index);
				}
			}
		}
		game.cPiece = null;
		game.spawnPiece();
	}
	
	private boolean checkMoveCollision(int _dir) {
		for(int y = 0; y < figure[0].length; y++) {
			for(int x = 0; x < figure.length; x++) {
				final int piecePosX = posX + x + _dir;
				final int piecePosY = posY + y;
				
				if(figure[y][x] != 0) {
					if(piecePosX <= -1)
						return false;
					if(piecePosX >= game.BOARD_WIDTH)
						return false;
					if(game.board[piecePosX][piecePosY] != null)
						return false;
				}
			}
		}
		return true;
	}
	
	public void move(int _dir) {
		if(_dir == 1 && moveCooldown == 0) {
			if(checkMoveCollision(_dir)) {
				posX++;
				moveCooldown = moveCooldownMax;
			}
		} else if (_dir == -1 && moveCooldown == 0) {
			if(checkMoveCollision(_dir)) {
				posX--;
				moveCooldown = moveCooldownMax;
			}
		}
	}
	
	private boolean checkRotation(int[][] newFigure) {
		for(int y = 0; y < newFigure[0].length; y++) {
			for(int x = 0; x < newFigure.length; x++) {
				final int nPosX = posX + x;
				final int nPosY = posY + y;
				if((nPosX <= -1) || (nPosX >= game.BOARD_WIDTH) || (nPosY <= 0) || (nPosY >= game.BOARD_HEIGHT - 1)) {
					if(newFigure[y][x] == 1)
						return false;
				}
				if(!(nPosX < 0 || nPosX >= game.BOARD_WIDTH || nPosY < 0 || nPosY >= game.BOARD_HEIGHT))
					if(game.board[nPosX][nPosY] != null)
						return false;
			}
		}
		return true;
	}

	public void rotate(int _dir) {
		if(rotationCooldown == 0) {
			int[][] newFigure = rotateMatrix(figure, _dir);
			if(checkRotation(newFigure)) {
				figure = newFigure;
				rotationCooldown = rotationCooldownMax;
			}
		}
		
	}
	
	private static int[][] rotateMatrix(int[][] figure2, int _dir) {
		int[][] rotated = new int[figure2[0].length][figure2.length];
		for (int i = 0; i < figure2[0].length; ++i) {
			for (int j = 0; j < figure2.length; ++j) {
				if(_dir == 1)
					rotated[i][j] = figure2[figure2.length - j - 1][i];              
				else if(_dir == -1)
					rotated[i][j] = figure2[j][figure2[0].length - i - 1];
			}
		}
		return rotated;
	}
	
	public void setPosX(int _x) {
		posX = _x;
	}
	
	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}


}
