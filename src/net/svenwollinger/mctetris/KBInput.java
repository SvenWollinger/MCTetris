package net.svenwollinger.mctetris;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KBInput implements KeyListener{

	private Game game;
	private boolean keys[] = new boolean[25565];
	
	public KBInput(Game _game) {
		game = _game;
	}
	
	public boolean isPressed(int keyCode) {
		return keys[keyCode];
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		keys[arg0.getKeyCode()] = true;
		
		if(arg0.getKeyCode() == KeyEvent.VK_ESCAPE)
			game.isPaused = !game.isPaused;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		keys[arg0.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) { }

}
