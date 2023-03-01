package com.clement.fruit.dao.impl;

import com.clement.fruit.dao.FruitDao;
import com.clement.fruit.entity.Fruit;
import com.clement.myssm.basedao.BaseDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: clement
 * @Date: 2022Äê12ÔÂ18ÈÕ  21:39
 * @Description:
 */
public class FruitDaoImpl implements FruitDao {

    @Override
    public List<Fruit> getAll(String fName) throws Exception {
        Connection con = BaseDao.getCon();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Fruit> list = new ArrayList<Fruit>();
        String sql = "select * from fruit";
        if (fName != null) {
            sql += " where fname like ? ";
        }
        ps = con.prepareStatement(sql);
        if (fName != null) {
            ps.setString(1, "%" + fName + "%");
        }
        rs = ps.executeQuery();
        while (rs.next()) {
            list.add(new Fruit(rs.getInt("fId"),
                    rs.getString("fName"),
                    rs.getBigDecimal("price"),
                    rs.getInt("fCount"),
                    rs.getString("remark")));
        }
        return list;
    }

    @Override
    public boolean addFruit(Fruit b) throws Exception {
        String sql = "INSERT INTO fruit(fName,price,fCount,remark) VALUES(?,?,?,?)";
        Object[] pas = {b.getfName(), b.getPrice(), b.getfCount(), b.getRemark()};
        return BaseDao.excute(sql, pas)>0 ? true:false;
    }

    @Override
    public boolean updateFruit(Fruit b) throws Exception {
        String sql = "update fruit  set fname = ?, price = ?, fcount = ?, remark = ? where fid = ?";
        Object[] pas = {b.getfName(), b.getPrice(), b.getfCount(), b.getRemark(),b.getfId()};
        return BaseDao.excute(sql, pas)>0 ? true:false;
    }

    @Override
    public Fruit getAllById(Integer fid) throws Exception {
        Connection con = BaseDao.getCon();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Fruit b = null;
        ps = con.prepareStatement("select * from fruit where fId = ?");
        ps.setObject(1, fid);
        rs = ps.executeQuery();
        while (rs.next()) {
            b = new Fruit(rs.getInt("fId"),
                    rs.getString("fName"),
                    rs.getBigDecimal("price"),
                    rs.getInt("fCount"),
                    rs.getString("remark"));
        }
        return b;
    }

    @Override
    public boolean deleteFruit(Integer id) throws Exception {
        String sql = "delete from fruit where fId = ?";
        Object[] pas = {id};
        return BaseDao.excute(sql, pas)>0 ? true:false;
    }
}
