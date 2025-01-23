package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.Game;
import main.GameLoader;
import player.Card;
import player.Company;
import player.Player;

@SuppressWarnings("serial")
public class Setup extends JFrame {
	JPanel panel;
	int screen_width;
	int screen_height;
	public static Scanner reader = new Scanner(System.in);
	Game game;
	
	private static final Color[] player_colors = {new Color(220,0,220), new Color(0,220,220), new Color(220,220,0), new Color(0,220,0), new Color(0,0,220)};

	public Setup() {
		super("Terraforming Mars"); 
		panel = new JPanel();
		StartScreen();
	}
	
	private void StartScreen() {
		reset();
		getContentPane().setBackground(new Color(210, 210, 210));
		setLayout(new BorderLayout());

		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		screen_width = (int) screensize.getWidth();
		screen_height = (int) screensize.getHeight();

		panel.setLayout(null);

		JLabel label = new JLabel("TERRAFORMING MARS");
		label.setFont(new Font("Monospaced", Font.BOLD, 60));
		Dimension size = label.getPreferredSize();
		label.setBounds(screen_width / 2 - size.width / 2, 50, size.width, size.height);
		panel.add(label);

		JButton button1 = new JButton("New Game");
		size = button1.getPreferredSize();
		button1.setBounds(screen_width / 2 - size.width / 2, screen_height / 2 - 100, size.width, size.height);
		button1.addActionListener(e -> {
			runGame(false);
		});

		JButton button2 = new JButton("Load Game");
		button2.setBounds(screen_width / 2 - size.width / 2, screen_height / 2 - 100 + size.height + 20, size.width,
				size.height);
		button2.addActionListener(e -> {
			runGame(true);
		});
		JButton button3 = new JButton("Exit Game");
		button3.setBounds(screen_width / 2 - size.width / 2, screen_height / 2 - 100 + 2 * (size.height + 20),
				size.width, size.height);
		button3.addActionListener(e -> {
			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		});

		panel.add(button1);
		panel.add(button2);
		panel.add(button3);

		add(panel, BorderLayout.CENTER);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(screen_width, screen_height);
		setVisible(true);
	}

	public void reset() {
		panel.removeAll();
		panel.revalidate();
		validate();
		repaint();
	}
	
	private void runGame(boolean in) {
		if(in) {
			GameLoader loader = new GameLoader();
			game = loader.loadGame(reader);
			Phases p = new Phases(this);
			p.launchGame();
		} else {
			game = new Game(reader);
			newGameSettings();
		}
//		while(true) {
//			reset();
//			if(!game.GameRunner()) break;
//		}
//		game.GameEnd();
	}
	
