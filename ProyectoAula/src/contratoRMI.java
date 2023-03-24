 


import java.rmi.Remote;
import java.rmi.RemoteException;
 

public interface contratoRMI extends Remote {
	
	public String conversion(Sublote objeto) throws RemoteException;;
	
	

}