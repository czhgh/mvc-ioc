package com.clement.fruit.biz.fruitservice.impl;

import com.clement.fruit.biz.fruitservice.FruitService;
import com.clement.fruit.dao.FruitDAO;
import com.clement.fruit.dao.impl.FruitDAOImpl;
import com.clement.fruit.pojo.Fruit;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: clement
 * @Date: 2023年02月27日  12:05
 * @Description:
 */
public class FruitServiceImpl implements FruitService {
       FruitDAO fruitDAO = new FruitDAOImpl();

    @Override
    public List<Fruit> getFruitList(String keyword, Integer pageNo) {
        List<Fruit> fruitList = fruitDAO.getFruitList(keyword, pageNo);
        return fruitList;
    }

    @Override
    public Fruit getFruitByFid(Integer fid) {
        Fruit fruit = fruitDAO.getFruitByFid(fid);
        return fruit;
    }

    @Override
    public void updateFruit(Fruit fruit) {
      fruitDAO.updateFruit(fruit);
    }

    @Override
    public void delFruit(Integer fid) {
        fruitDAO.delFruit(fid);
    }

    @Override
    public void addFruit(Fruit fruit) {
        fruitDAO.addFruit(fruit);
    }

    @Override
    public int getPageCount(String keyword) {
        int fruitCount = fruitDAO.getFruitCount(keyword);
        int pageCount = (fruitCount + 3 - 1) / 3;
        return pageCount;
    }
}
