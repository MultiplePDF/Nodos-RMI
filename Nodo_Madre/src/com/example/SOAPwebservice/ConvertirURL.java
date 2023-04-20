package com.example.SOAPwebservice;
 
import java.io.File;
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

		String UbicacionNuevoArchivo = convertirURL(obj);
 

		Path path = Paths.get(UbicacionNuevoArchivo);
		byte[] fileBytes = Files.readAllBytes(path);
		byte[] encodedBytes = Base64.getEncoder().encode(fileBytes);

		String base64PDF = new String(encodedBytes);

		Archivo archivopdf = new Archivo(obj.idSubLote, base64PDF, obj.idArchivo);
 

		return archivopdf;
	}

	public String convertirURL(Archivo obj) throws IOException {

		String url = obj.url;
		String titulo = "";
		String tit = url.replaceAll("[^a-zA-Z]", "");

		if (tit.length() >= 100) {
			titulo = tit.substring(0, 20);

		} else {

			titulo = tit;
		}

		System.out.println(titulo);

		String UbicacionNuevoArchivo = "/home/nodo1-admin/convertidos/" + titulo + ".pdf";

		System.out.println(Thread.currentThread().getName());
	    System.out.println("google-chrome-stable  --headless --disable-gpu --print-to-pdf=/home/nodo1-admin/convertidos/"+ titulo +".pdf" + " " + url);
		Runtime.getRuntime().exec("google-chrome-stable  --headless --disable-gpu --print-to-pdf=/home/nodo1-admin/convertidos/"+ titulo +".pdf" + " " + url);

		File verificacion = new File(UbicacionNuevoArchivo);

		// AHORA SE VERIFICA QUE EL ARCHIVO EXISTE

		while (!verificacion.exists()) {
		}

		return UbicacionNuevoArchivo;

	}

}
