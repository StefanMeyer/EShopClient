package ui.client.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import ui.client.GUI_2;


public class MenuePanel implements ActionListener{

	private JMenuBar menuBar;
	private JMenuItem mnLogin;
	private JMenuItem mnReg;
	private JMenuItem mnLogout;
	private SuchPanel suchPanel;
	private GUI_2 gui;
	
	//Konstruktor
	public MenuePanel(GUI_2 gui) {
		this.gui = gui;
		
		JMenuBar menueBar = new JMenuBar();		
		
		JMenu mnDatei = new JMenu("Datei");
		menueBar.add(mnDatei);

		JMenuItem mntmBeenden = new JMenuItem("Beenden");
		mntmBeenden.addActionListener(this);
		mnDatei.add(mntmBeenden);
		
		JMenu mnAccount = new JMenu("Account");
		menueBar.add(mnAccount);
		
		mnLogin = new JMenuItem("Einloggen");
		mnAccount.add(mnLogin);
		LoginPanel loginPanel = new LoginPanel(gui);
		mnLogin.addActionListener(loginPanel);
		
		mnReg = new JMenuItem("Registrieren");
		mnReg.addActionListener(loginPanel);
		mnAccount.add(mnReg);
		
		mnLogout = new JMenuItem("Ausloggen");
		mnAccount.add(mnLogout);
		mnLogout.addActionListener(this);

		JMenu mnHilfe = new JMenu("Hilfe");
		menueBar.add(mnHilfe);

		JMenuItem mnWieArtikelKaufen = new JMenuItem("Wie Artikel kaufen?");
		mnHilfe.add(mnWieArtikelKaufen);
		mnWieArtikelKaufen.addActionListener(this);
		
		JMenuItem mnUberUns = new JMenuItem("\u00DCber uns");
		mnHilfe.add(mnUberUns);
		mnUberUns.addActionListener(this);
		
		setUserLoggedIn(false);
		setMenue(menueBar);
	}
	
	//Getter und Setter	
	public JMenuBar getMenue() {
		return this.menuBar;
	}
	
	public void setMenue(JMenuBar menue) {
		this.menuBar = menue;
	}
	
	public SuchPanel getSuchPanel() {
		return suchPanel;
	}

	public void setSuchPanel(SuchPanel suchPanel) {
		this.suchPanel = suchPanel;
	}
	
	//ACTIONLISTENER
	public void actionPerformed(ActionEvent arg0){
		String command = arg0.getActionCommand();
		
		//Fuer Menue Datei -> Beenden Button
		if (command.equals("Beenden")){
			System.exit(0);
		}
		//Fuer Menue Datei -> Ausloggen Button
		else if(command.equals("Ausloggen")){
			setUserLoggedIn(false);
			//user = shop.logoutAccount(user.getName(), user.getPasswort());
			gui.userLoggedOut();
			System.out.println("Tschuess!");		
			gui.getObenPanel().setVisible(true);
		}
		//Fuer Menue Hilfe -> Artikel kaufen? Button
		else if (command.equals("Wie Artikel kaufen?")){
			JOptionPane.showMessageDialog(null,
				"Willkommen im E-Shop. \n Wenn Sie Artikel kaufen wollen, dann registrieren"
				+ "Sie sich und loggen Sie sich ein! \n Anschliessend koennen Sie die gewuenschten "
			    + "Artikel kaufen.");
		}
		//Fuer Menue Hilfe -> Ueber uns Button
		else if (command.equals("\u00DCber uns")){
			JOptionPane.showMessageDialog(null, "Entwickler: \n"
					+ "Immanuel Zimmermann \n" 
					+ "Stefan Mayer \n"
					+ "Daniel Böckmann \n\n" 
					+ "HS Bremen, Prog 2, SS 2016");
		}
		
		System.out.println("menuepanel Aktion ausgefuehrt");
	}
/**  Aktiviert oder deaktiviert die ein/auslogfunktion 
 *
 *   @param b true blendet loginfunktionen/anmeldefunktionen aus 
 * 			false blendet Ausslogen aus
 */
	public void setUserLoggedIn(boolean b){
		mnLogout.setEnabled(b);
		mnReg.setEnabled(!b);
		mnLogin.setEnabled(!b);
	}
}
