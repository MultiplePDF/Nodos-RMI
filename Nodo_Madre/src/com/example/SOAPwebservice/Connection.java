package com.example.SOAPwebservice;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Connection {

    public static void main(String[] args) {
        try {

            InterfaceRMI server = (InterfaceRMI) new Server();
            LocateRegistry.createRegistry(1099);

            String rmiObjectName = "rmi://10.154.12.166:1099/convertidor";
            //String rmiObjectName = "rmi://10.154.12.167:1099/convertidor";
            //String rmiObjectName = "rmi://10.154.12.168:1099/convertidor";
            Naming.rebind(rmiObjectName, server);

            System.out.println("Servidor Conectado de Convertidor");

        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }

    }

}