package ui.client.panel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import commen.client.Clientverwaltung;
import ui.client.ArtikelTableModel;
import ui.client.GUI_2;
import valueobjects.Account;
import valueobjects.Artikel;
import valueobjects.Kunde;
import valueobjects.Rechnung;


public class RechnungsPanel extends JPanel{
	private GUI_2 gui;
	private float gesamtpreis = 0.0f;
	private JLabel gesamt = new JLabel();
	private ArtikelTableModel artikeltable;
	private JTable ausgabeTabelle = null;
	private JScrollPane scrollPane;
	
	//Konstruktor
	public RechnungsPanel(GUI_2 gui){
		this.gui = gui;
		Clientverwaltung shop = gui.getShop();
		Rechnung rechnung = null;
		Kunde user =(Kunde) this.gui.getUser();
		rechnung = shop.kaufAbwickeln((Kunde) user );		
		
		final JFrame rechnungFenster = new JFrame();
		rechnungFenster.setTitle("Rechnung");
		rechnungFenster.setSize(500, 500);
		rechnungFenster.getContentPane().setLayout(new GridLayout(3, 1));

		JPanel oben = new JPanel();
		JPanel unten = new JPanel();
		JPanel ganz_unten = new JPanel();

		JPanel links_oben = new JPanel();
		JPanel links_unten = new JPanel();

		JLabel datum = new JLabel("Kaufdatum: "+ rechnung.getDatum());
		JLabel kaeufer = new JLabel(user.getName() + " ");
		JLabel strasse = new JLabel(((Kunde) user).getStrasse());
		JLabel plzUndOrt = new JLabel(((Kunde) user).getPlz() + " "+ ((Kunde) user).getWohnort());

		rechnungFenster.getContentPane().add(oben);
		rechnungFenster.getContentPane().add(unten);
		rechnungFenster.getContentPane().add(ganz_unten);
		rechnungFenster.setVisible(true);

		oben.setLayout(new GridLayout(1, 1));

		unten.setLayout(new GridLayout(2, 1));

		oben.add(links_oben);
		oben.add(links_unten);

		// oben kommt das Datum und die Adresse des Kunden hin

		links_oben.setLayout(new GridLayout(4, 1));
		links_oben.setBorder(BorderFactory.createTitledBorder("Kundendaten"));
		links_oben.add(datum);
		links_oben.add(kaeufer);
		links_oben.add(strasse);
		links_oben.add(plzUndOrt);

		// unten kommen die gekauften Artikel hin

		unten.setBorder(BorderFactory.createTitledBorder("Gekaufte Artikel"));


		// TableModel als "Datencontainer" anlegen:
		artikeltable = new ArtikelTableModel(false,false);
		
		// JTable-Objekt erzeugen und mit Datenmodell initialisieren:
		ausgabeTabelle = new JTable(artikeltable);
		ausgabeTabelle.setAutoCreateRowSorter(true);
		
		// JTable in ScrollPane platzieren:
		scrollPane = new JScrollPane(ausgabeTabelle);
		// Anzeige der Artikelliste auch in der Kunden-Ansicht
		artikeltable.setDataVector2(user.getWarenkorb(),"");
		unten.add(scrollPane);			

		gesamtpreis = rechnung.getGesamtpreis();
		gesamt.setText("Gesamtpreis: " + gesamtpreis + "€");

		


		//Fuer Button Schliessen
		JButton schliessen = new JButton("Schliessen");

		ganz_unten.setLayout(new GridLayout(4, 1));
		ganz_unten.add(gesamt);
		ganz_unten.add(schliessen);
		
		//Fuer schliessen Button
		schliessen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEv) {
				rechnungFenster.setVisible(false);
			}
		});//Ende schliessen Button				
		
		
		
		
		
	}
	
}