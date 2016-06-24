package ui.client.panel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import commen.client.Clientverwaltung;
import ui.client.GUI_2;
import valueobjects.Account;
import valueobjects.Kunde;
import valueobjects.Mitarbeiter;

public class LoginPanel extends JPanel implements ActionListener{

	private GUI_2 gui;

	//Konstruktor
	public LoginPanel(GUI_2 gui) {
		this.gui = gui;
	}
	
	//ACTIONLISTENER
	public void actionPerformed(ActionEvent arg0) {
		String command = arg0.getActionCommand();
		
		//Fuer Menue Account -> Einloggen Button
		if(command.equals("Einloggen")){
			
			final JFrame login = new JFrame();
	
			login.setSize(200, 300);
			login.setLayout(new GridLayout(6, 1));
	
			JLabel labelname = new JLabel("Name:");
			login.add(labelname);
			
			final JTextField nameFeld = new JTextField();
			login.add(nameFeld);
	
			JLabel labelpasswort = new JLabel("Passwort:");
			login.add(labelpasswort);
	
			final JPasswordField passwortFeld = new JPasswordField();
			login.add(passwortFeld);
			
			JLabel platzhalter = new JLabel("");
			login.add(platzhalter);
			
			JButton loginButton = new JButton("Login");
			login.add(loginButton);
			login.setVisible(true);	

			//Fuer Menue Account -> Einloggen -> Login Button
			loginButton.addActionListener(new ActionListener() { 
				
				public void actionPerformed(ActionEvent arg0) {
			
					System.out.println("loginbutton");

					//hole Name und Passwort aus Textfelder
					String name = nameFeld.getText();
					String passwort = String.valueOf(passwortFeld.getPassword());
			
					Clientverwaltung shop = gui.getShop();
					Account user = shop.loginAccount(name, passwort);
					
					if (user instanceof Kunde) {	
						
						login.setVisible(false);	
						gui.userLoggedIn(user); //wenn eingeloggt, loginPanel sichtbar

						JOptionPane.showMessageDialog(null,"Erfolgreich als Kunde eingeloggt!");	
					}
					else if (user instanceof Mitarbeiter){
						
						login.setVisible(false);
						gui.userLoggedIn(user); //wenn eingeloggt, loginPanel sichtbar
						
						JOptionPane.showMessageDialog(null,"Erfolgreich als Mitarbeiter eingeloggt!");
					}
				}
			});	
		}
		//Fuer Menue Account -> Registrieren Button
		else if (command.equals("Registrieren")){
			final JFrame registrieren = new JFrame();

			registrieren.setSize(400, 300);
			registrieren.setLayout(new GridLayout(12, 1));

			JLabel name = new JLabel("Name:");
			registrieren.add(name);

			final JTextField nameFeld = new JTextField();
			registrieren.add(nameFeld);

			JLabel passwort = new JLabel("Passwort:");
			registrieren.add(passwort);

			final JPasswordField passwortFeld = new JPasswordField();
			registrieren.add(passwortFeld);

			JLabel adresse = new JLabel("Adresse:");
			registrieren.add(adresse);

			final JTextField adressFeld = new JTextField();
			registrieren.add(adressFeld);

			JLabel plz = new JLabel("Postleitzahl:");
			registrieren.add(plz);

			final JTextField plzFeld = new JTextField();
			registrieren.add(plzFeld);

			JLabel wohnort = new JLabel("Ort:");
			registrieren.add(wohnort);

			final JTextField ortFeld = new JTextField();
			registrieren.add(ortFeld);
			
			JLabel platzhalter = new JLabel("");
			registrieren.add(platzhalter);

			JButton regButton = new JButton("Registrieren");
			registrieren.add(regButton);
			registrieren.setVisible(true);
			
			//Fuer Menue Account -> Registrieren -> Registrieren Button
			regButton.addActionListener(new ActionListener() { 
		
				public void actionPerformed(ActionEvent arg0) {
					
					//hole Name, Passwort, Starsse, PLZ und Ort aus Textfelder
					String name = nameFeld.getText();
					String passwort = String.valueOf(passwortFeld.getPassword());
					String strasse = adressFeld.getText();
					int plz = Integer.parseInt(plzFeld.getText());
					String ort = ortFeld.getText();
					
					Clientverwaltung shop = gui.getShop();
					shop.fuegeKundenAccountEin(name, passwort, strasse, plz, ort);
					shop.schreibeKundendaten();
					JOptionPane.showMessageDialog(null,"Erfolgreich als Kunde registriert!");
					registrieren.setVisible(false);
				}
			});//Ende regButton.addActionListener(new ActionListener()
		}//Ende else if (command.equals("Registrieren"))
	}//public void actionPerformed(ActionEvent arg0)
}