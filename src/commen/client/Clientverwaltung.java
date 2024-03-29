package commen.client;

import java.net.Socket;
import java.util.HashMap;
import java.util.List;

import com.blogspot.debukkitsblog.Util.Client;
import com.blogspot.debukkitsblog.Util.Datapackage;
import com.blogspot.debukkitsblog.Util.Executable;

import ui.client.GUI_2;
import valueobjects.Account;
import valueobjects.Artikel;
import valueobjects.Kunde;
import valueobjects.Rechnung;
import valueobjects.Stats;
import valueobjects.Warenkorb;

/**
 * Stellt die Kommunikation mit den Server da, dem Server wird mitgeteilt was der Client f�r Daten braucht oder was er zu tun hat.  
 */

public class Clientverwaltung extends Client{
	private GUI_2 gui;
	
	public void setGui(GUI_2 gui) {
		this.gui = gui;
	}

	public Clientverwaltung(String address, int port, int timeout, boolean autoKill) {
		super(address, port, timeout, autoKill);
		//server infos verarbeiten
        registerMethod("NEWARTIKELDATA", new Executable() {
            @Override
            public void run(Datapackage data, Socket socket) {
                gui.getArtikelPanel().getArtikeltable().setDataVector((List<Artikel>) data.get(1),"Kaufen");
                gui.getArtikelPanel().renderOption();    
            }
        });
		start();
	}

	public List<Artikel> gibAlleArtikel() {
		Datapackage artikel = sendMessage(new Datapackage("GIBALLEARLTIKEL"));
		System.out.println("[Client] GibAlleArtikel:" + artikel.get(1));
		List<Artikel> list = (List<Artikel>) artikel.get(1);
		return list;
	}

	public Artikel artikelSuchen(int artikelnummer) {
		Datapackage artikel = sendMessage(new Datapackage("ARTIKELSUCHEN", artikelnummer));
		System.out.println("[Client] artikelSuchen:" + artikel.get(1));
		Artikel einartikel = (Artikel) artikel.get(1);
		return einartikel;
	}
	
	public List<Artikel> sucheNachArtikel(String suchText) {
		Datapackage artikel = sendMessage(new Datapackage("SUCHENACHARTIKEL",suchText));
		System.out.println("[Client] suchenachartikel:" + artikel.get(1));
		List<Artikel> list = (List<Artikel>) artikel.get(1);
		return list;
	}
	public Kunde inWarenkorbEinfuegen(Artikel art, int anzahl, Kunde user) {
		Datapackage kunde = sendMessage(new Datapackage("INWARENKORBEINFUEGEN", art, anzahl, user));
		System.out.println("[Client] inWarenkorbeinf�gen:" + kunde.get(1));
		return (Kunde) kunde.get(1);
	}

	public Kunde ausWarenkorbloechen(Artikel artikelSuchen, Kunde user) {
		Datapackage kunde = sendMessage(new Datapackage("AUSWARENKORBLOESCHEN",artikelSuchen, user));
		System.out.println("[Client] Auswarenkorbl�schen" + kunde.get(1));
		return (Kunde) kunde.get(1);
	}

	public void aendereArtikel(String artikelname, int artikelnummer, int bestand, float preis, int packungsgroesse) {
		sendMessage(new Datapackage("AENDEREARTIKEL",artikelname, artikelnummer, bestand, preis, packungsgroesse));
		System.out.println("[Client] artikel�ndern:");
	}
	
	public void entferneArtikel(int artnr) {
		sendMessage(new Datapackage("ENTFERNEARTIKEL", artnr));
		System.out.println("[Client] artikeloeschen:");
	}
	
	public void schreibeKundendaten() {
		// TODO Auto-generated method stub
		sendMessage(new Datapackage("SCHREIBEKUNDENDATEN"));
	}
	
	public void schreibeMitarbeiterdaten(){
		// TODO Auto-generated method stub
		sendMessage(new Datapackage("SCHREIBEMITARBEITERDATEN"));
		System.out.println("[Client] schreibemitarbeiterdaten:");
	}
	
	public Rechnung kaufAbwickeln(Kunde user) {
		//TODO Aktaullisere f�r alle clients artikelliste
		Datapackage data = sendMessage(new Datapackage("KAUFABWICKELN", user));
		//kunden Updaten
		gui.setUser((Kunde) data.get(1));
		//warenkorb updaten
		Warenkorb wk = gui.getShop().getWarenkorb(gui.getUser());
        gui.getWarenkorbPanel().updateData(wk);
		System.out.println("[Client] KAUFABWICKELN (Kunde,Rechnung) :" +data.get(1) + data.get(2));
		return (Rechnung) data.get(2);
	}

	public List<Stats> gibAlleStats() {
		Datapackage stats = sendMessage(new Datapackage("GIBALLESTATS"));
		System.out.println("[Client] Getstats:");
		@SuppressWarnings("unchecked")
		List<Stats> list = (List<Stats>) stats.get(1);
		return list;	
	}

	public List<Stats> statsSuchen(int arklnummer) {
		Datapackage stats = sendMessage(new Datapackage("STATSSUCHEN", arklnummer));
		System.out.println("[Client] statsSuchen:");
		@SuppressWarnings("unchecked")
		List<Stats> list = (List<Stats>) stats.get(1);
		return list;	
	}

	public void fuegeKundenAccountEin(String name, String passwort, String strasse, int plz, String ort) {
		sendMessage(new Datapackage("FUEGEKUNDENACCOUNTEIN", name,passwort,strasse,plz,ort));
		System.out.println("[Client] fuegekundenaccountein:");
	}
	
	public void fuegeMitarbeiterAccountEin(String name, String passwort) {
		sendMessage(new Datapackage("FUEGEMITARBEITERACCOUNTEIN", name, passwort));
		System.out.println("[Client] fuegemitarbeiteraccountein:");
	}
	
	public Account loginAccount(String name, String passwort) {
		Datapackage kunde = sendMessage(new Datapackage("LOGINACCOUNT", name, passwort));
		System.out.println("[Client] LoginAccount:" + kunde.get(1));
		return (Account) kunde.get(1);
	}

	public HashMap<Artikel, Integer> pruefeKauf(Kunde user) {
		Datapackage artikel = sendMessage(new Datapackage("PRUEFEKAUF", user));
		System.out.println("[Client] pruefekauf: " + (HashMap) artikel.get(1));
		return (HashMap) artikel.get(1);
	}

	public Account erstelleGastAccount() {
		Datapackage kunde = sendMessage(new Datapackage("ERSTELLEGASTACCOUNT"));
		System.out.println("[Client] erstellegastaccount: " + kunde.get(1));
		return (Kunde) kunde.get(1);
	}

	public Warenkorb getWarenkorb(Account user) {
		Datapackage wk = sendMessage(new Datapackage("GETWARENKORB", user));
		System.out.println("[Client] getWarenkorb: " + wk.get(1));
		return (Warenkorb) wk.get(1);
	}

	public Kunde setWarenkorb(Account user, Warenkorb wk) {
		Datapackage kunde = sendMessage(new Datapackage("SETWARENKORB",(Kunde) user ,wk));
		System.out.println("[Client] setwarenkorb: " + kunde.get(1));
		return (Kunde) kunde.get(1);
	}
	
	//Server anweisen daten zu speichern
	public void schreibeArtikeldaten() {
		// TODO Auto-generated method stub
		sendMessage(new Datapackage("SCHREIBEARTIKELDATEN"));
	}

	public void schreibeStatsdaten() {
		// TODO Auto-generated method stub
		sendMessage(new Datapackage("SCHREIBESTATSDATEN"));
	}
}
