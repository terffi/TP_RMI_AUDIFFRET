package Serveur;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import Interface.InterfaceChatV1;

public class Serveur extends UnicastRemoteObject implements InterfaceChatV1 {
	private static final long serialVersionUID = 1L;
	
	private ArrayList<String> messages = new ArrayList<String>(); //liste des messages
	private ArrayList<Integer> idMessages = new ArrayList<Integer>(); //liste des identifiants des envoyeurs pour chaque messages de la liste messages
	private ArrayList<String> users = new ArrayList<String>(); //liste des noms des clients qui se sont connecté au chat (le nom ne part pas de la liste si le client se déconnecte), la position d'un client dans cette liste correspond à son id
	private static int nbUsers=0; //compteur de clients s'étant connecté au chat
	
	public Serveur() throws RemoteException{
		super(0);
	}

	@Override
	public void ajoutMessage(String message, int id) throws RemoteException {
		//méthode qui ajoute un message à la liste des messages et ajoute l'id du client ayant envoyé ce message dans la liste idMessages
		
		idMessages.add(id);
		messages.add(users.get(id)+": "+message);
	}
	
	@Override
	public String message(int i) throws RemoteException {
		//renvoi le message de la liste message à la position i
		return messages.get(i);
	}
	public int idMessage(int i) throws RemoteException{
		//renvoi l'id de celui qui à envoyé le message de la liste messages à la positions i (renvoi l'id de la liste idMessages à la position i)
		return idMessages.get(i);
	}

	@Override
	public int nbMessages() throws RemoteException {
		//renvoi le nombre de messages de la liste messages
		return messages.size();
	}

	@Override
	public int arrive(String nom) throws RemoteException {
		//ajout d'un client au chat
		
		users.add(nom); // ajout du nom du client à la liste des clients qui se sont connecté
		
		//ajout d'un message serveur pour annoncer la nouvelle connexion (-1 correspond à l'id du server)
		idMessages.add(-1);
		messages.add(nom+" rejoins la partie");
		
		return nbUsers++; //renvoi l'id du client (qui correspond à la place de son nom dans la liste users)
	}

	@Override
	public void depart(int i) throws RemoteException {
		//prévient qu'un client s'est déconnecté du chat
		
		idMessages.add(-1);
		messages.add(users.get(i)+" est parti");
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
        System.out.println("Serveur prêt!");
	}

}

