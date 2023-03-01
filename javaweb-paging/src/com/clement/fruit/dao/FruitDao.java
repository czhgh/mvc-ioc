package com.clement.fruit.dao;


import com.clement.fruit.entity.Fruit;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: clement
 * @Date: 2022年12月16日  10:52
 * @Description:
 */
public interface FruitDao {
    List<Fruit> getAll(String name, Integer pageNo) throws SQLException;

    boolean addFruit(Fruit books)  ;

    boolean updateFruit(Fruit books)  ;

    Fruit getAllById(Integer id) throws SQLException;

    boolean deleteFruit(Integer id);

    int fruitCount( ) throws SQLException;
}
