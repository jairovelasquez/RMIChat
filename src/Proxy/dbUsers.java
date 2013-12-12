/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Proxy;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
/**
 *
 * @author Martha Hidalgo
 */
public class dbUsers {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    public void connectDataBase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error, no se encontro el driver de la base de datos.");
        } 
        try {
            connect = DriverManager.getConnection("jdbc:mysql://localhost/db_os_users?"
              + "user=root");//&password=123456");
            System.out.println("se conecto");
        } catch (SQLException ex) {
            System.out.println("Error, no se pudo conectar a la base de datos.");
        }/*finally {
            close();
            }*/
    }
    public boolean isUnique(String u){
        try {
            statement = connect.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM db_os_users.usuarios WHERE username = '"+u+"'");
            return resultSet.first();
        } catch (SQLException ex) {
            System.out.println("Error verificando que el username "+u+" es unico.");
        }
        return false;
    }
    public boolean authClient(String u,String p){
        try {
            statement = connect.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM db_os_users.usuarios WHERE username = '"+u+"'");
            while(resultSet.next()){
                if(resultSet.getString("password").equals(p)){
                    return true;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error, autenticando al usuario "+u+"con contraseña "+p);
        }
        return false;
    }
    public int insertClient(String u, String p,String nombre){
        try {
            preparedStatement = connect.prepareStatement("INSERT INTO `db_os_users`.`usuarios` ( `username`,`password`,`nombre_completo`,`estado`) "+"VALUES ( '"+u+"','"+p+"','"+nombre+"','0')");
            return preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("No se pudo agregar el cliente "+u+" a la base de datos.");
        }
        return -1;
    }
    public String[] getClient(String u){
        String datos[]=new String[5];
        int i=0;
        try {
            statement = connect.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM db_os_users.usuarios WHERE username = '"+u+"'");
            while(resultSet.next()){
                datos[i]=resultSet.getString("");
            }
            //return datos;
        } catch (SQLException ex) {
            System.out.println("Error extrayendo la informacion del usuario: "+u);
        }
        return datos;
    }
    public void setClienteOnline(String u){
        try {
            System.out.print(u);
            preparedStatement = connect
                    .prepareStatement("UPDATE `db_os_users`.`usuarios` SET  `estado` = 1 WHERE username = '"+u+"'");
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
            //System.out.println("No se pudo agregar el cliente "+u+" a la base de datos.");
        }
    }
    public void setClienteOffline(String u){
        try {
            System.out.print(u);
            preparedStatement = connect
                    .prepareStatement("UPDATE `db_os_users`.`usuarios` SET  `estado` = 0 WHERE username = '"+u+"'");
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
            //System.out.println("No se pudo agregar el cliente "+u+" a la base de datos.");
        }
    }
    public int getClientid(String u){
        try {
            statement = connect.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM db_os_users.usuarios WHERE username = '"+u+"'");
            while(resultSet.next()){
                return resultSet.getInt("id_user");
            }
            //return datos;
        } catch (SQLException ex) {
            System.out.println("Error extrayendo la informacion del usuario: "+u);
        }
        return -1;
    }
    public void insertMessage(int u1,int u2,String m){
        
        //Date now = new Date();
        //String date = now.
        String date;
        date = Calendar.getInstance().get(Calendar.YEAR)+"-"+Calendar.getInstance().get(Calendar.MONTH)+"-"+Calendar.getInstance().get(Calendar.DATE)+" "+Calendar.getInstance().get(Calendar.HOUR)+":"+Calendar.getInstance().get(Calendar.MINUTE)+":"+Calendar.getInstance().get(Calendar.SECOND);
        try {
            preparedStatement = connect.prepareStatement("INSERT INTO `db_os_users`.`mensajes` ( `id_user1`,`id_user2`,`mensaje`,`hora`) "+"VALUES ( '"+u1+"','"+u2+"','"+m+"','"+date+"')");
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
            //2013-12-10 08:16:31
            //System.out.println("No se pudo agregar el cliente "+u+" a la base de datos.");
        }
    }
    
    public ArrayList<Message> getConversation(int client, int friend) {
        
        ArrayList<Message> conversation = new ArrayList();
        
        try {
            statement = connect.createStatement();
            
            String query =
                      "SELECT * FROM db_os_users.mensajes "
                    + "WHERE (id_user1 = '"+client+"' "
                    + "AND id_user2 = '"+friend+"') "
                    + "OR (id_user1 = '"+friend+"' AND id_user2 = '"+client+"')";
            
            System.out.println(query);

            resultSet = statement.executeQuery(query);
            
            while(resultSet.next()){
                int user1 = resultSet.getInt("id_user1");
                int user2 = resultSet.getInt("id_user2");
                String mensaje = resultSet.getString("mensaje");
                String hora = resultSet.getString("hora");
                conversation.add(new Message(user1,user2,mensaje,hora));                
            }                           
            //return datos;
        } catch (SQLException ex) {
            System.out.println("Error extrayendo la informacion del usuario");
        }        
        return  conversation;
    }
}


