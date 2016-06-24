package ui.client.panel;

import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import valueobjects.Artikel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import ui.client.ArtikelTableModel;
import ui.client.ButtonEditor;
import ui.client.ButtonRenderer;
import ui.client.GUI_2;

public class ArtikelPanel extends JPanel{
	
	private ArtikelTableModel artikeltable;
	private JTable ausgabeTabelle;
	private JScrollPane scrollPane;
	private GUI_2 gui;
	
	//Konstruktor
	public ArtikelPanel(List<Artikel> artikelliste, GUI_2 gui) {
		this.gui = gui;
		
		//JPanel artikelPanel = new JPanel();
		this.setLayout(new GridLayout());
		this.setBorder(BorderFactory.createTitledBorder("Artikel")); //Ueberschrift Artikel
		
		// TableModel als "Datencontainer" anlegen:
		artikeltable = new ArtikelTableModel(true,false);
		
		// Artikel-Liste aufbereiten
		//artikeltable.setDataVector(new Vector<Artikel>(),"Kaufen");
		
		// JTable-Objekt erzeugen und mit Datenmodell initialisieren:
		ausgabeTabelle = new JTable(artikeltable);
		ausgabeTabelle.setAutoCreateRowSorter(true);

		// JTable in ScrollPane platzieren:
		scrollPane = new JScrollPane(ausgabeTabelle);
				
		// Anzeige der Artikelliste auch in der Kunden-Ansicht
		artikeltable.setDataVector(artikelliste, "kaufen");	
		this.add(scrollPane);
		renderOption();
	
		//setArtikelPanel(artikelPanel);
	}
	
	public void renderOption() {
		 ActionListener listen = new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
			    //warenkorbpanel.wieOftArtikelZumWarenKorb();
			    try {
					JLabel wieOftArtikelKaufenLabel = new JLabel("Wie oft wollen Sie den Artikel kaufen?");
					final JTextField anzahl = new JTextField();
					JButton inDenWarenkorbButton = new JButton("In den Warenkorb");
					//TODO H�bsch machen	
					final JFrame wieOftArtikelKaufenFrame = new JFrame();
					wieOftArtikelKaufenFrame.getContentPane().setLayout(new GridLayout(2, 1));
					wieOftArtikelKaufenFrame.setSize(450, 100);
					wieOftArtikelKaufenFrame.getContentPane().add(wieOftArtikelKaufenLabel);
					wieOftArtikelKaufenFrame.getContentPane().add(anzahl);
					wieOftArtikelKaufenFrame.getContentPane().add(inDenWarenkorbButton);
					wieOftArtikelKaufenFrame.setVisible(true);
					
					inDenWarenkorbButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							try { 
								//Zum Warenkorb hinzuf�gen
								int anz = Integer.parseInt(anzahl.getText());
								gui.zumWarenkorbHinzufuegen(anz);
								wieOftArtikelKaufenFrame.setVisible(false);
							} catch (NumberFormatException e) {
								e.printStackTrace();
							}
						}
					});

				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				}
			}
	    };
		//Layout Tabelle -> Button fuer "Option"
		ausgabeTabelle.getColumn("Option").setCellRenderer(new ButtonRenderer());
		ausgabeTabelle.getColumn("Option").setCellEditor(
	        new ButtonEditor(new JCheckBox(), listen));	
		
	}
	
	//Getter und Setter
	public ArtikelTableModel getArtikeltable() {
		return artikeltable;
	}
	public void setArtikeltable(ArtikelTableModel artikeltable) {
		this.artikeltable = artikeltable;
	}

	public JTable getAusgabeTabelle() {
		return this.ausgabeTabelle;
	}
	
	/*public JPanel getArtikelPanel() {
		return this.artikelPanel;
	}
	public void setArtikelPanel(JPanel artikelPanel) {
		this.artikelPanel = artikelPanel;
	}	
	*/
}
