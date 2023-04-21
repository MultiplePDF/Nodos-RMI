package com.example.SOAPwebservice;

import java.io.File;
import java.io.IOException;
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

        System.out.println(title);

        String newFilePath = "/home/nodo1-admin/convertidos/" + title + ".pdf";

        System.out.println(Thread.currentThread().getName());
        System.out.println("google-chrome-stable  --headless --disable-gpu --print-to-pdf=/home/nodo1-admin/convertidos/" + title + ".pdf" + " " + url);
        Runtime.getRuntime().exec("google-chrome-stable  --headless --disable-gpu --print-to-pdf=/home/nodo1-admin/convertidos/" + title + ".pdf" + " " + url);

        File verificacion = new File(newFilePath);

        // AHORA SE VERIFICA QUE EL ARCHIVO EXISTE

        while (!verificacion.exists()) {
        }

        return newFilePath;

    }

}
