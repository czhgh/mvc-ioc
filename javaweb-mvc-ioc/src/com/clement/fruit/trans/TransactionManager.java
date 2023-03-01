package com.clement.fruit.trans;

import com.clement.fruit.utils.ConnUtil;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: clement
 * @Date: 2023年02月27日  20:48
 * @Description:
 */
public class TransactionManager {

    //开启事务
    public static void beginTrans() throws SQLException {
        ConnUtil.getConn().setAutoCommit(false);
    }

    //提交事务
    public static void commitTrans() throws SQLException {
        Connection conn =  ConnUtil.getConn();
        conn.commit();
        ConnUtil.CloseConn();
    }

    //关闭事务
    public static void rollbackTrans() throws SQLException {
        Connection conn =  ConnUtil.getConn();
        conn.rollback();
        ConnUtil.CloseConn();
    }
}
