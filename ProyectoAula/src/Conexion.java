
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Conexion {

	public static void main(String[] args) {
		try {

			contratoRMI servidor = new Servidor();
			LocateRegistry.createRegistry(1902);

			String rmiObjectName = "rmi://192.168.20.20:1902/convertidor";
			Naming.rebind(rmiObjectName, servidor);

			System.out.println("Servidor Conectado de Convertidor");

		} catch (RemoteException | MalformedURLException e) {

			e.printStackTrace();
		}

	}

}