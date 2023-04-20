package com.example.SOAPwebservice;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Base64;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Servidor extends UnicastRemoteObject implements contratoRMI {

	protected Servidor() throws RemoteException {
		super();

	}

	@Override
	public Sublote conversionOffice(Sublote objeto) throws RemoteException {

		ExecutorService executor = Executors.newFixedThreadPool(3);

		System.out.println("Se esta haciendo la conversion");
		Archivo[] archivos = objeto.archivos;
		Archivo archivospdf[] = new Archivo[archivos.length];

		for (int i = 0; i < archivos.length; i++) {

			Callable<Archivo> worker = new ConvertirArchivo(archivos[i]);
			Future<Archivo> future = executor.submit(worker);

			try {
				Archivo temp = future.get();
				archivospdf[i] = temp;
			} catch (InterruptedException | ExecutionException e) {

				e.printStackTrace();
			}

		}

		executor.shutdown();
		while (!executor.isTerminated()) {
		}

		Sublote ConversionRealizada = new Sublote(objeto.idSublote, objeto.idUsuario, archivospdf);

		return ConversionRealizada;
	}

	@Override
	public Sublote conversionURL(Sublote objeto) throws RemoteException {

		ExecutorService executor = Executors.newFixedThreadPool(3);

		Archivo[] archivos = objeto.archivos;
		Archivo archivospdf[] = new Archivo[archivos.length];

		for (int i = 0; i < archivos.length; i++) {

			Callable<Archivo> worker = new ConvertirURL(archivos[i]);
			Future<Archivo> future = executor.submit(worker);

			try {
				Archivo temp = future.get();
				archivospdf[i] = temp;

			} catch (InterruptedException | ExecutionException e) {

				e.printStackTrace();
			}

		}

		executor.shutdown();
		while (!executor.isTerminated()) {
		}

		Sublote ConversionRealizada = new Sublote(objeto.idSublote, objeto.idUsuario, archivospdf);

		return ConversionRealizada;
	}

}
