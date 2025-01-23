package frames;

import java.awt.Frame;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.Game;

@SuppressWarnings("serial")
public class Phases extends JFrame{
	JPanel panel;
	private int screen_width;
	private int screen_height;
	public static Scanner reader = new Scanner(System.in);
	Game game;
	
	public Phases(Setup setup) {
		this.panel = setup.panel;
		this.screen_width = setup.screen_width;
		this.screen_height = setup.screen_height;
		Phases.reader = Setup.reader;
		this.game = setup.game;
	}
	
	public void launchGame() {
		reset();
		game.GameRunner();
	}
	
	public void reset() {
		panel.removeAll();
		panel.revalidate();
		validate();
		repaint();
	}
}
