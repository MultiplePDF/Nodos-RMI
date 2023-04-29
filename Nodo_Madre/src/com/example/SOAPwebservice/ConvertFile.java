package com.example.SOAPwebservice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.concurrent.Callable;

public class ConvertFile implements Callable<com.example.SOAPwebservice.File> {
    com.example.SOAPwebservice.File obj;

    public ConvertFile(com.example.SOAPwebservice.File obj) {
        super();
        this.obj = obj;

    }

    @Override
    public com.example.SOAPwebservice.File call() throws Exception {
        com.example.SOAPwebservice.File nuevo = convertFile(obj);
        return nuevo;
    }

    public com.example.SOAPwebservice.File convertFile(com.example.SOAPwebservice.File obj) throws IOException, java.io.IOException {

        System.out.println("OBTENCION DE DOCUMENTO: " + obj.name);
        String file[] = obj.name.split("\\.");
        String extension = "." + file[file.length - 1];
        String base64 = obj.base64;
        String checksumAntiguo = obj.checksum; 

        String name = "";
        for (int i = 0; i < file.length - 1; i++) {
            name = name + file[i];
        }

        System.out.println(name);
        
        File newAccess = new File("/home/nodo1-admin/noconvertidos/" + obj.name );
    	System.out.println(newAccess);

     
        // DECODIFICAR Archivos
        byte[] decoded = Base64.getDecoder().decode(base64);
 
        FileOutputStream fos = new FileOutputStream(newAccess);
        fos.write(decoded);
        fos.close();
      
        System.out.println("Termino " + newAccess.getAbsolutePath());
        System.out.println(String.valueOf(newAccess));
        
        //SE NECESITA VERIFICAR LA INTEGRIDAD DEL ARCHIVO DE MD5
        
        String checksum = "";
        
        try {
            checksum = GetSHAString(newAccess.getAbsolutePath());
            System.out.println("El SHA-256 checksum de " + newAccess.getAbsolutePath() + " es " + checksum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        if(checksum.equals(checksumAntiguo)) {

        	if(newAccess.exists()) {
        		if(newAccess.canRead()) {
        			System.out.println("Tiene permiso de lectura");
        		}else {
        			System.out.println("No tiene permiso de lectura");
        		}
        	}else {
        		System.out.println("El archivo no existe");
        	}
        	
        	String ruta =  newAccess.getAbsolutePath();
            String newFilePath = "/home/nodo1-admin/convertidos/" + name + ".pdf";
            System.out.println(newFilePath);
            
        	newAccess = null;
        	
    
        	System.out.println(Thread.currentThread().getName());
        	
            System.out.println("unoconv -f pdf -o " + newFilePath + ruta );
            
            
            ProcessBuilder pb = new ProcessBuilder("unoconv", "-f", "pdf", "-o", newFilePath, ruta);
 
            
            
            // Runtime.getRuntime().exec("sudo soffice --headless --convert-to pdf:writer_pdf_Export --outdir \"/home/nodo1-admin/convertidos/\" \""  + ruta + "\" ");
            Process pr = pb.start();
            
       
			try {
				int exitCode = pr.waitFor();
				if (exitCode == 0) {
	                System.out.println("Comando ejecutado correctamente");
	            } else {
	                System.out.println("Error al ejecutar el comando");
	            }
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
             
            
            
            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
            
            String line;
            while (true) {
                line = in.readLine();
                if (line == null) { break; }
                System.out.println(line);
            }
            


            // AHORA SE VERIFICA QUE EL File EXISTE

            File verify = new File(newFilePath);
            while (!verify.exists()) {
             
            	 
            }

            FileInputStream input = new FileInputStream(verify);
            byte[] bytes = new byte[(int) input.available()];
            input.read(bytes);
            String base64PDF = new String(Base64.getEncoder().encode(bytes));

            com.example.SOAPwebservice.File pdfFile = new com.example.SOAPwebservice.File(obj.subBatchID, base64PDF, name + ".pdf", checksum);
            System.out.println("SE CREO OBJETO");
            return pdfFile;
        	
        }else {
        	
        	System.out.println("No se puede convertir porque no son iguales");
        	com.example.SOAPwebservice.File pdfFile = new com.example.SOAPwebservice.File(null, null, null, null);
        	return pdfFile;
        	
        }
           
         
    }
    
    
    public static byte[] GetChecksum(String nombreArchivo) throws Exception {
        InputStream fis = new FileInputStream(nombreArchivo);

        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("SHA-256");
        int numRead;
        // Leer el archivo pedazo por pedazo
        do {
            // Leer datos y ponerlos dentro del búfer
            numRead = fis.read(buffer);
            // Si se leyó algo, se actualiza el MessageDigest
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);

        fis.close();
        // Devolver el arreglo de bytes
        return complete.digest();
    }
    
    public static String GetSHAString(String nombreArchivo) throws Exception {
        // Convertir el arreglo de bytes a cadena
        byte[] b = GetChecksum(nombreArchivo);
        StringBuilder resultado = new StringBuilder();

        for (byte unByte : b) {
            resultado.append(Integer.toString((unByte & 0xff) + 0x100, 16).substring(1));
        }
        return resultado.toString();
    }
    
    
}
