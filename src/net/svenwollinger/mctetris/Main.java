package net.svenwollinger.mctetris;

import java.io.IOException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {
	
	public final static String VERSION = "20200901_1";
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Resources().init();
			new Game();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
