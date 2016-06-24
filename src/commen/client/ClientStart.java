package commen.client;
import ui.client.GUI_2;

public class ClientStart {
	public ClientStart() {
    	Clientverwaltung client = new Clientverwaltung("localhost",8788, 10*1000 /*10 Sek timeout*/,false);
    	
    	new GUI_2(client);  		
	}
}
