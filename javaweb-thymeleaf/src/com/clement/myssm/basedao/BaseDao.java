package com.clement.myssm.basedao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: clement
 * @Date: 2022年12月16日  8:37
 * @Description:
 */
public class BaseDao {
    static String driver = "";
    static String url = "";
    static String user = "";
    static String password = "";
    static {
        //--1.创建properties对象
        Properties prop = new Properties();
        //--2.加载配置文件(配置文件必须在src目录下)
        //--2.1将配置文件读取成io流
        InputStream stream = BaseDao.class.getClassLoader().getResourceAsStream("jdbc.properties");
        try {
            prop.load(stream);
            driver = prop.getProperty("jdbc.driverClass");
            url = prop.getProperty("jdbc.url");
            user = prop.getProperty("jdbc.user");
            password = prop.getProperty("jdbc.password");
        } catch ( IOException e) {
            e.printStackTrace();
        }
    }
    //通用数据库连接
    public static Connection getCon() {
        Connection conn = null;
        //加载驱动
        try {
            Class.forName(driver);
            //2.建立数据库对象
            try {
                conn = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                System.out.println("conn执行");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Class执行");
        }
        return conn;
    }

    //通用关闭
    public static void closeAll(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //通用增删改
    public static int excute(String sql, Object[] pas) {
        Connection conn = BaseDao.getCon();
        PreparedStatement ps = null;
        int j = 0;
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < pas.length; i++) {
                ps.setObject(i + 1, pas[i]);
            }
            j = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeAll(conn, ps, null);
        }
        return j;
    }

}
