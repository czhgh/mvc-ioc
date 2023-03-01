package com.clement.fruit.biz.fruitservice;

import com.clement.fruit.pojo.Fruit;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: clement
 * @Date: 2023年02月27日  12:03
 * @Description:
 */
public interface FruitService {
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
    int getPageCount(String keyword);
}
