package net.svenwollinger.mctetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JPanel;

public class GamePanel extends JPanel{
	private static final long serialVersionUID = -1644163741924496069L;

	Game game;
	
	public GamePanel (Game _game) {
		game = _game;
	}
	
	@Override
	public void paint(Graphics g) {
		final int ts = game.getTileSize();
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		int offsetX = (this.getWidth()/2) - (game.BOARD_WIDTH * ts)/2;
		g.setColor(Color.BLACK);
		if(game.cPiece != null) {
			Piece cp = game.cPiece;
			for(int y = 0; y < cp.figure[0].length; y++) {
				for(int x = 0; x < cp.figure.length; x++) {
					if(cp.figure[y][x] != 0) {
						g.drawImage(Resources.sprites.get(cp.index), offsetX + (cp.getPosX() + x) * ts, (cp.getPosY() + y) * ts, ts, ts, null);
					}
				}
			}
		}
		
		for(int y = 0; y < game.BOARD_HEIGHT; y++) {
			for(int x = 0; x < game.BOARD_WIDTH; x++) {
				Block cBlock = game.board[x][y];
				if(cBlock != null) {
					g.drawImage(Resources.sprites.get(cBlock.index), offsetX + x * ts, y * ts, ts, ts, null);
					g.setColor(Color.BLACK);
				}
				g.drawRect(offsetX + x * ts, y * ts, ts, ts);
			}
		}
		
		if(game.isPaused) {
			g.setColor(new Color(0,0,0,100));
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			g.setColor(Color.WHITE);
			drawCenteredString(g, "PAUSED", new Rectangle(0,0,this.getWidth(), this.getHeight()), new Font("Monospaced", Font.BOLD, this.getHeight()/20));
		}
	}
	
	public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
	    FontMetrics metrics = g.getFontMetrics(font);
	    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
	    int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
	    g.setFont(font);
	    g.drawString(text, x, y);
	}
}
