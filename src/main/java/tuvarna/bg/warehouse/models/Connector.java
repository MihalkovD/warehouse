
package tuvarna.bg.warehouse.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {

    /*String url = "jdbc:mysql://www.papademas.net:3306/510labs?autoReconnect=true&useSSL=false";
    String driver = "com.mysql.jdbc.Driver";
    String userName = "db510";
    String password = "510";*/

    String url = "jdbc:mysql://localhost:3306/warehouse";
    String driver = "com.mysql.jdbc.Driver";
    String userName = "User";
    String password = "123456789";

    public Connection connect() {
        try {
            //Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url, userName, password);
            System.out.println("Connected database successfully...");
            return conn;
        } catch ( SQLException e) {
        }
        return null;
    }
}