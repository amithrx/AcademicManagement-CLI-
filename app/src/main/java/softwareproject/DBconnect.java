package softwareproject;

import java.sql.*;

public class DBconnect {
    //postgres credentials 
    private final static String url = "jdbc:postgresql://localhost/miniproject";
    private final static String user = "postgres";
    private final static String password = "Amit@7870";

    //method for connecting to database
    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
