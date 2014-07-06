/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ossama
 */
class DBConnect {
    

    private static Connection conn = null;
//    private static final String url = "jdbc:mysql://192.168.0.15/prayertime?useUnicode=true&characterEncoding=UTF-8";
//    private static final String user = "admin";
    private static final String url = "jdbc:mysql://127.0.0.1/prayertime?useUnicode=true&characterEncoding=UTF-8";
    private static final String user = "root";
    private static final String pass = "soumaya";

    public static Connection connect() throws SQLException
    {
        try{Class.forName("com.mysql.jdbc.Driver").newInstance();}
        catch(ClassNotFoundException cnfe){System.err.println("Error: "+cnfe.getMessage());}
        catch(InstantiationException ie){System.err.println("Error: "+ie.getMessage());}
        catch(IllegalAccessException iae){System.err.println("Error: "+iae.getMessage());}

        conn = DriverManager.getConnection(url,user,pass);
        return conn;
    }

    public static Connection getConnection() throws SQLException{
        if(conn !=null && !conn.isClosed())
            return conn;
        connect();
        return conn;

    }
}
