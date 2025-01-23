package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import board.Resources;
import player.Ability;
import player.Company;
import player.Player;

@SuppressWarnings("serial")
public class CompanyInfoPopup extends JFrame implements ActionListener{
	private final Company company;
	private Player player;
	private JPanel panel;
	private static int width = 350;
	private static int height = 350;
	private boolean added = false;
	
	private boolean all_players_done = false;
	private int player_num;
	private Setup Setup;
	private int num_choices = 4;
	private boolean played_before = true;
	
	CompanyInfoPopup(final Company company) {
		super();
		this.company = company;
	}
	
	public void addPlayer(Player player) {this.player = player;}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(player != null) {
			if(!added) {
				added = true;
				addCompanyToPlayer();
				return;
			}
		}
		SetupCompany();
	}
	
	private void SetupCompany() {
		panel = new JPanel();
		getContentPane().setBackground(new Color(210, 210, 210));
		panel.setLayout(null);

		JLabel label = new JLabel(company.name);
		label.setFont(new Font("Monospaced", Font.BOLD, 30));
		Dimension size = label.getPreferredSize();
		label.setBounds(width / 2 - size.width / 2, 30, size.width, size.height);
		panel.add(label);
		
		label = new JLabel("Starting MegaCredits: " + String.valueOf(company.megacredits));
		size = label.getPreferredSize();
		label.setBounds(width / 2 - size.width / 2, 100, size.width, size.height);
		panel.add(label);
		
		if(company.resource_generation != null) {
			String resources = "";
			for(Resources e: company.resource_generation) resources += e.toString() + ", ";
			JTextArea text = new JTextArea("Starting Resource Generation: " + resources);
			text.setLineWrap(true);
			size = text.getPreferredSize();
			text.setBounds(0, 150, 350, size.height);
			panel.add(text);
		}
		
		if(company.resources != null) {
			String resources = "";
			for(Resources e: company.resources) resources += e.toString();
			JTextArea text = new JTextArea("Starting Resources: " + resources);
			text.setLineWrap(true);
			size = text.getPreferredSize();
			text.setBounds(0, 200, 350, size.height);
			panel.add(text);
		}
		
		if(company.abilities != null) {
			String abilities = "";
			for(Ability e: company.abilities) abilities += e.toString();
			JTextArea text = new JTextArea("Starting Abilities: " + abilities);
			text.setLineWrap(true);
			size = text.getPreferredSize();
			text.setBounds(0, 250, 350, size.height);
			panel.add(text);
		}
		
		if(company.tag != null) {
			for(int i = 0; i<company.tag.length; i++) {
				ImageIcon icon = new ImageIcon("lib/tags/" + company.tag[i].toString().toLowerCase() + ".png");
				Image image = icon.getImage();
				image = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
				icon = new ImageIcon(image);
				label = new JLabel(icon);
				size = label.getPreferredSize();
				label.setBounds(width - size.width, i * size.height, size.width, size.height);
				panel.add(label);
			}
		}
		
		add(panel, BorderLayout.CENTER);
		setSize(width, height);
		setVisible(true);
	}
	
	public void allPlayersDone() {
		all_players_done = true;
	}
	
	public void playerNum(int i, int num_choices, boolean played_before, Setup d) {
		player_num = i;
		Setup = d;
		this.num_choices = num_choices;
		this.played_before = played_before;
	}
	
	private void addCompanyToPlayer() {
		player.company = company;
		company.owner = player;
		company.loadInitialStats();
		if(!all_players_done) {
			Setup.playerChooseCompany(player_num + 1, num_choices, played_before);
		} else {
			Setup.setupPrelude(0);
		}
	}
}
