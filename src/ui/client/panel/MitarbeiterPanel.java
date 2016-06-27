package ui.client.panel;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import commen.client.Clientverwaltung;
//import domain.exceptions.AccountExistiertBereitsException;
import ui.client.ArtikelTableModel;
import ui.client.ButtonEditor;
import ui.client.ButtonRenderer;
import ui.client.GUI_2;
import valueobjects.Artikel;
import valueobjects.Stats;


public class MitarbeiterPanel extends JPanel{
	
	private GUI_2 gui;
	private JPanel artikelPanel = new JPanel();
	private ArtikelTableModel artikeltable;
	private JTable ausgabeTabelle;
	private JScrollPane scrollPane;
	public JButton statistikButton = new JButton("Statistik",new ImageIcon("src/assets/statistikIcon.png"));
	private JButton artikelHinzufuegenButton = new JButton("Artikel hinzufuegen", new ImageIcon("src/assets/artikelHinzufuegenIcon.png"));
	private JButton artikelLoeschenButton = new JButton("Artikel löschen", new ImageIcon("src/assets/artikelLöschenIcon.png"));
	private JButton mitarbeiterHinzufuegenButton = new JButton("Mitarbeiter hinzufuegen", new ImageIcon("src/assets/mitarbeiterHinzufuegenIcon.png"));
	//private JButton refreshButton = new JButton("refresh", new ImageIcon("src/assets/refreshIcon.png"));
	private GraphPanel gP;
	protected String[] args;
	
	//Konstruktor
	public MitarbeiterPanel(GUI_2 gui) {
		
		this.gui = gui;

		//Artikelliste f�r Mitarbeiter erstellen
		
		artikelPanel.setLayout(new GridLayout());
		artikelPanel.setBorder(BorderFactory.createTitledBorder("Artikel")); //Ueberschrift Artikel
		
		// TableModel als "Datencontainer" anlegen:
		artikeltable = new ArtikelTableModel(true,true);
		
		// Artikel-Liste aufbereiten
		//artikeltable.setDataVector(new Vector<Artikel>(),"Kaufen");
		
		// JTable-Objekt erzeugen und mit Datenmodell initialisieren:
		ausgabeTabelle = new JTable(artikeltable);
		ausgabeTabelle.setAutoCreateRowSorter(true);

		// JTable in ScrollPane platzieren:
		scrollPane = new JScrollPane(ausgabeTabelle);
				
		// Anzeige der Artikelliste auch in der Kunden-Ansicht
		artikeltable.setDataVector(gui.getShop().gibAlleArtikel(), "Speichern");	
		renderOption();	
		artikelPanel.add(scrollPane);
			
		add(statistikButton);
		add(artikelHinzufuegenButton);
		add(artikelLoeschenButton);
		add(mitarbeiterHinzufuegenButton);
		//add(refreshButton);
		
		hinzufugenPanel(); 
		
		statistikButtonGedrueckt();
		
		/*refreshButton.addActionListener(new ActionListener() { 
			
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("refresh");
//TODO artikelliste aktualliseren
			}
		});		
		*/	
	}			
	
