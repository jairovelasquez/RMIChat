/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Proxy;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class Client extends UnicastRemoteObject implements IClient {
    private int ID;
    private String User;
    private String Pass;
    
    public Client(String User, String Pass) throws RemoteException {
        super();
        this.User = User;
        this.Pass = Pass;
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
    }
    
    public void getMessage(String Message)  {
        System.out.println("Mensaje:" + Message);
    }
}
