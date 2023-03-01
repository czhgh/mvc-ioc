package com.clement.fruit.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: clement
 * @Date: 2023年02月27日  20:52
 * @Description:
 */
public class ConnUtil {
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String URL = "jdbc:mysql://localhost:3306/web?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8";
    public static final String USER = "root";
    public static final String PWD = "root";
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    private static Connection createConnection() {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PWD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;

    }

    public static Connection getConn() {
        Connection connection = threadLocal.get();
        if (connection == null) {
            connection = createConnection();
            threadLocal.set(connection);
        }
        return  threadLocal.get();
    }

    public static void CloseConn() throws SQLException {
        Connection connection = threadLocal.get();
        if (connection == null) {
            return;
        }
        if(!connection.isClosed()){
           connection.close();
           threadLocal.set(null);
        }
    }
}
