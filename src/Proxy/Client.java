/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Proxy;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client extends UnicastRemoteObject implements IClient {
    private int ID;
    private String User;
    private String Pass;
    private final IServer Server;
    private Queue<Message> Messages;

    public IServer getServer() {
        return Server;
    }

    public Queue<Message> getMessages() {
        return Messages;
    }
    private ArrayList<String> usuarios = new ArrayList();
    private LandingV2 landing;
    
    public Client(String User, String Pass, final IServer Server) throws RemoteException {
        super();
        this.User = User;        
        this.Pass = Pass;        
        this.Server = Server;
        Messages = new LinkedList<>();        
        new Thread() {
            public void run() {
                while (true) {
                    String resp;
                    Scanner s = new Scanner(System.in);
                    do {
                        System.out.print("What? (m/r/e) ");
                        resp = s.nextLine();
                    } while (resp.length() == 0);
                    switch (resp.toLowerCase().charAt(0)) {
                        case 'm':
                            int usid;
                            String mess;
                            System.out.print("\nWhich user? ");
                            usid = s.nextInt();
                            s.nextLine();
                            System.out.print("Message? ");
                            mess = s.nextLine();
                            Message newe = new Message(getID(),usid,mess,"");
                            System.out.println(newe.toString() + "\n");
                            sendMessage(newe);
                            break;
                        case 'r':
                            System.out.println();
                            if (Messages.size() == 0) {
                                System.out.println("No hay mensajes nuevos.");
                            } else {
                                while (!Messages.isEmpty())
                                    System.out.println(Messages.peek().getStart() + " says: " + Messages.poll().getMessage());
                            }
                            System.out.println();
                            break;
                        case 'e':
                            go();
                            System.exit(0);
                            break;
                        default:
                            System.out.println("...Waiting...");
                    }
                }
            }
        }.start();
    }
    
    public void seConectoUnUsuario(){
        this.landing.hello(this.getOnlineUsers());
    }
    
    public void go() {
        try {
            Server.releaseClient(this);
        } catch (RemoteException ex) {
            System.out.println("Error cerrando sesión.");
        }
    }

    public String getUser() {
        return User;
    }

    public void setUser(String User) {
        this.User = User;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String Pass) {
        this.Pass = Pass;
    }
        
    public int getID() {
        return ID;
    }
    
    public void setID(int ID) {
        this.ID = ID;
        this.landing = new LandingV2(this);
    }
    
    public void getMessage(Message Message) {
        Messages.add(Message);
        this.landing.paint(Message);
        //String user = 
        //this.landing.recibirMensaje(Message.getMessage(),""+Message.getStart(),0);
    }
    
    public void sendMessage(Message Message)  {
        try {
            Server.getMessage(Message);            
        } catch (RemoteException ex) {
            System.out.println("Error enviando mensaje al servidor.");
        }
    }
    
    public String toString(){
        return "ID: " + this.ID + "\nUser: " + this.User + "\n";
    }
    
    /**
     *
     * @return
     */
    @Override
        public ArrayList<String> getOnlineUsers(){
        this.usuarios.clear();
        try {
            System.out.println("Tamaño de usuarios: "+Server.getUsers());
            this.usuarios = Server.getUsers();            
            
        } catch (RemoteException ex) {
            System.out.println("Error al obtener los usuarios."+ex.toString());
        }
        
        return this.usuarios;
    }

}
