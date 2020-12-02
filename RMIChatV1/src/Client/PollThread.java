package Client;

import java.rmi.RemoteException;

public class PollThread extends Thread{
	Client chatClient;
	int nbMessages;
	int id;
	
	public PollThread(Client chatClient, int id) {
		//initialisation du thread
		this.chatClient=chatClient;
		this.id=id;
		try {
			this.nbMessages=chatClient.Serveur.nbMessages(); //récupération du nombre de messages déjà stocké sur le serveur
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while(true) {
			try {
			if(chatClient.Serveur.nbMessages()>nbMessages) {
				//on vérifie s'il y a des nouveaux messages sur le serveur
				for(int i=nbMessages;i<chatClient.Serveur.nbMessages();i++) {
					//on affiche chaque message non lu en vérifiant que l'id de celui qui a envoyé le message ne correspond pas à l'id du client
					if(chatClient.Serveur.idMessage(i)!=id) {
					System.out.println(chatClient.Serveur.message(i));
					}
				}
				nbMessages=chatClient.Serveur.nbMessages(); //mise à jour du nombre de messages lus
			}			
			
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			
		}
	}
}