	private void renderOption() {
		 ActionListener listen = new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
					try {
						//geaenderte Daten auslesen
						int artikelnummer = Integer.parseInt((artikeltable.getValueAt(ausgabeTabelle.getSelectedRow(),0)).toString());
						String artikelname =  artikeltable.getValueAt(ausgabeTabelle.getSelectedRow(), 1).toString();
						int bestand = Integer.parseInt((artikeltable.getValueAt(ausgabeTabelle.getSelectedRow(),2)).toString());
						float preis = Float.parseFloat((artikeltable.getValueAt(ausgabeTabelle.getSelectedRow(),3)).toString());
						int packungsgroesse = Integer.parseInt((artikeltable.getValueAt(ausgabeTabelle.getSelectedRow(),4)).toString());
						//�nderungen speichern
						gui.aendereArtikel(artikelname,artikelnummer, bestand,preis,packungsgroesse);							
						JOptionPane.showMessageDialog(null,"Artikel�nderungen Gespeichert");
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}    				
		    };
		};
		//Layout Tabelle -> Button fuer "Option"
		ausgabeTabelle.getColumn("Option").setCellRenderer(new ButtonRenderer());
		ausgabeTabelle.getColumn("Option").setCellEditor(new ButtonEditor(new JCheckBox(), listen));		
	}

	//Getter uns Setter
	public JButton getStatistikButton() {
		return statistikButton;
	}

	public void setStatistikButton(JButton statistikButton) {
		this.statistikButton = statistikButton;
	}
	
	public JButton getArtikelHinzufuegenButton() {
		return artikelHinzufuegenButton;
	}

	public void setArtikelHinzufuegenButton(JButton artikelHinzufuegenButton) {
		this.artikelHinzufuegenButton = artikelHinzufuegenButton;
	}
	
	public void hinzufugenPanel() {
		final JFrame artikelHinzufuegenFrame = new JFrame();

		artikelHinzufuegenFrame.setSize(400, 300);
		artikelHinzufuegenFrame.setLayout(new GridLayout(10, 1));

		JLabel artikelname = new JLabel("Artikelname:");
		artikelHinzufuegenFrame.add(artikelname);

		final JTextField artikelnameFeld = new JTextField();
		artikelHinzufuegenFrame.add(artikelnameFeld);

		JLabel artikelnummer = new JLabel("Artikelnummer:");
		artikelHinzufuegenFrame.add(artikelnummer);

		final JTextField artikelnummerFeld = new JTextField();
		artikelHinzufuegenFrame.add(artikelnummerFeld);

		JLabel preis = new JLabel("Preis:");
		artikelHinzufuegenFrame.add(preis);

		final JTextField preisFeld = new JTextField();
		artikelHinzufuegenFrame.add(preisFeld);

		JLabel bestand = new JLabel("Bestand:");
		artikelHinzufuegenFrame.add(bestand);

		final JTextField bestandFeld = new JTextField();
		artikelHinzufuegenFrame.add(bestandFeld);
		
		JLabel packungsgroesse = new JLabel("Packungsgroesse:");
		artikelHinzufuegenFrame.add(packungsgroesse);

		final JTextField packungsgroesseFeld = new JTextField();
		artikelHinzufuegenFrame.add(packungsgroesseFeld);
		
		JLabel platzhalter = new JLabel("");
		artikelHinzufuegenFrame.add(platzhalter);
		
		JButton hinzufuegenButton = new JButton("hinzufuegen");
		artikelHinzufuegenFrame.add(hinzufuegenButton);
		artikelHinzufuegenFrame.setVisible(false);
		artikelHinzufuegenButton.addActionListener(new ActionListener() { 
			
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Artikel hinzufuegen");
				
				//Fuer Menue Account -> Registrieren Button
				artikelHinzufuegenFrame.setVisible(true);
					
				//Artikel hinzufuegen
				hinzufuegenButton.addActionListener(new ActionListener() { 
			
					public void actionPerformed(ActionEvent arg0) {
						
						String artikelname = artikelnameFeld.getText();
						int artikelnummer = Integer.parseInt(artikelnummerFeld.getText());
						int bestand = Integer.parseInt(bestandFeld.getText());
						float preis = Float.parseFloat(preisFeld.getText());
						int packungsgroesse = Integer.parseInt(packungsgroesseFeld.getText());
						gui.addArtikel(artikelname,artikelnummer, bestand,preis,packungsgroesse);
						artikelHinzufuegenFrame.setVisible(false);	
					}
				});
			}
		});	
		
		//Artikel löschen Button
		artikelLoeschenButton.addActionListener(new ActionListener() { 

			public void actionPerformed(ActionEvent arg0) {
				
				System.out.println("ARTIKEL LÖSCHEN");
								
					gui.getShop().entferneArtikel(Integer.parseInt(((ausgabeTabelle.getValueAt(ausgabeTabelle.getSelectedRow(), 0)).toString())));
					artikeltable.setDataVector(gui.getShop().gibAlleArtikel(), "Speichern");
					renderOption();	
					JOptionPane.showMessageDialog(null, "Artikel wurde gelöscht.");
					gui.getShop().schreibeArtikeldaten();
			}
			
		});
		
		
		
		//Mitarbiter hinzufügen Button
		mitarbeiterHinzufuegenButton.addActionListener(new ActionListener() { 

			public void actionPerformed(ActionEvent arg0) {
				
				final JFrame maHinzufuegenFrame = new JFrame();
				
				maHinzufuegenFrame.setSize(200, 300);
				maHinzufuegenFrame.setLayout(new GridLayout(6, 1));
		
				JLabel maName = new JLabel("Mitarbeitername:");
				maHinzufuegenFrame.add(maName);
				
				final JTextField maNameFeld = new JTextField();
				maHinzufuegenFrame.add(maNameFeld);
				
				JLabel maPasswort = new JLabel("Mitarbeiterpasswort:");
				maHinzufuegenFrame.add(maPasswort);
				
				final JTextField maPasswortFeld = new JTextField();
				maHinzufuegenFrame.add(maPasswortFeld);
				
				JLabel platzhalter = new JLabel("");
				maHinzufuegenFrame.add(platzhalter);
				
				JButton hinzufuegenButton = new JButton("Hinzufügen");
				maHinzufuegenFrame.add(hinzufuegenButton);
				
				maHinzufuegenFrame.setVisible(true);
				
				hinzufuegenButton.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent arg0) {
						System.out.println("Mitarbeiter hinzufügen Button");

						//hole Name und Passwort aus Textfelder
						String name = maName.getText();
						String passwort = maPasswort.getText();
						
//						try {
							gui.getShop().fuegeMitarbeiterAccountEin(name, passwort);
							gui.getShop().schreibeMitarbeiterdaten();
							maHinzufuegenFrame.setVisible(false);
					}
				});
			}
		});
		
	}
	
	public void statistikButtonGedrueckt() {
		statistikButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent arg0) {
				JFrame frame = new JFrame("");
		 	      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 	      	   
		 	      JPanel layout = new JPanel();
		 	      JPanel nav = new JPanel();
		 	      layout.setLayout(new BorderLayout());
		 	      nav.setLayout(new GridLayout(5,1));
		 		   List <Stats> alleStats = gui.getShop().gibAlleStats();
		 			if (alleStats.isEmpty()) {
		 				System.out.println("Keine Statistiken verfügbar");
		 			} else {
		 				Iterator<Stats> iter = alleStats.iterator();
		 				int lastartikelnummer = 0;
		 				int max_bes = 0;
		 				while (iter.hasNext()) {
		 					Stats statslist2 = iter.next();
		 					//Für jeden artikel ein Listenemelemt erstellen
		 					if (statslist2.getArklnummer() != lastartikelnummer) {
		 						//neues Listenfeld erzeugen
		 						JButton button = new JButton("Statistik für: " + statslist2.getAtklname());
		 						button.addActionListener(new ActionListener() { 
		 							public void actionPerformed(ActionEvent arg0) {
		 								System.out.println("layout " + statslist2.getAtklname());
		 								layout.add(new statsPanel(gui.getShop().statsSuchen(statslist2.getArklnummer())), BorderLayout.CENTER);
		 								frame.pack();
		 							}
		 						});
		 						nav.add(button);
		 						System.out.println("add: " + statslist2.getAtklname());
		 					}
		 					lastartikelnummer = statslist2.getArklnummer();
		 				}
		 			}	      
		 	      layout.add(nav, BorderLayout.WEST);
		 	      frame.getContentPane().add(layout);	
		 	      frame.pack();
		 	      frame.setLocationByPlatform(true);
		 	      frame.setVisible(true);	
			}
		});
	}
	
	

	public JPanel getContentframe() {
		return this.artikelPanel;
	}	
}
