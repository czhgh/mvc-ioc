package com.clement.fruit.dao.impl;

import com.clement.fruit.dao.BookDao;
import com.clement.fruit.entity.BookEntity;
import com.clement.myssm.basedao.BaseDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: clement
 * @Date: 2022Äê12ÔÂ18ÈÕ  21:39
 * @Description:
 */
public class BookDaoImpl implements BookDao {
    @Override
    public List<BookEntity> getAll(String fName) throws Exception {
        Connection con = BaseDao.getCon();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<BookEntity> list = new ArrayList<BookEntity>();
        String sql = "select * from book";
        if (fName != null) {
            sql += " where fname like ? ";
        }
        ps = con.prepareStatement(sql);
        if (fName != null) {
            ps.setString(1, "%" + fName + "%");
        }
        rs = ps.executeQuery();
        while (rs.next()) {
            list.add(new BookEntity(rs.getInt("fId"),
                    rs.getString("fName"),
                    rs.getBigDecimal("price"),
                    rs.getInt("fCount"),
                    rs.getString("remark")));
        }
        return list;
    }

    @Override
    public boolean addBook(BookEntity b) throws Exception {
        String sql = "INSERT INTO book(fName,price,fCount,remark) VALUES(?,?,?,?)";
        Object[] pas = {b.getfName(), b.getPrice(), b.getfCount(), b.getRemark()};
        return BaseDao.excute(sql, pas)>0 ? true:false;
    }

    @Override
    public boolean updateBook(BookEntity b) throws Exception {
        String sql = "UPDATE book  set fName = ?, price = ?, fCount = ?, remark = ? where fId = ?";
        Object[] pas = {b.getfName(), b.getPrice(), b.getfCount(), b.getRemark()};
        return BaseDao.excute(sql, pas)>0 ? true:false;
    }

    @Override
    public BookEntity getAllById(Integer fid) throws Exception {
        Connection con = BaseDao.getCon();
        PreparedStatement ps = null;
        ResultSet rs = null;
        BookEntity b = null;
        ps = con.prepareStatement("select * from book where fId = ?");
        ps.setObject(1, fid);
        rs = ps.executeQuery();
        while (rs.next()) {
            b = new BookEntity(rs.getInt("fId"),
                    rs.getString("fName"),
                    rs.getBigDecimal("price"),
                    rs.getInt("fCount"),
                    rs.getString("remark"));
        }
        return b;
    }

    @Override
    public boolean deleteBook(Integer id) throws Exception {
        String sql = "delete from book where fId = ?";
        Object[] pas = {id};
        return BaseDao.excute(sql, pas)>0 ? true:false;
    }
}
