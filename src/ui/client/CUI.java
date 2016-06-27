package ui.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import commen.client.Clientverwaltung;
import valueobjects.Account;
import valueobjects.Artikel;
import valueobjects.Kunde;
import valueobjects.Mitarbeiter;
import valueobjects.Rechnung;
import valueobjects.Stats;
import valueobjects.Warenkorb;

public class CUI {

	// Instanzierung der benoetigten Objekte
	private Clientverwaltung shop;
	private BufferedReader reader;
	private Account user;
	private boolean massengut = false;

	public CUI(String datei) throws IOException {
		
		// Die Shop-Verwaltung erledigt, die Aufgaben,
		// die nichts mit Ein-/Ausgabe zu tun haben
		shop = new Clientverwaltung("Localhost",8788, 10*1000 /*10 Sek timeout*/,false);
		
		// Stream-Objekt fuer Texteingabe ueber Konsolenfenster erzeugen
		// Der Buffered Reader wird benutzt um die Eingabe einer einzelnen Zeile
		// zu bekommen.
		reader = new BufferedReader(new InputStreamReader(System.in));
	}
	
	// Startmenue: interne (private) Methode zur Ausgabe des Menues
	private void menueStart() throws IOException {

		System.out.println("\n[Startmenue] \n");
		System.out.println("Los geht's! \n");
		System.out.println("Account anlegen: n");
		System.out.println("Login: l");
		System.out.println("Beenden: q \n\n");		
		System.out.println("Eingabe: ");				
		System.out.flush();
	}

	// Startmenue: interne (private) Methode zur Ausgabe des Menues fuer den Kunden
	private void menueKunde() {

		System.out.println("\n[Kundenbereich] \n");
		System.out.println("[some bugs inside]Artikel im Warenkorb ablegen: k");
		System.out.println("Warenkorb einsehen: w");
		System.out.println("[nicht vollstaendig impelemtiert(16.4)] Warenkorb - Bestellung abschliessen: b \n");
		System.out.println("Artikel ausgeben: a");
		System.out.println("Artikel ordnen: o");
		System.out.println("Artikel suchen: f");
		System.out.println("\n Ausloggen: al\n\n");
		System.out.println("Eingabe: ");
		System.out.flush();
	}
	// Startmenue: interne (private) Methode zur Ausgabe des Menues fuer den Mitarbeiter
	
	private void menueMitarbeiter() {
		System.out.println("\n[Mitarbeiterbereich]\n");
		System.out.println("Artikel einfuegen: e");
		System.out.println("Artikel entfernen: d");
		System.out.println("Artikel ausgeben: a");
		System.out.println("Artikel ordnen: o");
		System.out.println("Artikelmenge ändern: z \n");
		System.out.println("Ausloggen: al \n\n");
		System.out.println("Ihre Eingabe: ");
		System.out.flush();
	}
	// Interne, private Methode zur Verarbeitung der Eingaben und Ausgaben der
	// Ergebnisse
	private String liesEingabe() throws IOException {
		return reader.readLine();
	}
	/**
	 * Methode zum Verarbeiten der Eingabe im Menue
	 * 
	 */
	
