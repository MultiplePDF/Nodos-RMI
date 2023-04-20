import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.concurrent.Callable;

public class ConvertirURL implements Callable<Archivo> {

	Archivo obj;

	public ConvertirURL(Archivo obj) {
		super();
		this.obj = obj;

	}

	@Override
	public Archivo call() throws Exception {

		Archivo nuevo = convertirURL(obj);
		return nuevo;
	}

	
	public Archivo convertirURL(Archivo obj) throws IOException {

		String url = obj.url;
		String titulo = "";
		String tit = url.replaceAll("[^a-zA-Z]", "");
		 
		
		if(tit.length()>=10) {
			titulo = tit.substring(0, 20);
			
		}else {
			
			titulo = tit;
		}
		
		System.out.println(titulo);
		System.out.println(Thread.currentThread().getName() + " Start");

		System.out.println(
				"\"C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe\" --headless --disable-gpu --print-to-pdf=\"D:\\documentos convertidos\\"
						+ titulo + ".pdf\" \"" + url + "\"");
		Runtime.getRuntime().exec(
				"\"C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe\" --headless --disable-gpu --print-to-pdf=\"D:\\documentos convertidos\\"
						+ titulo + ".pdf\" \"" + url + "\"");

		String UbicacionNuevoArchivo = "D:\\documentos convertidos\\" + titulo + ".pdf";

		// AHORA SE VERIFICA QUE EL ARCHIVO EXISTE
		File verificacion = new File(UbicacionNuevoArchivo);
		while (!verificacion.exists()) {
		}

		System.out.println(UbicacionNuevoArchivo);

		Path path = Paths.get(UbicacionNuevoArchivo);
		byte[] fileBytes = Files.readAllBytes(path);
		byte[] encodedBytes = Base64.getEncoder().encode(fileBytes);
 
		String base64PDF = new String(encodedBytes);
 
 
        
         
		 Archivo archivopdf = new Archivo(obj.idSubLote, base64PDF, obj.idArchivo);
		 System.out.println("SE HA CREADO EL NUEVO OBJETO");
		 

		System.out.println(Thread.currentThread().getName() + " Termino Hilo");

		return archivopdf;

	}

}
