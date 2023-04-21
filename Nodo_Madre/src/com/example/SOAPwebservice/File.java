package com.example.SOAPwebservice;

import java.io.Serializable;

public class File implements Serializable {

    int fileID;
    String subBatchID;
    String url;
    String base64;
    String name;
    int size;

    public File(String idSubLote, String base64, String name) {

        this.subBatchID = idSubLote;
        this.base64 = base64;
        this.name = name;
    }

    public File(String subBatchID, String url, int fileID) {

        this.subBatchID = subBatchID;
        this.url = url;
        this.fileID = fileID;
    }

    @Override
    public String toString() {
        return "{\n" +
                "        \"fileID\": " + subBatchID + ",\n" +
                "        \"subBatchID\": \"" + subBatchID + "\",\n" +
                "        \"url\": \"" + url + "\",\n" +
                "        \"base64\": \"" + base64 + "\",\n" +
                "        \"fileName\": \"" + name + "\",\n" +
                "    }";
    }

}