	private void verarbeiteEingabe(String line) throws IOException {
		
		/**
		 * Befehl e: Artikel einfuegen, nur als Mitarbeiter
		 */
		
		if (line.equals("e")) {

			if (user instanceof Mitarbeiter) {

				// lies die notwendigen Parameter einzeln pro Zeile
				System.out.print("Artikelnummer > ");
				String nummer = liesEingabe();
				int artnr = Integer.parseInt(nummer);
				System.out.print("Artikelname  > ");
				String artname = liesEingabe();
				System.out.println("Massengutartikel? j/n");
				String massengut1 = liesEingabe();
				int packung = 0;
				if (massengut1.equals("j")) {
					System.out.println("Packungsgroesse > ");
					String packungsgroesse = liesEingabe();
					packung = Integer.parseInt(packungsgroesse);
					massengut = true;
				} else if (line.equals("n")) {
					massengut = false;
				} else {
					massengut = false;
				}
				int artbestand;

				System.out.println("Bestand  > ");
				String bestand = liesEingabe();
				artbestand = Integer.parseInt(bestand);

				System.out.println("Einzelpreis > ");
				String preis = liesEingabe();
				preis = preis.replace(',','.');
				float artpreis = Float.parseFloat(preis);
				boolean ok;
				
				//Massengut
				try {
					if (!massengut) {
						ok = shop.fuegeArtikelEin(artname, artnr, artbestand,
								artpreis, packung);
						shop.schreibeArtikeldaten();
						if (ok)
							System.out
									.println("Einfuegen ok, Artikel wurde angelegt.");
						else
							throw new Exception();
					} else if (massengut) {
						ok = shop.fuegeMassengutEin(artname, artnr, artbestand, artpreis, packung);
						shop.schreibeArtikeldaten();
						if (ok)
							System.out
									.println("Einfuegen ok, Artikel wurde angelegt.");
						else
							throw new ArtikelExistiertBereitsException();
					}
				} catch (ArtikelExistiertBereitsException e) {

				}
				
				//Ende Massengut
			// wenn als Kunde eingeloggt 
			} else
				System.out.println("Das darfst du nicht!");
		}

		/**
		 * Befehl a: Artikelliste wird ausgegeben
		 */

		else if (line.equals("a")) {

			if (user != null) {
				List<Artikel> artikelListe = shop.gibAlleArtikel();
				gibArtikellisteAus(artikelListe);
			} else
				System.out.println("Bitte loggen Sie ein!");
		}
		else if (line.equals("adda")) {
			Random zahl = new Random();
			int b;
			int start = zahl.nextInt(10000000);
			for(int i=start;i<(start + 30);i+=2) {
				try {
					b = (int) zahl.nextFloat()*100;
					shop.fuegeArtikelEin("Artikel_"+i, i, zahl.nextInt(100), (float)(b/100.0), 0);
				} catch (ArtikelExistiertBereitsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					b = (int) zahl.nextFloat()*101;
					shop.fuegeMassengutEin("MassengutArtikel_"+(i+1), i+1, zahl.nextInt(101), (float)(b/100.0), zahl.nextInt(5));
				} catch (ArtikelExistiertBereitsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
			shop.schreibeArtikeldaten();			
		}
		
		/**
		 * Befehl sd - Statistik debug funktionen 
		 */
		
		else if (line.equals("sd")) {		
		}
		else if (line.equals("sl")) {
			List <Stats> alleStats = shop.gibAlleStats();
			gibStatsAus(alleStats);
		}
		else if (line.equals("sa")) {
			List <Stats> statszuartikel = shop.statsSuchen(8953);
			gibStatsAus(statszuartikel);				
		}
		else if (line.equals("sb")) {	
		}
		
		/**
		 * Befehl d: Artikel wird entfernt, nur als Mitarbeiter	 
		 */

		else if (line.equals("d")) {

			if (user instanceof Mitarbeiter) {
				List<Artikel> artikelListe = shop.gibAlleArtikel();
				gibArtikellisteAus(artikelListe);
				System.out.println("Artikelnummer zum entfernen des Produktes:");
				System.out.println("Eingabe >");
				String nummer = liesEingabe();
				int artnr = Integer.parseInt(nummer);

				boolean ok = shop.entferneArtikel(artnr);
				shop.schreibeArtikeldaten();

				if (ok) {
					System.out.println("Artikel wurde gelÃ¶scht.");
				} else
					System.out.println("Fehler beim Entfernen.");
				
			// wenn als Kunde eingeloggt	
			} else
				System.out.println("Darfst du nicht!");
		}

		/**
		 * Befehl o: Artikelliste sortieren nach Name oder Nummer 
		 */

		else if (line.equals("o")) {

			if (user != null) {
				System.out.println("Sortieren nach Name (x) oder Nummer (y)");
				System.out.println("Eingabe >");
				String name = liesEingabe();

				List<Artikel> artikelListe;
				// TODO: Die Sortierung sollte eigentlich nicht in der CUI stehen

				if (name.equals("x")) {

					// Nach Name sortieren
					artikelListe = shop.getSortierteArtikelnamen();

					// Artikel ausgeben
					gibArtikellisteAus(artikelListe);

				} else if (name.equals("y")) {

					// Nach Nummer sortieren
					artikelListe = shop.getSortierteArtikelnummern();

					// Artikel ausgeben
					gibArtikellisteAus(artikelListe);

				} else {

					System.out.println("Ungueltige Eingabe!");
					System.out.println("Zum Sortieren nur Befehl x oder y moeglich.");
					System.out.println("Sortiervorgang wiederholen");
				}
			} else
				System.out.println("Bitte einloggen");
		}

		/**
		 * Befehl f: Artikel finden -> nach Artikelname
		 */
		
		//Artikel suchen
		else if (line.equals("f")) {
			System.out.print("Artikelname > ");
			String artname = liesEingabe();
			List<Artikel> liste = shop.sucheNachArtikel(artname);
			System.out.print(liste);
		}

		/**
		 * Befehl n: Neuen Account anlegen
		 */

		else if (line.equals("n")) {
			System.out.println("Account anlegen.");
			System.out.println("Name eingeben");
			String name = liesEingabe();
			System.out.println("Passwort eingeben");
			String passwort = liesEingabe();
			System.out.println("Kunde oder Mitarbeiter? Kunde = k / Mitarbeiter = m");
			String account = liesEingabe();

			// Auswahldialog mit erweiterter Eingabe
			// Kunde k

			if (account.equals("k")) {
				System.out.println("Strasse: ");
				String strasse = liesEingabe();

				int plz = 0;
				do {
					System.out.println("Postleitzahl: ");
					String plz1 = liesEingabe();
					plz = Integer.parseInt(plz1);

					if (plz > 99999 || plz < 10000) {
						System.out.println("Ungueltige Postleitzahl! Bitte fünfstellig.");
					}
				} while (plz == 0 || plz > 99999 || plz < 10000);

				System.out.println("Wohnort: ");
				String ort = liesEingabe();
				// boolean ok =
				try {
					shop.fuegeKundenAccountEin(name, passwort, strasse, plz,
							ort);
					shop.schreibeKundendaten();
				} catch (AccountExistiertBereitsException e1) {
					System.out.println(e1.getMessage());
					System.out.println("Registrierungsvorgang wiederholen");
				}
			// Mitarbeiter m 
			} else if (account.equals("m")) {
				boolean ok;
				try {
					ok = shop.fuegeMitarbeiterAccountEin(name, passwort);

					if (ok) {
						try {
							int accnummer = shop.loginAccount(name, passwort)
									.getAccountNr();
							shop.schreibeMitarbeiterdaten();
							System.out
									.println("Mitarbeiter-Account wurde angelegt. Accountnummer: "
											+ accnummer + ".");
							menueStart();
						} catch (AccountExistiertNichtException e) {
							e.printStackTrace();
						}

					} else {
						System.out.println("Fehler beim Einfuegen.");
					}

				} catch (AccountExistiertBereitsException e1) {
					e1.printStackTrace();
				}
			}

		}

		/**
		 * Befehl l: einloggen
		 */

		else if (line.equals("l")) {

			if (user == null) {

				System.out.println("Login:");
				System.out.println("Namen eingeben:");
				String name = liesEingabe();
				System.out.println("Passwort eingeben:");
				String passwort = liesEingabe();
	
				try {
					user = shop.loginAccount(name, passwort);
					System.out.println("Einloggen erfolgreich.");
					System.out.println("Hallo " + user.getName());

					do {
						// Accountunterscheidung Mitarbeiter bzw Kunde
						if (user instanceof Mitarbeiter)
							menueMitarbeiter();
						else if (user instanceof Kunde)

							menueKunde();

						try {
							line = liesEingabe();
							verarbeiteEingabe(line);
						} catch (IOException e) {
							e.printStackTrace();
						}

					}while (!line.equals("q"));

				} catch (AccountExistiertNichtException e) {
					System.out.println("Der eingegebene Name ("+name+") und das Passwort stimmen nicht überein!");
				}
			} else {
				System.out.println("Sie sind bereits eingeloggt!");
			}
		}

		/**
		 * Befehl k: Artikel kaufen, nur als Kunde
		 */
		
		else if (line.equals("k")) {
			
			if (user instanceof Kunde) {
				List<Artikel> artikelListe = shop.gibAlleArtikel();
				gibArtikellisteAus(artikelListe);
				
				System.out.println("Welchen Artikel? Artikelnummer eingeben.");
				
				try {	
					String kaufen = liesEingabe();
					int einkaufen = Integer.parseInt(kaufen);
					Artikel a = shop.artikelSuchen(einkaufen);
					
					System.out.println("Wie oft wollen Sie den Artikel kaufen?");
					
					String anzahl = liesEingabe();	
					int anzahl1 = Integer.parseInt(anzahl);
					
					// Artikel in den Warenkorb einfuegen
					shop.inWarenkorbEinfuegen(a ,anzahl1, (Kunde) user);
					System.out.println("Artikel in den Warenkorb gelegt.");
				} catch (BestandUeberschrittenException e) {
					// Text siehe Exception

				} catch (ArtikelExistiertNichtException e) {
					System.out.println(e.getMessage());
				}
				
			// wenn als Mitarbeiter eingeloggt
			} else
				System.out.println("Bitte loggen Sie sich fuer diesen Vorgang als Kunde ein!");
		}
		
		/**
		 * Befehl w: Warenkorb anzeigen, nur als Kunde
		 */

		else if (line.equals("w")) {

			if (user instanceof Kunde) {

				Kunde kunde = (Kunde) user;

				// Warenkorb warenkorb = shop.getWarenkorbByKunde((Kunde) user);
				Warenkorb warenkorb = kunde.getWarenkorb();
				
				if(warenkorb.getInhalt().isEmpty()){
					System.out.println("Warenkorbe ist leer!");
				}
				else{
					System.out.println("Der Inhalt des Warenkorbes:");
					
					Set<Artikel> articles = warenkorb.getInhalt().keySet();
				
					for (Artikel artikel : articles) {
						int anzahl = (Integer) warenkorb.getInhalt().get(artikel);
	
						float artikelGesamtpreis = (anzahl * artikel.getPreis());
						System.out.println(artikel.getName() + " - Anzahl: "
								+ anzahl + " - Einzelpreis: " + artikel.getPreis()
								+ " - Artikel-Gesamtpreis: " + artikelGesamtpreis);
					}
				}
			// wenn als Mitarbeiter eingeloggt	
			} else
				System.out.println("Bitte loggen Sie sich fuer diesen Vorgang als Kunde ein!");
		}

		/**
		 * Befehl z: Bestand aendern, nur als Mitarbeiter 
		 */
		
		else if (line.equals("z")) {

			if (user instanceof Mitarbeiter) {
				List<Artikel> artikelListe = shop.gibAlleArtikel();
				gibArtikellisteAus(artikelListe);
				System.out.println("Waehlen Sie einen Artikel aus, dessen Bestand Sie veraendern wollen, indem Sie die Artikelnummer angeben.");
				String bestandAnders = liesEingabe();
				int bestandAendern = Integer.parseInt(bestandAnders);

				System.out.println("Neuer Bestand: ");

				String newBestand = liesEingabe();
				int newBestand1 = Integer.parseInt(newBestand);

				if (newBestand1 <= 0) {
					shop.entferneArtikel(bestandAendern);
					System.out.println("Artikel geloescht.");
					shop.schreibeArtikeldaten();
				} else {
					try {
						shop.aendereBestand(bestandAendern, newBestand1);
						System.out.println("Bestand geaendert.");
						shop.schreibeArtikeldaten();
						shop.schreibeStatsdaten();
					} catch (ArtikelExistiertNichtException e) {
						System.out.println(e.getMessage());
					}
				}
				
			// wenn als Kunde eingeloggt
			} else
				System.out.println("Dazu sind Sie nicht berechtigt!");
		}

		/**
		 * Befehl al: User ausloggen
		 */
		
		else if (line.equals("al")) {
			user = shop.logoutAccount(user.getName(), user.getPasswort());
			menueStart();
		}

		/**
		 * Befehl b: Bestellung abschliessen, Kauf abewickeln nur als Kunde
		 */
		
		else if (line.equals("b")) {

			if (user instanceof Kunde) {
				HashMap<Artikel, Integer> warenkorbBestand = shop
						.gibAlleArtikelAusWarenkorb((Kunde) user);
				gibWarenbestandAus(warenkorbBestand);

				if (warenkorbBestand.isEmpty()) {

				} else {
					System.out
							.println("Wollen Sie Ihre Warenkorbartikel jetzt kaufen? j/n?");
					String kaufenJaNein = liesEingabe();

					if (kaufenJaNein.equals("j")) {
						HashMap<Artikel, Integer> fehlerliste = shop
								.pruefeKauf((Kunde) user);
						if (!fehlerliste.isEmpty()) {
							System.out
									.println("Folgende Artikel konnten nicht zum Kauf angeboten werden:");
							Set<Artikel> articles = fehlerliste.keySet();
							for (Artikel artikel : articles) {
								System.out.println(artikel);
							}
						}

						// cast Argument (Kunde) user
						Rechnung rechnung = shop.kaufAbwickeln((Kunde) user);

						System.out.println("R E C H N U N G");
						System.out.println("");
						System.out.println("Datum: " + rechnung.getDatum());
						System.out.println("Gekauft von: " + user.getName());
						System.out.println(((Kunde) user).getStrasse());
						System.out.println(((Kunde) user).getPlz() + " " + ((Kunde) user).getWohnort());
						System.out.println("");
						System.out.println("Der Gesamtpreis betraegt:" + rechnung.getGesamtpreis());

					} else if (kaufenJaNein.equals("n")) {
						menueKunde();
					} else {
						System.out.println("Bitte geben Sie nur j (Ja) oder n (Nein) ein.");
					}
				}
				
			// wenn als Mitarbeiter eingeloggt
			} else
				System.out.println("Bitte loggen Sie sich fuer diesen Vorgang als Kunde ein!");
		} else {
			if (!line.equals("q"))
				System.out.println("Bitte geben Sie einen der aufgefuehrten Befehle ein.");
		}
	}

	// Ausgabe des Warenkorbbestandes
	private void gibWarenbestandAus(HashMap<Artikel, Integer> warenkorbBestand) {
		if (warenkorbBestand.isEmpty()) {
			System.out.println("Liste ist leer.");
		} else {
			Iterator<Artikel> iter = warenkorbBestand.keySet().iterator();
			while (iter.hasNext()) {
				Artikel artikel = iter.next();
				System.out.println(artikel.toString());
			}
		}
	}

	private void gibArtikellisteAus(List<Artikel> artikel2) {
		if (artikel2.isEmpty()) {
			System.out.println("Liste ist leer.");
		} else {
			Iterator<Artikel> iter = artikel2.iterator();
			while (iter.hasNext()) {
				Artikel artikel = iter.next();
				System.out.println(artikel.toString());
			}
		}
	}
	private void gibStatsAus(List<Stats> statslist) {
		if (statslist.isEmpty()) {
			System.out.println("Keine Statistik zum Artikel");
		} else {
			//TODO sotieren
	
			//TODO tabelle erzeugen
			Iterator<Stats> iter = statslist.iterator();
			while (iter.hasNext()) {
				Stats statslist2 = iter.next();
				System.out.println(statslist2.toString());
			}
		}
	}
	public void run() throws IOException, ArtikelExistiertNichtException {
		// Variable fuer Eingaben von der Konsole
		String input = "";

		// Hauptschleife der Benutzungsschnittstelle
		do {
			if (user == null) {
				menueStart();
			}
			try {
				input = liesEingabe();
				verarbeiteEingabe(input);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} while (!input.equals("q"));
	}

	//MAIN
	public static void main(String[] args) throws StatExistiertBereitsException, ArtikelExistiertNichtException {

		CUI cui;
		try {
			cui = new CUI("Shop");
			cui.run();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}