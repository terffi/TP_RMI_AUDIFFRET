package Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceChatV2Client extends Remote{
	
	public String getNom()throws RemoteException; //initialisation du client
	public void message(String msg)throws RemoteException; //affiche un message dans la console du client
	
}
