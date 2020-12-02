package Client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import Interface.InterfaceChatV1;


public class Client {
	InterfaceChatV1 Serveur;
	
	public Client() throws MalformedURLException, RemoteException, NotBoundException {
		Serveur = (InterfaceChatV1)Naming.lookup("//localhost/RmiServer");
	}
	
	public static void main(String args[]) throws Exception {
		Scanner sc = new Scanner(System.in);
		
		//définition du nom du client
		System.out.println("Entrez votre nom d'utilisateur : ");
		String nom = sc.nextLine();
		
        Client chatClient=new Client();  
        int id = chatClient.Serveur.arrive(nom); //connexion au chat et récupération de l'id du client
        
        PollThread pollthread = new PollThread(chatClient,id); //initialisation du thread de polling
        pollthread.start(); //lancement du thread de polling
        
        String message = sc.nextLine();
        while(!message.equals("quit")) {
        	//envoi de messages au serveur
        	chatClient.Serveur.ajoutMessage(message,id);
        	
        	message=sc.nextLine();
        }
        
        chatClient.Serveur.depart(id); //prévient le serveur de la déconnection
        pollthread.stop(); //arrêt du thread
        sc.close();
    }
}
