import java.io.File;
import java.io.FileInputStream;
import java.rmi.Naming;
import java.util.Base64;

public class Cliente {
	public static void main(String[] args) {

		try {
			
			/*

			// Obtener archivo Word en formato Base64
			FileInputStream fis = new FileInputStream(new File("G:\\PRUEBA\\EJERCICIO APLICACIÓN CONTABLE - ENERO 2022.pptx"));
			byte[] bytes = new byte[(int) fis.available()];
			fis.read(bytes);
			String base64String = new String(Base64.getEncoder().encode(bytes));
			
			// Obtener archivo Word en formato Base64
			FileInputStream fis1 = new FileInputStream(new File("G:\\PRUEBA\\DiagramaCasoUso.docx"));
			byte[] bytes1 = new byte[(int) fis1.available()];
			fis1.read(bytes1);
			String base64String1 = new String(Base64.getEncoder().encode(bytes1));

			// Obtener archivo Word en formato Base64
			FileInputStream fis2 = new FileInputStream(new File("G:\\PRUEBA\\ContabilidadAnalisis_ALIVAL.xlsx"));
			byte[] bytes2 = new byte[(int) fis2.available()];
			fis2.read(bytes2);
			String base64String2 = new String(Base64.getEncoder().encode(bytes2));
			
			 

			Archivo cliente = new Archivo("1", base64String, "EJERCICIO APLICACIÓN CONTABLE - ENERO 2022.pptx");
			Archivo cliente2 = new Archivo("2", base64String1, "DiagramaCasoUso.docx");
			Archivo cliente3 = new Archivo("3", base64String2, "ContabilidadAnalisis_ALIVAL.xlsx");
			*/
			
			
			Archivo cliente4 = new Archivo("1","https://jarroba.com/multitarea-e-hilos-en-java-con-ejemplos-thread-runnable/", 1);
			Archivo cliente5 = new Archivo("2", "https://www.autodraw.com/", 2);
			Archivo cliente6 = new Archivo("3", "http://www.homestyler.com/", 3);
			
			Archivo[] l = new Archivo[3];
			
			l[0] = cliente4;
			l[1] = cliente5;
			l[2] = cliente6;
 			
			
			Sublote completo = new Sublote("12324","1232415","12324",l);

			contratoRMI server = (contratoRMI) Naming.lookup("rmi://192.168.20.20:1902/convertidor");
			System.out.print("Usuario conectado");
			
			//Sublote nuevo = server.conversionOffice(completo);
			
			Sublote nuevo =server.conversionURL(completo);
			
			Archivo[] recibir = nuevo.archivos;
			
			for (int i = 0; i<recibir.length;i++) {
				System.out.println(recibir[i].idArchivo);
				System.out.println(recibir[i].base64);
			}

		} catch (Exception e) {
			System.out.println("Error al conectar con el servidor: " + e);
		}

	}
}
