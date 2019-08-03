package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Paulous
 * Database class to connect to xampp server using port 3306 for mysql
 * Add the mysql_connector to your project for this class to not give you any errors
 *
 */


class DatabaseClassConnectForXampp {
    static Connection getCon(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/employesystem", "root", "");
            return con;
        } catch(Exception e){
            return null;
        }
    }

}
