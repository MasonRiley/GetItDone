package com.example.getitdone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class EventDB {

    public static Connection getRemoteConnection() {
        if (System.getenv("phoningitin.cmkzamfpzfbh.us-east-2.rds.amazonaws.com:3306") != null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                String dbName = System.getenv("GetItDoneDB");
                String userName = System.getenv("admin");
                String password = System.getenv("password");
                String hostname = System.getenv("phoningitin.cmkzamfpzfbh.us-east-2.rds.amazonaws.com");
                String port = System.getenv("3306");
                String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password;
                Connection con = DriverManager.getConnection(jdbcUrl);
                return con;
            } catch (ClassNotFoundException e) {
                System.out.println("ERROR: Class not found");
            } catch (SQLException e) {
                System.out.println("ERROR: SQL Exception");
            }
        }
        return null;
    }
}
