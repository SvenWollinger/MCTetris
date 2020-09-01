package net.svenwollinger.mctetris;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Resources {
	
	public static ArrayList<int[][]> pieces = new ArrayList<int[][]>();
	public static ArrayList<Image> sprites = new ArrayList<Image>();
	
	public void init() throws IOException {
		pieces.add(new int[][]{
			{0,0,1,0},
			{0,0,1,0},
			{0,0,1,0},
			{0,0,1,0}
		});
		sprites.add(new ImageIcon(getClass().getResource("/art/cyan.png")).getImage());
		
		pieces.add(new int[][]{
			{1,1},
			{1,1}
		});
		sprites.add(new ImageIcon(getClass().getResource("/art/yellow.png")).getImage());
		
		pieces.add(new int[][]{
			{1,0,0},
			{1,0,0},
			{1,1,0}
		});
		sprites.add(new ImageIcon(getClass().getResource("/art/brown.png")).getImage());
		
		pieces.add(new int[][]{
			{1,0,0},
			{1,1,0},
			{1,0,0}
		});
		sprites.add(new ImageIcon(getClass().getResource("/art/magenta.png")).getImage());
		
		pieces.add(new int[][]{
			{0,1,0},
			{0,1,0},
			{1,1,0}
		});
		sprites.add(new ImageIcon(getClass().getResource("/art/blue.png")).getImage());
		
		pieces.add(new int[][]{
			{0,1,0},
			{1,1,0},
			{1,0,0}
		});
		sprites.add(new ImageIcon(getClass().getResource("/art/green.png")).getImage());
		
		pieces.add(new int[][]{
			{1,0,0},
			{1,1,0},
			{0,1,0}
		});
		sprites.add(new ImageIcon(getClass().getResource("/art/red.png")).getImage());
	}
}
