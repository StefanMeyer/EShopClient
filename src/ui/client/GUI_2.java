package ui.client;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import commen.client.Clientverwaltung;
import ui.client.controller.SuchController;
import ui.client.panel.ArtikelPanel;
import ui.client.panel.KundenPanel;
import ui.client.panel.MenuePanel;
import ui.client.panel.MitarbeiterPanel;
import ui.client.panel.SuchPanel;
import ui.client.panel.WarenkorbButtonPanel;
import ui.client.panel.WarenkorbPanel;
import valueobjects.Account;
import valueobjects.Artikel;
import valueobjects.Kunde;
import valueobjects.Mitarbeiter;
import valueobjects.Warenkorb;

public class GUI_2 extends JFrame{

	private static final long serialVersionUID = -8552807729176148890L;
	private SuchController suchController = null;
	private Clientverwaltung shop;
	private Account user;
	
	//Menuebar
	private MenuePanel menuBar;
	
	//LayoutPanel
	private JPanel navframe = new JPanel();		
	private JPanel contentframe = new JPanel();	
	private JPanel mainPanel = new JPanel();
	
	private JPanel obenPanel = new JPanel();
		
	private MitarbeiterPanel mitarbeiterPanel;
	private KundenPanel kundenPanel;
	private SuchPanel suchPanel;
	private ArtikelPanel artikelPanel;
	private WarenkorbButtonPanel warenKorbButtons = new WarenkorbButtonPanel(this);;

	private WarenkorbPanel warenkorbPanel;
	
	//Konstrukter
	public GUI_2(Clientverwaltung client) {
		this.shop = client;
		this.user = this.shop.erstelleGastAccount();
		menuBar = new MenuePanel(this);			
		this.suchController = new SuchController(this);
		setTitle("E-Shop");
		setSize(800, 600); //Fenstergroesse
		//setResizable(false);
		this.initialize();
	}	
	
	//initialisieren
	private void initialize() {
		//Panels Initializieren
		artikelPanel = new ArtikelPanel(shop.gibAlleArtikel(), this);
		mitarbeiterPanel = new MitarbeiterPanel(this);
		kundenPanel = new KundenPanel(this);
		suchPanel = new SuchPanel(suchController, this);
		//Warenkorb erstellen.
		warenkorbPanel = new WarenkorbPanel(this);		
		//beendet das Programm durch klicken auf [X]
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.mainPanel.setLayout(new BorderLayout());
		this.navframe.setLayout(new BorderLayout());
		this.contentframe.setLayout(new BorderLayout());
		
		//Menuebar
		setJMenuBar(menuBar.getMenue());
		
		//SuchPanel
		suchPanelSetzen();
		
		//ObenPanel
		this.contentframe.add(obenPanel, BorderLayout.NORTH);
		
		//"norden splitten"
		obenPanelSetzen();
			
		//ArtikelPanel
		artikelPanelSetzen();
		
		//GUI setzen
		this.mainPanel.add(this.navframe,BorderLayout.NORTH);
		this.mainPanel.add(this.contentframe,BorderLayout.CENTER);	
		add(this.mainPanel);
		setVisible(true);
	}
	
	//ObenPanel
	public void obenPanelSetzen(){
		obenPanel.setLayout(new GridLayout(1,1));
		obenPanel.add(suchPanel);
		obenPanel.add(warenKorbButtons.getZumWarenKorbButton());
	}
	
	public void KundenPanelSetzen(){
		kundenPanel.setLayout(new GridLayout(1, 3));
		kundenPanel.setBorder(BorderFactory.createTitledBorder("Kundenbereich  -  Herzlich Willkommen: "+user.getName()+" !")); //Ueberschrift Kunden Login	
		navframe.add(kundenPanel, BorderLayout.NORTH);
		//nnicht benötige panel ausblenden
		obenPanel.setVisible(true);
	}
	
	public void MitarbeiterPanelSetzen(){
		mitarbeiterPanel.setLayout(new GridLayout(1, 3));
		navframe.add(mitarbeiterPanel, BorderLayout.NORTH);
		contentframe.add(mitarbeiterPanel.getContentframe());
		mitarbeiterPanel.setBorder(BorderFactory.createTitledBorder("Mitarbeiterbereich  -  Herzlich Willkommen: "+user.getName()+" !")); //Ueberschrift Mitarbeiter Login
		//nicht benötigte Panel ausblenden
		artikelPanel.setVisible(false);
		warenkorbPanel.setVisible(false);
	}	
	//SuchPanel
	public void suchPanelSetzen(){		
		obenPanel.add(suchPanel, BorderLayout.NORTH);	
	}
	
	//ArtikelPanel
	public void artikelPanelSetzen(){
		this.contentframe.add(artikelPanel, BorderLayout.CENTER);	
	}
		
	//Wenn Benutzer eingeloggt
	public void userLoggedIn(Account user) {
		//Menuebar anpassen 
		menuBar.setUserLoggedIn(true);
		if (user instanceof Kunde) {
			//kunde einloggen und warenkorb übernehmen
			if(!(user.getAccountNr() < 0)){
				Warenkorb wk = shop.getWarenkorb(this.user);
				this.user = user;
				shop.setWarenkorb(this.user, wk);
			}				
			//Panel einblenden
			KundenPanelSetzen();
		}
		else if(user instanceof Mitarbeiter) {
			obenPanel.setVisible(false);
			//mitarbeiter einloggen
			this.user = user;
			//Panel einblenden
			MitarbeiterPanelSetzen();
		}
		refresh();
	}
	
