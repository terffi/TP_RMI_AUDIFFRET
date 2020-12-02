package Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceChatV1 extends Remote{
	
	public void ajoutMessage(String message, int id) throws RemoteException; //m�thode qui ajoute un message � la liste des messages et ajoute l'id du client ayant envoy� ce message dans la liste idMessages
	public String message(int i) throws RemoteException; //renvoi le message de la liste message � la position i
	public int idMessage(int i) throws RemoteException; //renvoi l'id de celui qui � envoy� le message de la liste messages � la positions i (renvoi l'id de la liste idMessages � la position i)
	public int nbMessages() throws RemoteException; //renvoi le nombre de messages de la liste messages
	public int arrive(String nom) throws RemoteException; //ajout d'un client au chat
	public void depart(int i) throws RemoteException; //pr�vient qu'un client s'est d�connect� du chat

}
