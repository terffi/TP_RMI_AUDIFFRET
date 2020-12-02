package Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceChatV2Server extends Remote{
	
	public void ajoutMessage(String message, InterfaceChatV2Client envoyeur) throws RemoteException; //envoi le message message aux autres clients connectés au chat
	public void arrive(InterfaceChatV2Client client) throws RemoteException; //ajoute un client au chat et l'annonce
	public void depart(InterfaceChatV2Client client) throws RemoteException; //retire un client du chat et l'annonce

}