	private void newGameSettings() {
		reset();
		JLabel label = new JLabel("TERRAFORMING MARS");
		label.setFont(new Font("Monospaced", Font.BOLD, 60));
		Dimension size = label.getPreferredSize();
		label.setBounds(screen_width / 2 - size.width / 2, 50, size.width, size.height);
		panel.add(label);
		
		label = new JLabel("Num Players");
		JTextField myTextField = new JTextField("1");
		JPanel tempPanel = new JPanel(new BorderLayout());
		tempPanel.add(label,BorderLayout.WEST);
		tempPanel.add(myTextField,BorderLayout.CENTER);
		size = tempPanel.getPreferredSize();
		tempPanel.setBounds(screen_width / 2 - size.width / 2, 200, size.width, size.height);
		panel.add(tempPanel);
		
		label = new JLabel("Num Company Choices");
		myTextField = new JTextField("2");
		tempPanel = new JPanel(new BorderLayout());
		tempPanel.add(label,BorderLayout.WEST);
		tempPanel.add(myTextField,BorderLayout.CENTER);
		size = tempPanel.getPreferredSize();
		tempPanel.setBounds(screen_width / 2 - size.width / 2, 250, size.width, size.height);
		panel.add(tempPanel);
		
		label = new JLabel("Corporate Era");
		myTextField = new JTextField("1");
		tempPanel = new JPanel(new BorderLayout());
		tempPanel.add(label,BorderLayout.WEST);
		tempPanel.add(myTextField,BorderLayout.CENTER);
		size = tempPanel.getPreferredSize();
		tempPanel.setBounds(screen_width / 2 - size.width / 2, 300, size.width, size.height);
		panel.add(tempPanel);
		
		label = new JLabel("Preludes");
		myTextField = new JTextField("1");
		tempPanel = new JPanel(new BorderLayout());
		tempPanel.add(label,BorderLayout.WEST);
		tempPanel.add(myTextField,BorderLayout.CENTER);
		size = tempPanel.getPreferredSize();
		tempPanel.setBounds(screen_width / 2 - size.width / 2, 350, size.width, size.height);
		panel.add(tempPanel);
		
		label = new JLabel("Custom");
		myTextField = new JTextField("0");
		tempPanel = new JPanel(new BorderLayout());
		tempPanel.add(label,BorderLayout.WEST);
		tempPanel.add(myTextField,BorderLayout.CENTER);
		size = tempPanel.getPreferredSize();
		tempPanel.setBounds(screen_width / 2 - size.width / 2, 400, size.width, size.height);
		panel.add(tempPanel);
		
		label = new JLabel("Default Companies");
		myTextField = new JTextField("0");
		tempPanel = new JPanel(new BorderLayout());
		tempPanel.add(label,BorderLayout.WEST);
		tempPanel.add(myTextField,BorderLayout.CENTER);
		size = tempPanel.getPreferredSize();
		tempPanel.setBounds(screen_width / 2 - size.width / 2, 450, size.width, size.height);
		panel.add(tempPanel);
		
		JButton button1 = new JButton("Start Game");
		size = button1.getPreferredSize();
		button1.setBounds(screen_width / 2 - size.width / 2, 500, size.width, size.height);
		button1.addActionListener(e -> {
			Component[] components = panel.getComponents();
			boolean reset = false;
			try {
				int num_players 		= Integer.valueOf(((JTextField) (((JPanel) components[1]).getComponents())[1]).getText());
				int num_company_choices = Integer.valueOf(((JTextField) (((JPanel) components[2]).getComponents())[1]).getText());
				int corporate_era 		= Integer.valueOf(((JTextField) (((JPanel) components[3]).getComponents())[1]).getText());
				int prelude 			= Integer.valueOf(((JTextField) (((JPanel) components[4]).getComponents())[1]).getText());
				int custom 				= Integer.valueOf(((JTextField) (((JPanel) components[5]).getComponents())[1]).getText());
				int played_before 		= Integer.valueOf(((JTextField) (((JPanel) components[6]).getComponents())[1]).getText());

				if(num_players < 1 || num_players > 5) {
					JOptionPane.showMessageDialog(panel, "Must have between 1 and 5 players");
					reset = true;
				} else if(num_company_choices < 1 || num_company_choices > 4) {
					JOptionPane.showMessageDialog(panel, "Must have between 1 and 4 company choices");
					reset = true;
				} else if(corporate_era != 0 && corporate_era != 1) {
					JOptionPane.showMessageDialog(panel, "Must have a value of 0 (no) or 1 (yes) for corporate era");
					reset = true;
				} else if(prelude != 0 && prelude != 1) {
					JOptionPane.showMessageDialog(panel, "Must have a value of 0 (no) or 1 (yes) for prelude");
					reset = true;
				} else if(custom != 0 && custom != 1) {
					JOptionPane.showMessageDialog(panel, "Must have a value of 0 (no) or 1 (yes) for custom");
					reset = true;
				} else if(played_before != 0 && played_before != 1) {
					JOptionPane.showMessageDialog(panel, "Must have a value of 0 (no) or 1 (yes) for played_before");
					reset = true;
				} else {
					game.setup(num_players, num_company_choices, corporate_era == 1, prelude == 1, custom == 1, played_before == 0);
					game.loader.initialBounderies(corporate_era == 1, prelude == 1, custom == 1);
					playerChooseCompany(0, num_company_choices, played_before == 0);
				}
			} catch (NumberFormatException err) {
				JOptionPane.showMessageDialog(panel, "Not a valid number entry");
				reset = true;
			}
			
			if(reset) {
				((JTextField) (((JPanel) components[1]).getComponents())[1]).setText("1");
				((JTextField) (((JPanel) components[2]).getComponents())[1]).setText("2");
				((JTextField) (((JPanel) components[3]).getComponents())[1]).setText("1");
				((JTextField) (((JPanel) components[4]).getComponents())[1]).setText("1");
				((JTextField) (((JPanel) components[5]).getComponents())[1]).setText("0");
				((JTextField) (((JPanel) components[6]).getComponents())[1]).setText("0");
			}
		});
		panel.add(button1);
		
		JButton button2 = new JButton("Back");
		size = button2.getPreferredSize();
		button2.setBounds(screen_width / 2 - size.width / 2, 600, size.width, size.height);
		button2.addActionListener(e -> {
			StartScreen();
		});
		panel.add(button2);
	}
	
