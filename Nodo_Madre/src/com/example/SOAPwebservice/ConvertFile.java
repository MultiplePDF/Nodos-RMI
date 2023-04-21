package com.example.SOAPwebservice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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

        String name = "";
        for (int i = 0; i < file.length - 1; i++) {
            name = name + file[i];
        }

        System.out.println(name);

        // DECODIFICAR Archivos
        byte[] decoded = Base64.getDecoder().decode(base64);

        /*
         * LOS Archivos SE GUARDAN EN C:\Users\USUARIO\AppData\Local\Temp\ Varia de
         * acuerdo al computador C:\Users\admin\AppData\Local\Temp\
         */

        File tempFile = File.createTempFile(name, extension);
        FileOutputStream fos = new FileOutputStream(tempFile);
        fos.write(decoded);
        fos.close();

        System.out.println("Termino " + tempFile.getAbsolutePath());
        System.out.println(String.valueOf(tempFile));

        System.out.println(Thread.currentThread().getName());

        System.out.println("libreoffice --convert-to pdf --outdir /home/nodo1-admin/convertidos/" + " " + tempFile);
        Runtime.getRuntime().exec("libreoffice --convert-to pdf --outdir /home/nodo1-admin/convertidos" + " " + tempFile);


        String convertedFile[] = tempFile.getName().split("\\.");
        String newName = "";
        for (int i = 0; i < convertedFile.length - 1; i++) {
            newName = newName + convertedFile[i];
        }

        String newFilePath = "/home/nodo1-admin/convertidos/" + newName + ".pdf";

        // AHORA SE VERIFICA QUE EL File EXISTE

        File verify = new File(newFilePath);
        while (!verify.exists()) {
        }


        FileInputStream input = new FileInputStream(verify);
        byte[] bytes = new byte[(int) input.available()];
        input.read(bytes);
        String base64PDF = new String(Base64.getEncoder().encode(bytes));

        com.example.SOAPwebservice.File pdfFile = new com.example.SOAPwebservice.File(obj.subBatchID, base64PDF, name);
        return pdfFile;
    }
}
