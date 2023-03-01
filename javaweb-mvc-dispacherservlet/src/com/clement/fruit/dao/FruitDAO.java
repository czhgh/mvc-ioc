package com.clement.fruit.dao;


import com.clement.fruit.pojo.Fruit;

import java.util.List;

public interface FruitDAO {
    /**
     *
     * @param keyword
     * @param pageNo
     * @return
     */
    List<Fruit> getFruitList(String keyword , Integer pageNo);

    /**
     *
     * @param fid
     * @return
     */
    Fruit getFruitByFid(Integer fid);

    /**
     *
     * @param fruit
     */
    void updateFruit(Fruit fruit );

    /**
     *
     * @param fid
     */
    void delFruit(Integer fid);

    /**
     *
     * @param fruit
     */
    void addFruit(Fruit fruit);

    /**
     *
     * @param keyword
     * @return
     */
    int getFruitCount(String keyword);
}
