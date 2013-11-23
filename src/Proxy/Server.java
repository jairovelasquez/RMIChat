/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proxy;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JairoDavid
 */
public class Server extends UnicastRemoteObject implements IServer {

    Map<String, IClient> Clients;
    int i = 1;

    public Server() throws RemoteException {
        super();
        Clients = new HashMap<String, IClient>();
        System.out.println("Sistema listo.\n");
        new Thread() {
            public void run() {
                while (true) {
                    String resp;
                    Scanner s = new Scanner(System.in);
                    do {
                        System.out.print("Print clients? ");
                        resp = s.nextLine();
                    } while (resp.length() == 0);
                    if (resp.charAt(0) == 'y') {
                        if (Clients.size() > 0) {
                            System.out.println("\nShowing registered clients.");
                            Iterator users = Clients.values().iterator();
                            while (users.hasNext()) {
                                Object item = users.next();
                                try {
                                    System.out.print("User: " + ((IClient)item).getUser() + " Pass: " + ((IClient)item).getPass() + "\n");
                                } catch (RemoteException ex) {
                                    System.out.println("Error");
                                }
                            }
                            System.out.println("");
                        } else {
                            System.out.println("No hay clientes aún.");
                        }
                    }
                }
            }
        }.start();
    }

    public void registerClient(IClient c) {
        try {
            Clients.put(c.getUser(), c);
            c.setID(i++);
            System.out.println("Se registró exitosamente al cliente " + c.getID() + ".");
        } catch (RemoteException ex) {
            System.out.println("Fallo asignándole ID a cliente.");
        }
    }

    public void releaseClient(IClient c) {
        try {
            if (Clients.containsKey(c.getUser())) {
                Clients.remove(c.getUser());
            }
        } catch (RemoteException ex) {
            System.out.println("Fallo removiendo al cliente.");
        }
    }
}
