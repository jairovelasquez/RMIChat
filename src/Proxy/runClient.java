/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Proxy;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 *
 * @author JairoDavid
 */
public class runClient {
    
    public static void startClient(String u, String p) {
        try {
            int i = 0;
            Registry Reg = LocateRegistry.getRegistry("127.0.0.1", 1993);
            IServer Server = (IServer) Reg.lookup("Chat");
            Client Client = new Client(u,p);
            Server.registerClient(Client);
        } catch (RemoteException ex) {
            System.out.println("Error al conectarse al Servidor.");
        } catch (NotBoundException ex) {
            System.out.println("Error en el nombre del Servidor.");
        }        
    }
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String u, p;
        do {
            System.out.print("Ingrese usuario: ");
            u = s.nextLine();
            System.out.print("Ingrese contraseña: ");
            p = s.nextLine();
        } while (u.length() == 0 || p.length() == 0);
        //String u = "Jairo", p = "123";
        startClient(u,p);
    }
    
}
