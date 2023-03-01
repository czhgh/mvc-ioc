package com.clement.fruit.dao;


import com.clement.fruit.entity.Fruit;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: clement
 * @Date: 2022年12月16日  10:52
 * @Description:
 */
public interface FruitDao {
    List<Fruit> getAll(String name) throws Exception;

    boolean addFruit(Fruit books) throws Exception;

    boolean updateFruit(Fruit books) throws Exception;

    Fruit getAllById(Integer id) throws Exception;

    boolean deleteFruit(Integer id) throws Exception ;
}