	//Wenn Benutzer ausgeloggt
	public void userLoggedOut(){
		if (user instanceof Kunde) {
			navframe.remove(kundenPanel);
		}else{
			navframe.remove(mitarbeiterPanel);
			contentframe.remove(mitarbeiterPanel.getContentframe());
			artikelPanel.setVisible(true);
			warenkorbPanel.setVisible(true);
		//TODO	untenframe.setVisible(true);			
		}
		JOptionPane.showMessageDialog(null,"Erfolgreich ausgeloggt!");
		//panelanzeige aktuallisieren
		refresh();
		//Gastkonto "reaktivieren"
		this.user = this.shop.erstelleGastAccount();
	}
	
	//warenkorb anzeigen
	public void zumWarenKorb(){
		
		obenPanel.setVisible(true);
	//TODO	untenframe.setVisible(true);
		
		//Artikel Panel aus ansicht entfernen
		contentframe.remove(artikelPanel);
		
		//Hinzufuegen hinzugefuegte Artikel Panel
		this.contentframe.add(warenkorbPanel);
		
		obenPanel.remove(suchPanel);
		contentframe.remove(suchPanel);
		
		obenPanel.remove(warenKorbButtons.getZumWarenKorbButton());
		obenPanel.add(warenKorbButtons.getZumShop());
		refresh();
	}
	
	public void zumShopButton(){	
		
		obenPanel.setVisible(true);
		
		obenPanel.remove(warenKorbButtons.getZumShop());
		contentframe.remove(warenkorbPanel);
	//TODO	contentframe.remove(untenWarenKorbBereichPanel);
	//TODO	untenWarenKorbBereichPanel.remove(warenKorbButtons.getKaufAbschliessenButton());		
		
		obenPanel.add(suchPanel);
		obenPanel.add(warenKorbButtons.getZumWarenKorbButton());
		contentframe.add(artikelPanel);
		
		refresh();
	}
	/**Fügt einen Artikel in den Warenkorb
	 * 
	 * @param anzahl -> Anzahl der Artikel die Hinzugefï¿½gt werden sollen
	 * @throws NumberFormatException
	 */
	public void zumWarenkorbHinzufuegen(int anzahl) throws NumberFormatException {

		Artikel art = shop.artikelSuchen(Integer.parseInt((this.artikelPanel.getArtikeltable().getValueAt(this.getArtikelPanel().getAusgabeTabelle().convertRowIndexToModel(this.getArtikelPanel().getAusgabeTabelle().getSelectedRow()),0)).toString()));
		Kunde kunde = (Kunde) this.user;		
		this.user = shop.inWarenkorbEinfuegen(art,anzahl,kunde);
		//aktuallisere Warenkorb
		Warenkorb wk = shop.getWarenkorb(this.user);
        this.getWarenkorbPanel().updateData(wk);
	}
		
	public void ausWarenkorbentfernen(int artikelnummer) {
		this.user = shop.ausWarenkorbloechen(shop.artikelSuchen(artikelnummer), (Kunde) user);
        this.getWarenkorbPanel().updateData(shop.getWarenkorb(this.user));
	}		
	
	/** Fügt einen Artikel dem shop hinzu
	 * 
	 * @param atkl
	 */
	public void addArtikel(String artikelname,int artikelnummer, int bestand,float preis,int packungsgroesse) {
/*		
		try {
			shop.fuegeArtikelEin(artikelname, artikelnummer, bestand, preis, packungsgroesse);
			try {
				shop.schreibeArtikeldaten();
				JOptionPane.showMessageDialog(null,"Artikel erfolgreich hinzugefuegt!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (ArtikelExistiertBereitsException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
		*/		
	}
	/** Lï¿½scht einen Artikel aus dem Shop
	 * 
	 * @param atkl
	 */	
	public void deleteArtikel(int nr) {
	/*	try {
			shop.entferneArtikel(nr);
		} catch (ArtikelExistiertNichtException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}	
	public void aendereArtikel(String artikelname,int artikelnummer, int bestand,float preis,int packungsgroesse) {
		
		
		shop.aendereArtikel(artikelname, artikelnummer, bestand, preis, packungsgroesse);
		shop.schreibeArtikeldaten();
		shop.schreibeStatsdaten();
	}
	public void untenWarenKorbBereichPanel(boolean b){
	//TODO	untenWarenKorbBereichPanel.setVisible(b);
	}
	
	//Getter und Setter
	public ArtikelPanel getArtikelPanel() {
		return artikelPanel;
	}
	
	public void setArtikelPanel(ArtikelPanel artikelPanel) {
		this.artikelPanel = artikelPanel;
	}
	
	public Clientverwaltung getShop() {
		return shop;
	}
	
	public Account getUser() {
		return user;
	}

	public void setUser(Account user) {
		this.user = user;
	}
	
	public WarenkorbPanel getWarenkorbPanel() {
		return warenkorbPanel;
	}

	public void setWarenkorbPanel(WarenkorbPanel warenkorbPanel) {
		this.warenkorbPanel = warenkorbPanel;
	}
	
	public JPanel getObenPanel() {
		return obenPanel;
	}

	public void setObenPanel(JPanel obenPanel) {
		this.obenPanel = obenPanel;
	}
	public WarenkorbButtonPanel getWarenKorbButtons() {
		return warenKorbButtons;
	}	
	//refresht alle Panels
	public void refresh(){

		mainPanel.revalidate();
		contentframe.revalidate();
		obenPanel.revalidate();
		
		mainPanel.repaint();
		contentframe.repaint();
		navframe.repaint();
	}
}