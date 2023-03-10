package com.clement.fruit.dao.impl;


import com.clement.fruit.dao.FruitDAO;
import com.clement.fruit.entity.Fruit;
import com.clement.myssm.basedao.BaseDAO;

import java.util.List;

public class FruitDAOImpl extends BaseDAO<Fruit> implements FruitDAO {
    @Override
    public List<Fruit> getFruitList(String keyword , Integer pageNo) {
        return super.executeQuery("select * from fruit where fname like ? or remark like ? limit ? , 3" ,"%"+keyword+"%","%"+keyword+"%", (pageNo-1)*3);
    }

    @Override
    public Fruit getFruitByFid(Integer fid) {
        return super.load("select * from fruit where fid = ? " , fid);
    }

    @Override
    public void updateFruit(Fruit fruit) {
        String sql = "update fruit set fname = ? , price = ? , fcount = ? , remark = ? where fid = ? " ;
        super.executeUpdate(sql,fruit.getFname(),fruit.getPrice(),fruit.getFcount(),fruit.getRemark(),fruit.getFid());
    }

    @Override
    public void delFruit(Integer fid) {
        super.executeUpdate("delete from fruit where fid = ? " , fid) ;
    }

    @Override
    public void addFruit(Fruit fruit) {
        String sql = "insert into fruit values(0,?,?,?,?)";
        super.executeUpdate(sql,fruit.getFname(),fruit.getPrice(),fruit.getFcount(),fruit.getRemark());
    }

    @Override
    public int getFruitCount(String keyword ) {
        return ((Long)super.executeComplexQuery("select count(*) from fruit where fname like ? or remark like ?" , "%"+keyword+"%","%"+keyword+"%")[0]).intValue();
    }
}
