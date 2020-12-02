package Serveur;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import Interface.InterfaceChatV2Server;
import Interface.InterfaceChatV2Client;

public class Serveur extends UnicastRemoteObject implements InterfaceChatV2Server {
	private static final long serialVersionUID = 1L;
	
	ArrayList<InterfaceChatV2Client> clients=new ArrayList<>(); //liste des clients connect�s aux chat
	ArrayList<String> nomsClients=new ArrayList<>(); //liste des noms des clients connect�s (on les stock pour pouvoir savoir qui s'est d�connect� lors d'un crash cot� client)
	
	public Serveur() throws RemoteException{
		super(0);
	}

	@Override
	public void ajoutMessage(String message, InterfaceChatV2Client envoyeur) throws RemoteException {
	//envoi le message message aux autres clients connect�s au chat
		
		for(InterfaceChatV2Client client : clients) {
			try {
				if(!client.equals(envoyeur)) {
					//on n'envoi pas le message � celui qui l'a envoy�
					client.message(envoyeur.getNom()+": "+message);
				}
			}catch(RemoteException e) {
				//un client n'est plus accessible
				crash(client);
			}
		}
	}
	
	private void annonce(String message) throws RemoteException {
		//m�me principe que ajoutMessage, mais envoi � tout les clients sans exception, utilis� pour les messages d'annonce serveur
		for(InterfaceChatV2Client client : clients) {
			try {
				client.message(message);
			}catch(RemoteException e) {
				//un client n'est plus accessible
				crash(client);
			}
		}
	}

	@Override
	public void arrive(InterfaceChatV2Client client) throws RemoteException {
		//ajoute un client au chat et l'annonce
		clients.add(client);
		nomsClients.add(client.getNom());
		annonce(client.getNom()+" rejoins la partie");
	}

	@Override
	public void depart(InterfaceChatV2Client client) throws RemoteException {
		//retire un client du chat et l'annonce
		nomsClients.remove(clients.indexOf(client));
		clients.remove(client);
		annonce(client.getNom()+" est parti");
	}
	
	private void crash(InterfaceChatV2Client client) throws RemoteException {
		//si on n'arrive pas � acceder � un client, on le retire de la liste des noms des clients et de la liste des clients connect�s au chat puis on l'annonce
		String nom = nomsClients.get(clients.indexOf(client));
		clients.remove(client);
		nomsClients.remove(nom);
		annonce(nom+" n'est plus joignable");
		
	}
	
	public static void main(String[] args) throws Exception{
		try {
			LocateRegistry.createRegistry(1099);
		} catch (Exception e) {
			System.out.println("Serveur exception: "+e.toString());
			e.printStackTrace();
		}
        Serveur chatServeur = new Serveur();
        Naming.rebind("//localhost/RmiServer", chatServeur);
        System.out.println("Serveur pr�t!");
	}

}

