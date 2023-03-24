import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Conexion {

	public static void main(String[] args) {
try {
			
			contratoRMI servidor = new Servidor();
			LocateRegistry.createRegistry(2000);
			 
			String rmiObjectName = "rmi://10.152.164.146:2000/convertidor";
			Naming.rebind(rmiObjectName,servidor);
			
			System.out.println("Servidor Conectado de Convertidor");
			
		} catch (RemoteException | MalformedURLException e) {
			 
			e.printStackTrace();
		}

	}

}
