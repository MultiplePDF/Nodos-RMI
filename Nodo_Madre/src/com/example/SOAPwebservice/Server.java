package com.example.SOAPwebservice;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Server extends UnicastRemoteObject implements InterfaceRMI {

    protected Server() throws RemoteException {
        super();

    }

    @Override
    public SubBatch conversionOffice(SubBatch object) throws RemoteException {

        ExecutorService executor = Executors.newFixedThreadPool(3);

        System.out.println("Se esta haciendo la conversion");
        File[] files = object.files;
        File archivospdf[] = new File[files.length];

        for (int i = 0; i < files.length; i++) {

            Callable<File> worker = new ConvertFile(files[i]);
            Future<File> future = executor.submit(worker);

            try {
                File temp = future.get();
                archivospdf[i] = temp;
            } catch (InterruptedException | ExecutionException e) {

                e.printStackTrace();
            }

        }

        executor.shutdown();
        while (!executor.isTerminated()) {
        }

        SubBatch finalBatch = new SubBatch(object.subBatchID, object.userID, archivospdf);

        return finalBatch;
    }

    @Override
    public SubBatch conversionURL(SubBatch object) throws RemoteException {

        ExecutorService executor = Executors.newFixedThreadPool(3);

        File[] files = object.files;
        File archivospdf[] = new File[files.length];

        for (int i = 0; i < files.length; i++) {

            Callable<File> worker = new ConvertURL(files[i]);
            Future<File> future = executor.submit(worker);

            try {
                File temp = future.get();
                archivospdf[i] = temp;

            } catch (InterruptedException | ExecutionException e) {

                e.printStackTrace();
            }

        }

        executor.shutdown();
        while (!executor.isTerminated()) {
        }

        SubBatch finalBatch = new SubBatch(object.subBatchID, object.userID, archivospdf);

        return finalBatch;
    }

}
