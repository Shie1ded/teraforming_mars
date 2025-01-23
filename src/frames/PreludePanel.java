package frames;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import board.Resources;
import player.Ability;
import player.Card;
import player.Player;

public class PreludePanel implements ActionListener {
	static public JPanel returnPanel(Card prelude, int scale_width, int scale_height) {
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		ImageIcon background = new ImageIcon("lib/templates/red_normal.png");
		Image image = background.getImage();
		image = image.getScaledInstance(scale_width, scale_height, Image.SCALE_SMOOTH);
		background = new ImageIcon(image);
		JLabel label = new JLabel(background);
		Dimension size = label.getPreferredSize();
		label.setBounds(0,0,size.width, size.height);
//		panel.add(label);
		
		label = new JLabel(prelude.name);
		size = label.getPreferredSize();
		label.setBounds(15, 5, size.width, size.height);
		panel.add(label);
		
		if(prelude.tag != null) {
			for(int i = 0; i<prelude.tag.length; i++) {
				ImageIcon icon = new ImageIcon("lib/tags/" + prelude.tag[i].toString().toLowerCase() + ".png");
				image = icon.getImage();
				image = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
				icon = new ImageIcon(image);
				label = new JLabel(icon);
				size = label.getPreferredSize();
				label.setBounds(scale_width - size.width * i, size.height, size.width, size.height);
				panel.add(label);
			}
		}
		
		return panel;
	}
	
