package com.xqs.jdbc;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JdbcMySQL8Demo {
    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/dev?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "iluying616";

        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            String sql = "select id,name,inet_ntoa(ip) from user";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String ip = rs.getString(2);
                
                System.out.println("id: " + id + ", name: " + name + ", ip: " + ip);
            }
            rs.close();
        } finally {
            if (connection != null)
                connection.close();
            if (statement != null)
                statement.close();
        }
    }
}