	public void playerChooseCompany(int i, int num_choices, boolean played_before) {
		Company[] companies = game.getChoices(num_choices, played_before);
		reset();
		JLabel label = new JLabel("---------- Player " + game.players[i].number + " ----------");
		label.setFont(new Font("Monospaced", Font.BOLD, 40));
		label.setForeground(player_colors[i]);
		Dimension size = label.getPreferredSize();
		label.setBounds(screen_width / 2 - size.width / 2, 50, size.width, size.height);
		panel.add(label);
		
		int temp_height = size.height;
		label = new JLabel("Select Company");
		label.setFont(new Font("Monospaced", Font.BOLD, 20));
		label.setForeground(Color.BLACK);
		size = label.getPreferredSize();
		label.setBounds(screen_width / 2 - size.width / 2, 50+temp_height, size.width, size.height);
		panel.add(label);
		
		label = new JLabel("?");
		label.setFont(new Font("Monospaced", Font.BOLD, 60));
		size = label.getPreferredSize();
		label.setBounds(screen_width - size.width, 0, size.width, size.height);
		label.addMouseListener(new MouseAdapter() {
			@Override
			@SuppressWarnings("unused")
			public void mousePressed(MouseEvent e) {
				HelpPopup help = new HelpPopup();
			}
		});
		panel.add(label);
		
		
		JPanel choices_panel = new JPanel();
		choices_panel.setLayout(new FlowLayout());
		
		for(int j = 0; j<companies.length; j++) {
			JButton loop_button = new JButton(companies[j].name);
			size = loop_button.getPreferredSize();
			loop_button.setBounds(j * 200, 0, size.width, size.height);
			loop_button.addActionListener(new CompanyInfoPopup(companies[j]));
			
			CompanyInfoPopup selectCompany = new CompanyInfoPopup(companies[j]);
			selectCompany.addPlayer(game.players[i]);
			if(i == game.players.length - 1) {
				selectCompany.allPlayersDone();
			}
			selectCompany.playerNum(i, num_choices, played_before, this);
			JButton select = new JButton("Select");
			select.setBounds(j * 200, 50, size.width, size.height);
			select.addActionListener(selectCompany);
			
			JPanel tempPanel = new JPanel();
			tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.PAGE_AXIS));
			tempPanel.add(loop_button);
			tempPanel.add(select);
			size = tempPanel.getPreferredSize();
			tempPanel.setBounds(screen_width / 2 + 200 - j * 200, 200, size.width, size.height);
			panel.add(tempPanel);
		}
		
		JButton button2 = new JButton("Back");
		size = button2.getPreferredSize();
		button2.setBounds(screen_width / 2 - size.width / 2, 600, size.width, size.height);
		button2.addActionListener(e -> {
			StartScreen();
		});
		panel.add(button2);
	}
	
	public void setupPrelude(int i) {
		if(game.preludes == null) {
			finishSetup();
		}
		ArrayList<Card> preludes = game.getChoices(game.players[i]);
		choosePreludes(i, preludes, new ArrayList<Card>());
	}
	
	public void choosePreludes(int i, ArrayList<Card> preludes, ArrayList<Card> selected) {
		reset();
		JLabel label = new JLabel("---------- Player " + game.players[i].number + " ----------");
		label.setFont(new Font("Monospaced", Font.BOLD, 40));
		label.setForeground(player_colors[i]);
		Dimension size = label.getPreferredSize();
		label.setBounds(screen_width / 2 - size.width / 2, 50, size.width, size.height);
		panel.add(label);
		
		label = new JLabel("?");
		label.setFont(new Font("Monospaced", Font.BOLD, 60));
		size = label.getPreferredSize();
		label.setBounds(screen_width - size.width, 0, size.width, size.height);
		label.addMouseListener(new MouseAdapter() {
			@Override
			@SuppressWarnings("unused")
			public void mousePressed(MouseEvent e) {
				HelpPopup help = new HelpPopup();
			}
		});
		panel.add(label);
				
		JPanel choices_panel = new JPanel();
		choices_panel.setLayout(new FlowLayout());
		
		int scale_width = (screen_width - 50) / 4;
		
				
		for(int j = 0; j<preludes.size(); j++) {
			JLayeredPane prelude = PreludePanel.returnLayeredPane(preludes.get(j), scale_width, 500);
			prelude.setBounds((scale_width+10)*j, 100, scale_width, 500);
			panel.add(prelude);
			
			JButton select = new JButton("Select");
			size = select.getPreferredSize();
			select.setBounds((scale_width+10)*j, 650, size.width, size.height);
			PreludePanel action = new PreludePanel();
			action.addStats(j, i, preludes, selected, i == game.players.length - 1, game.players[i], this);
			select.addActionListener(action);
			panel.add(select);
		}
	}
	
	public void finishSetup() {
		for (Player i : game.players) {
			i.clearAbilities();
			i.terraform_rating_before = i.terraform_rating;
		}
		Phases p = new Phases(this);
		p.launchGame();
	}
}