	static public JLayeredPane returnLayeredPane(Card prelude, int scale_width, int scale_height) {
		JLayeredPane panel = new JLayeredPane();
		panel.setLayout(null);
		
		ImageIcon background = new ImageIcon("lib/templates/red_normal.png");
		Image image = background.getImage();
		image = image.getScaledInstance(scale_width, scale_height, Image.SCALE_SMOOTH);
		background = new ImageIcon(image);
		JLabel label = new JLabel(background);
		Dimension size = label.getPreferredSize();
		label.setBounds(0,0,size.width, size.height);
		panel.add(label, Integer.valueOf(1));
		
		label = new JLabel(prelude.name);
		label.setFont(new Font("Monospaced", Font.BOLD, 18));
		size = label.getPreferredSize();
		label.setBounds(80, 45, size.width, size.height);
		panel.add(label, Integer.valueOf(2));
		
		if(prelude.tag != null) {
			for(int i = 0; i<prelude.tag.length; i++) {
				ImageIcon icon = new ImageIcon("lib/tags/" + prelude.tag[i].toString().toLowerCase() + ".png");
				image = icon.getImage();
				image = image.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
				icon = new ImageIcon(image);
				label = new JLabel(icon);
				size = label.getPreferredSize();
				label.setBounds(scale_width - size.width * i - 80, 80, size.width, size.height);
				panel.add(label, Integer.valueOf(2));
			}
		}
		
		int recent_height = 0;
		
		if(prelude.resources_add != null) {
			String resources = "";
			for(Resources e: prelude.resources_add) resources += e.toString() + ", ";
			JTextArea text = new JTextArea("Add Resources: " + resources);
			text.setLineWrap(true);
			size = text.getPreferredSize();
			text.setBounds(0, 150 + recent_height, scale_width, size.height);
			recent_height += size.height + 10;
			panel.add(text, Integer.valueOf(2));
		}
		
		if(prelude.resources_remove != null) {
			String resources = "";
			for(Resources e: prelude.resources_remove) resources += e.toString() + ", ";
			JTextArea text = new JTextArea("Remove Resources: " + resources);
			text.setLineWrap(true);
			size = text.getPreferredSize();
			text.setBounds(0, 150 + recent_height, scale_width, size.height);
			recent_height += size.height + 10;
			panel.add(text, Integer.valueOf(2));
		}
		
		if(prelude.resources_remove_global != null) {
			String resources = "";
			for(Resources e: prelude.resources_remove_global) resources += e.toString() + ", ";
			JTextArea text = new JTextArea("Remove Resources from Others: " + resources);
			text.setLineWrap(true);
			size = text.getPreferredSize();
			text.setBounds(0, 150 + recent_height, scale_width, size.height);
			recent_height += size.height + 10;
			panel.add(text, Integer.valueOf(2));
		}
		
		if(prelude.resource_generation_add != null) {
			String resources = "";
			for(Resources e: prelude.resource_generation_add) resources += e.toString() + ", ";
			JTextArea text = new JTextArea("Resource Generation Add: " + resources);
			text.setLineWrap(true);
			size = text.getPreferredSize();
			text.setBounds(0, 150 + recent_height, scale_width, size.height);
			recent_height += size.height + 10;
			panel.add(text, Integer.valueOf(2));
		}
		
		if(prelude.resource_generation_remove != null) {
			String resources = "";
			for(Resources e: prelude.resource_generation_remove) resources += e.toString() + ", ";
			JTextArea text = new JTextArea("Resource Generation Remove: " + resources);
			text.setLineWrap(true);
			size = text.getPreferredSize();
			text.setBounds(0, 150 + recent_height, scale_width, size.height);
			recent_height += size.height + 10;
			panel.add(text, Integer.valueOf(2));
		}
		
		if(prelude.resource_generation_remove_global != null) {
			String resources = "";
			for(Resources e: prelude.resource_generation_remove_global) resources += e.toString() + ", ";
			JTextArea text = new JTextArea("Resource Generation Remove from Others: " + resources);
			text.setLineWrap(true);
			size = text.getPreferredSize();
			text.setBounds(0, 150 + recent_height, scale_width, size.height);
			recent_height += size.height + 10;
			panel.add(text, Integer.valueOf(2));
		}
		
		if(prelude.abilities != null) {
			String resources = "";
			for(Ability e: prelude.abilities) resources += e.toString() + ", ";
			JTextArea text = new JTextArea("Abilities: " + resources);
			text.setLineWrap(true);
			size = text.getPreferredSize();
			text.setBounds(0, 150 + recent_height, scale_width, size.height);
			recent_height += size.height + 10;
			panel.add(text, Integer.valueOf(2));
		}
		
		if(prelude.activate_abilities != null) {
			String resources = "";
			for(Ability e: prelude.activate_abilities) resources += e.toString() + ", ";
			JTextArea text = new JTextArea("Abilities: " + resources);
			text.setLineWrap(true);
			size = text.getPreferredSize();
			text.setBounds(0, 150 + recent_height, scale_width, size.height);
			recent_height += size.height + 10;
			panel.add(text, Integer.valueOf(2));
		}
		
		if(prelude.points != 0) {
			ImageIcon icon = new ImageIcon("lib/tags/mars.png");
			image = icon.getImage();
			image = image.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
			icon = new ImageIcon(image);
			label = new JLabel(icon);
			size = label.getPreferredSize();
			label.setBounds(scale_width - size.width - 10, scale_height - size.height - 10, size.width, size.height);
			panel.add(label, Integer.valueOf(2));
			
			label = new JLabel(String.valueOf(prelude.points));
			label.setFont(new Font("Monospaced", Font.BOLD, 26));
			size = label.getPreferredSize();
			label.setBounds(scale_width - 50, scale_height - 50, size.width, size.height);
			panel.add(label, Integer.valueOf(3));
		}
		
		
		return panel;
	}
	
	private ArrayList<Card> selected;
	private ArrayList<Card> options;
	private int player_number;
	private int choice;
	private Setup setup;
	private boolean all_players_done;
	private Player player;

	public void addStats(int choice, int player_number, ArrayList<Card> options, ArrayList<Card> selected, boolean all_players_done, Player player, Setup setup) {
		this.choice = choice;
		this.player_number = player_number;
		this.options = options;
		this.selected = selected;
		this.all_players_done = all_players_done;
		this.player = player;
		this.setup = setup;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		selected.add(options.remove(choice));
		
		if(selected.size() == 1) {
			setup.choosePreludes(player_number, options, selected);
			return;
		} 
		
		for(Card c: selected) player.hand.add(c);
		for(Card c: options) player.game.prelude_discard.add(c);
		if(!all_players_done) {
			setup.setupPrelude(player_number + 1);
			return;
		}
		
		setup.finishSetup();
	}
}
