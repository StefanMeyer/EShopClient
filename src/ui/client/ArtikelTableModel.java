package ui.client;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;


import valueobjects.Account;
import valueobjects.Artikel;
import valueobjects.Kunde;
import valueobjects.Massengutartikel;
import valueobjects.Mitarbeiter;
import valueobjects.Warenkorb;


public class ArtikelTableModel extends DefaultTableModel {
	boolean edit = false;
	boolean option = false;
	public ArtikelTableModel(boolean option, boolean edit) {
		// Ober-Objekt der Klasse DefaultTableModel initialisieren		
		super();
		this.edit = edit;
		this.option = option;
	}
	
		// Tabellendaten hinzufügen
		public void setDataVector(List<Artikel> articles, String optionname) {
			// DefaultTableModel erwartet Repräsentation der Tabellendaten
			// als Vector von Vectoren
			Vector spalten = new Vector();		
			spalten.add("Nummer");
			spalten.add("Name");
			spalten.add("Bestand");
			spalten.add("Preis");
			spalten.add("Packungsgroesse");
			if (option) spalten.add("Option");
			// Spaltennamen in geerbtem Attribut merken
			this.columnIdentifiers = spalten;
			Vector rows = new Vector();

			for (Artikel artikel: articles) {
				Vector einArtikelAlsVector = new Vector();
				einArtikelAlsVector.add(artikel.getNummer());
				einArtikelAlsVector.add(artikel.getName());
				einArtikelAlsVector.add(artikel.getBestand());
				einArtikelAlsVector.add(artikel.getPreis());
				if (artikel instanceof Massengutartikel) {
					einArtikelAlsVector.add(((Massengutartikel) artikel).getPackungsgroesse());
				}else{
					einArtikelAlsVector.add(1);
				}
				if (!optionname.isEmpty()) einArtikelAlsVector.add(optionname);
				rows.add(einArtikelAlsVector);
			}
			this.setDataVector(rows, columnIdentifiers);
		}
	
	
	/**
	 * Tabellendaten hinzufügen (für den Warenkorb)
	 * @param optionname 
	 */
	
	public void setDataVector2(Warenkorb warenkorb, String optionname) {
		Set<Artikel> articles = warenkorb.getInhalt().keySet();
		Vector spalten = new Vector();		
		spalten.add("Nummer");
		spalten.add("Name");
		spalten.add("Packungsgroesse");
		spalten.add("Preis");
		spalten.add("Anzahl im Warenkorb");
		if (option) spalten.add("Option");
		// Spaltennamen in geerbtem Attribut merken
		this.columnIdentifiers = spalten;
		
		Vector rows = new Vector();
	
		for (Artikel artikel : articles) {

			Vector einArtikelAlsVector = new Vector();
			einArtikelAlsVector.add(artikel.getNummer());
			einArtikelAlsVector.add(artikel.getName());
			if (artikel instanceof Massengutartikel) {
				einArtikelAlsVector.add(((Massengutartikel) artikel).getPackungsgroesse());
			}else{
				einArtikelAlsVector.add(1);
			}
			einArtikelAlsVector.add(artikel.getPreis());
			einArtikelAlsVector.add((Integer)warenkorb.getInhalt().get(artikel));
			if (!optionname.isEmpty()) einArtikelAlsVector.add(optionname);
			rows.add(einArtikelAlsVector);
		}
		this.setDataVector(rows, columnIdentifiers);
	}
	
    public boolean isCellEditable(int row, int col) {
    	//Tabelle bearbeitbar?
    	if (this.edit == false) {
    		//Optionen freigeben
    		if (col > 4) return true;
    		return false;
    	}
    	//artikelnummern dürfen nicht geaednert werden .. und packungsgrößen auch net
        if (col < 1 || col == 4) {
          return false;
        } else {
          return true;
        }
      }
}
