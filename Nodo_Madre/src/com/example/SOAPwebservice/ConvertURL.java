package com.example.SOAPwebservice;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.concurrent.Callable;

public class ConvertURL implements Callable<com.example.SOAPwebservice.File> {

    com.example.SOAPwebservice.File obj;

    public ConvertURL(com.example.SOAPwebservice.File obj) {
        super();
        this.obj = obj;
    }

    @Override
    public com.example.SOAPwebservice.File call() throws Exception {

        String newFilePath = convertirURL(obj);

        Path path = Paths.get(newFilePath);
        byte[] fileBytes = Files.readAllBytes(path);
        byte[] encodedBytes = Base64.getEncoder().encode(fileBytes);

        String base64PDF = new String(encodedBytes);
        System.out.println("SE CREO OBJETO");

        com.example.SOAPwebservice.File pdfFile = new com.example.SOAPwebservice.File(obj.subBatchID, base64PDF, obj.fileID);
        return pdfFile;
    }

    public String convertirURL(com.example.SOAPwebservice.File obj) throws IOException {

        String url = obj.url;
        String title = "";
        String tit = url.replaceAll("[^a-zA-Z]", "");

        if (tit.length() >= 100) {
            title = tit.substring(0, 20);

        } else {

            title = tit;
        }
        obj.name = title;
        System.out.println(title);

        String newFilePath = "/home/nodo1-admin/convertidos/" + title + ".pdf";

        System.out.println(Thread.currentThread().getName());
 
        
        ProcessBuilder pb = new ProcessBuilder("wkhtmltopdf","--load-error-handling", "ignore", url, newFilePath);
        Process process = pb.start();
        
        //Runtime.getRuntime().exec("google-chrome-stable  --headless --disable-gpu --print-to-pdf=/home/nodo1-admin/convertidos/" + title + ".pdf" + " " + url);
		try {
			int exitCode = process.waitFor();
			if (exitCode == 0) {
                System.out.println("Comando ejecutado correctamente");
            } else {
                System.out.println("Error al ejecutar el comando");
            }
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
         
        
        
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        
        String line;
        while (true) {
            line = in.readLine();
            if (line == null) { break; }
            System.out.println(line);
        }
        
        
        
        
        File verificacion = new File(newFilePath);

        // AHORA SE VERIFICA QUE EL ARCHIVO EXISTE

        while (!verificacion.exists()) {
        }

        return newFilePath;

    }

}
