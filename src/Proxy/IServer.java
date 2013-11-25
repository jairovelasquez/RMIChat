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
public interface IServer extends Remote {
    //void sendMessage(int id, String name) throws RemoteException;
    
    public void registerClient(IClient c) throws RemoteException;
    
    public void releaseClient(IClient c) throws RemoteException;
    
    public void sendMessage() throws RemoteException;
 
    public void getMessage(Message Message) throws RemoteException;
}
