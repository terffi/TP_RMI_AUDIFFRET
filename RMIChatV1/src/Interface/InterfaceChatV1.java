package Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceChatV1 extends Remote{
	
	public void ajoutMessage(String message, int id) throws RemoteException; //méthode qui ajoute un message à la liste des messages et ajoute l'id du client ayant envoyé ce message dans la liste idMessages
	public String message(int i) throws RemoteException; //renvoi le message de la liste message à la position i
	public int idMessage(int i) throws RemoteException; //renvoi l'id de celui qui à envoyé le message de la liste messages à la positions i (renvoi l'id de la liste idMessages à la position i)
	public int nbMessages() throws RemoteException; //renvoi le nombre de messages de la liste messages
	public int arrive(String nom) throws RemoteException; //ajout d'un client au chat
	public void depart(int i) throws RemoteException; //prévient qu'un client s'est déconnecté du chat

}
