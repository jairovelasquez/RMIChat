/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Proxy;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

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
