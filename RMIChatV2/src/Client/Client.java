package Client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import Interface.InterfaceChatV2Server;
import Interface.InterfaceChatV2Client;


public class Client extends UnicastRemoteObject implements InterfaceChatV2Client{
	private static final long serialVersionUID = 1L;
	
	static Scanner sc;	
	private static String nom;
	
	
	public Client() throws MalformedURLException, RemoteException, NotBoundException {
		//initialisation du client
		System.out.println("Entrez votre nom d'utilisateur : ");
		nom = sc.nextLine();
	}
	

	@Override
	public String getNom() throws RemoteException {
		//retourne le nom du client
		return nom;
	}

	@Override
	public void message(String msg) throws RemoteException {
		//affiche un message dans la console du client
		System.out.println(msg);
	}
	
	public static void main(String args[]) throws Exception {
		sc = new Scanner(System.in);
		Client chatClient=new Client(); 
		
		InterfaceChatV2Server Serveur=(InterfaceChatV2Server)Naming.lookup("//localhost/RmiServer"); //connexion au serveur
         
        Serveur.arrive(chatClient); //connexion au chat
        
        String message = sc.nextLine();
        while(!message.equals("quit")) {
        	//envoi de messages au serveur
        	
        	Serveur.ajoutMessage(message,chatClient);
        	
        	message=sc.nextLine();
        }
        
        Serveur.depart(chatClient); //déconnection du chat
        sc.close();
        System.exit(0); //fermeture du client
    }

}
