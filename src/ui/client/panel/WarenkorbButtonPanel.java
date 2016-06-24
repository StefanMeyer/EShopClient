package ui.client.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JTextField;

import commen.client.Clientverwaltung;

import ui.client.GUI_2;
import valueobjects.Account;
import valueobjects.Artikel;
import valueobjects.Kunde;


public class WarenkorbButtonPanel extends JPanel{
	
	private GUI_2 gui;
	
	private JButton zumWarenKorbButton = new JButton("zum Warenkorb",new ImageIcon("src/assets/warenkorbIcon.png"));
	private JButton kaufAbschliessenButton = new JButton("Kauf abschliessen");	
	private JButton zumShop = new JButton("zurueck zum Shop");
	private JButton inDenWarenkorbButton = new JButton("In den Warenkorb");	
	
	private JLabel wieOftArtikelKaufenLabel = new JLabel("Wie oft wollen Sie den Artikel kaufen?");
	private JFrame wieOftArtikelKaufenFrame = new JFrame();
	private JTextField anzahl = new JTextField();
	
	//Konstruktor
	public WarenkorbButtonPanel(GUI_2 gui) {
		this.gui = gui;
		//Fuer Warenkorb Button "zum Warenkorb"
		//ACTIONLISTINER
		zumWarenKorbButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gui.zumWarenKorb();
			}
		});	
		//Fuer Warenkorb Button "zum Shop"
		//ACTIONLISTINER
		zumShop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("zum Shop ausgefuehrt");	
				gui.zumShopButton();
			}
		});	
		//Fuer Warenkorb Button "Kauf abschließen"	
		//ACTIONLISTINER
		kaufAbschliessenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				System.out.println("Kauf Abschließen Button");
				//JOptionPane.showMessageDialog(null,"bitte einloggen!");
				//rechnungspanel.actionPerformed(arg0);
				//prï¿½fen ob User eingelogt?
				Account user = gui.getUser();
				//JOptionPane.showMessageDialog(null,"bitte einloggen!");
				if(!(user.getAccountNr() < 0)){
					int jaNein = JOptionPane.showConfirmDialog(null,"Bestellung abschliessen?");
					if (jaNein == 0) {
						Clientverwaltung shop = gui.getShop();
						HashMap<Artikel, Integer> fehlerliste = shop.pruefeKauf((Kunde) user);
						if (!fehlerliste.isEmpty()) {
							JOptionPane.showMessageDialog(null,"Es konnten nicht alle Artikel zum Kauf angeboten werden.");				
							} else {
								RechnungsPanel rechnung = new RechnungsPanel(gui);			
						}
					}
					else if (user.getAccountNr() < 0) {
						System.out.println("Gast konto");
						//user muss sich erst einloggen 	
					}
				}else{
					JOptionPane.showMessageDialog(null,"bitte einloggen!");
						
				}//Ende else if(user instanceof Kunde)
			}//Ende public void actionPerformed(ActionEvent e)
		});//Endekauf AbschliessenButton.addActionListener(new ActionListener()
	}
	public JButton getZumWarenKorbButton() {
		return zumWarenKorbButton;
	}

	public JButton getKaufAbschliessenButton() {
		return kaufAbschliessenButton;
	}

	public JButton getZumShop() {
		return zumShop;
	}	
}

