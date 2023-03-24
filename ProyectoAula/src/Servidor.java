import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.io.*;
import java.util.Base64;
 

import com.aspose.words.*;

public class Servidor extends UnicastRemoteObject implements contratoRMI {

	protected Servidor() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String conversion(Sublote objeto) throws RemoteException {

		// TODO Auto-generated method stub
		return null;
	}

	public String convertirArchivo(Archivo obj) throws IOException {
		
		System.out.println("OBTENCION DE DOCUMENTO: " + obj.nombre);
		String archivo[] =obj.nombre.split("\\.");
		String extension = "."+archivo[archivo.length-1];
		
		String nombre = "";
		for (int i = 0; i < archivo.length-1; i++) {
			nombre = nombre + archivo[i];
		}
		
		System.out.println(nombre);
		String base64 = obj.getBase64();
		

		
		

		// Decodificar archivo Word en Base64
		byte[] decoded = Base64.getDecoder().decode(base64);
		
		/*
		 * LOS ARCHIVOS SE GUARDAN EN C:\Users\USUARIO\AppData\Local\Temp\ Varia de
		 * acuerdo al computador C:\Users\admin\AppData\Local\Temp\
		 */
		
		


		File tempFile = File.createTempFile("document",  extension);
		FileOutputStream fos = new FileOutputStream(tempFile);
		fos.write(decoded);
		fos.close();
		
		
		 
		System.out.println("Termino " + tempFile);
		System.out.println(String.valueOf(tempFile));
		System.out.println("C:\\Users\\admin\\Pictures\\DOCUMENTOS_CONVERTIDOS\\"+nombre+".pdf");
		
		
		//CONVERSION EN PDF 
		
		
	 
		try {
			Document doc = new Document(String.valueOf(tempFile));
			System.out.println(doc);
			 
			//doc.save("C:\\Users\\admin\\Pictures\\DOCUMENTOS_CONVERTIDOS\\"+nombre+".pdf");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 

		return null;

	}

	public static void main(String[] args) throws IOException {
		// Obtener archivo Word en formato Base64
		FileInputStream fis = new FileInputStream(new File("E:\\Taller5 sistemas de archivos linux.docx"));
		byte[] bytes = new byte[(int) fis.available()];
		fis.read(bytes);
		String base64String = new String(Base64.getEncoder().encode(bytes));

		
		Servidor server = new Servidor();
		Archivo nuevo = new Archivo("12354",base64String,"Taller5 sistemas de archivos linux.docx");
		
		System.out.println(nuevo.toString());
		
		server.convertirArchivo(nuevo);
		
		
	}

}
