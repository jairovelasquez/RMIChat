/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Proxy;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JairoDavid
 */
public class Server extends UnicastRemoteObject implements IServer {

    private Map<String, IClient> Clients;
    private Queue<Message> Requests;
    private int i = 1;
    private ArrayList<String> log = new ArrayList();
    private ArrayList<String> usuarios;
    public dbUsers dbu = new dbUsers();

    public Server() throws RemoteException {
        super();
        this.usuarios = new ArrayList();
        Clients = new HashMap<>();
        Requests = new LinkedList<>();
        
        dbu.connectDataBase();
        log.add("User database connected.");
        log.add("Server up!");
        System.out.println(log.get(1));
        new Thread() {
            public void run() {
                while (true) {
                    String resp;
                    Scanner s = new Scanner(System.in);
                    do {
                        System.out.print("What? (u/l/e) ");
                        resp = s.nextLine();
                    } while (resp.length() == 0);
                    switch (resp.toLowerCase().charAt(0)) {
                        case 'u':
                            getUsers();
                            break;
                        case 'l':
                            System.out.println("\nPrinting Log.");
                            for (int j = 0; j < log.size(); j++) {
                                System.out.println(log.get(j));
                            }
                            System.out.println();
                            break;
                        case 'e':
                            System.exit(0);
                            break;
                        default:
                            System.out.println("...Waiting...");
                    }
                }
            }
        }.start();
    }
    @Override
    public boolean authenClient(String user,String password){
      //  return true;
        return dbu.authClient(user, password);
    }
    
    public int insertClient(String usuario, String password, String nombre){
        return dbu.insertClient(usuario, password,nombre);
    }
    
    public ArrayList<Message> getConversationFromDatabase(int client, int friend){
        return dbu.getConversation(client, friend);
    }
    
    public int getUserIdByUsername(String username){
        return dbu.getClientid(username);        
    }

    public void registerClient(IClient c) {
        try {
            Clients.put(c.getUser(), c);
            //c.setID(i++);
            dbu.setClienteOnline(c.getUser());
            c.setID(dbu.getClientid(c.getUser()));
            this.actualizarListado();
            log.add("Se registró al cliente " + c.getID() + ".");
        } catch (RemoteException ex) {
            System.out.println("aqui esta fallando" + ex.toString());
            System.out.println("Fallo asignándole ID a cliente.");
        }
    }

    public void releaseClient(IClient c) {
        try {
            if (Clients.containsKey(c.getUser())) {
                Clients.remove(c.getUser());
                dbu.setClienteOffline(c.getUser());
                log.add("Se removió al cliente " + c.getID() + ".");
                this.actualizarListado();
            }
        } catch (RemoteException ex) {
            System.out.println("Fallo removiendo al cliente.");
        }
    }

    public IClient findUser(int ID) {
        Iterator users = Clients.values().iterator();
        while (users.hasNext()) {
            try {
                Object item = users.next();
                if (((IClient)item).getID() == ID) {
                    return Clients.get(((IClient)item).getUser());
                }
            } catch (RemoteException ex) {
                System.out.println("Error obteniendo información del cliente.");
            }
        }
        return null;
    }

    public void getMessage(Message Message) {
        Requests.add(Message);        
        if (Message.getEnd() == -1) {
            log.add("Se recibió un broadcast desde " + Message.getStart());
            log.add("El mensaje es: "+Message.getMessage());
        } else {
            dbu.insertMessage(Message.getStart(), Message.getEnd(), Message.getMessage());
            log.add("Se recibió mensaje desde " + Message.getStart() + " hacia " + Message.getEnd() + ".");
        }
        sendMessage();
    }

    public void sendMessage() {
        try {
            if (Requests.peek().getEnd() == -1) {
                Iterator users = Clients.values().iterator();
                while (users.hasNext()) {
                    Object item = users.next();
                    if (((IClient) item).getID() != Requests.peek().getStart()) {
                        ((IClient) item).getMessage(Requests.peek());
                    }
                }
                log.add(Requests.peek().getStart() + " envió un broadcast.");
                Requests.poll();
            } else { 
                findUser(Requests.peek().getEnd()).getMessage(Requests.peek());
                
                log.add("Se envió mensaje hacia " + Requests.peek().getEnd() + " desde " + Requests.poll().getStart());
            }
        } catch (RemoteException ex) {
            System.out.println("Error enviando mensaje.");
        }
    }        
    
    public void actualizarListado() {
        if(Clients.size()> 0) {
            Iterator users = Clients.values().iterator();
            
            while(users.hasNext()) {
                Object item = users.next();
                
                try {                    
                    ((IClient) item).seConectoUnUsuario();
                } catch (RemoteException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
        
    }
    
    public ArrayList<String> getUsers() {
        usuarios.clear();
        if (Clients.size() > 0) {
            System.out.println("\nObtener Usuarios.");
            Iterator users = Clients.values().iterator();
            while (users.hasNext()) {                
                Object item = users.next();
                
                try {
                    usuarios.add(""+((IClient) item).getUser());
                    System.out.print("User: " + ((IClient) item).getUser() + " Pass: " + ((IClient) item).getPass() + "\n");
                } catch (RemoteException ex) {
                    System.out.println("Error getting client information.");
                }
            }
            System.out.println();
        } else {
            System.out.println("\nNo hay clientes aún.\n");
        }
        
        return this.usuarios;
    }
}
