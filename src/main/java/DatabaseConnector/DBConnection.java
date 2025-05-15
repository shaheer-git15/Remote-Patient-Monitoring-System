package DatabaseConnector;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/rpms", //  your DB name
                    "root",                             //  your MySQL username
                    "Shaheerkhan@2004"                     // your MySQL password
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}