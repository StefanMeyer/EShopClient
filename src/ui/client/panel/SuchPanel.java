package ui.client.panel;

import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ui.client.GUI_2;
import ui.client.controller.SuchController;


public class SuchPanel extends JPanel{

	private JTextField suchenTextField;
	private SuchController suchController;
	private GUI_2 gui;

	//Konstruktor
	public SuchPanel(SuchController suchController, GUI_2 gui) {
		
		this.suchController = suchController;
		this.gui = gui;
		
		initialize();
	}

	private void initialize() {

		this.setLayout(new GridLayout(1, 2));

		suchenTextField = new JTextField();
		this.add(suchenTextField);

		JButton suchButton = new JButton("Suchen");
		this.setBorder(BorderFactory.createTitledBorder("Suchen")); //Ueberschrift Suchen
		this.add(suchButton);
		
		//Actionlostener
		suchButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				suchen(suchenTextField.getText());
			}
		});		
	}

	private void suchen(String text) {
		suchController.suchen(text);
	}
}

