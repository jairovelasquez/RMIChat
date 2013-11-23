/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Proxy;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author JairoDavid
 */
public interface IClient extends Remote {
    
    public void getMessage(String mensaje) throws RemoteException;
    
    public void setUser(String User) throws RemoteException;
    
    public String getUser() throws RemoteException;
    
    public void setID(int ID) throws RemoteException;
    
    public int getID() throws RemoteException;
}
