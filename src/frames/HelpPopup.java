package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import player.Abilities;
import player.Ability;

@SuppressWarnings("serial")
public class HelpPopup extends JFrame{
	JPanel panel;
	static int width = 350;
	static int height = 350;
	
	HelpPopup() {
		super("Help");
		panel = new JPanel();
		createPopup();
	}
	
	private void createPopup() {
		reset();
		getContentPane().setBackground(new Color(210, 210, 210));

		JLabel label = new JLabel("Describe Ability");
		JTextField myTextField = new JTextField("PlantSteel_to_MegaCredit");
		JPanel tempPanel = new JPanel(new BorderLayout());
		tempPanel.add(label,BorderLayout.WEST);
		tempPanel.add(myTextField,BorderLayout.CENTER);
		Dimension size = tempPanel.getPreferredSize();
		tempPanel.setBounds(width / 2 - size.width / 2, 50, size.width, size.height);
		panel.add(tempPanel);
		
		JTextArea text = new JTextArea();
		text.setLineWrap(true);
		size = text.getPreferredSize();
		text.setBounds(width / 2 - size.width / 2, 100, 350, size.height);
		panel.add(text);
		
		JButton button1 = new JButton("Search");
		size = button1.getPreferredSize();
		button1.setBounds(width / 2 - size.width / 2, height - 50, size.width, size.height);
		button1.addActionListener(e -> {
			Component[] components = panel.getComponents();
			try {
				Ability a = Ability.valueOf(((JTextField) (((JPanel) components[0]).getComponents())[1]).getText());
				((JTextArea) components[1]).setText(Abilities.describe(a));
			} catch (Exception err) {
				((JTextArea) components[1]).setText("Invalid ability");
			}
		});
		panel.add(button1);
		
		add(panel,BorderLayout.CENTER);
		setSize(width, height);
		setVisible(true);
	}
	
	public void reset() {
		panel.removeAll();
		panel.revalidate();
		validate();
		repaint();
	}
	
}
