package com.clement.fruit.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: clement
 * @Date: 2022年12月16日  10:05
 * @Description:
 */
public class Fruit implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer fId;
    private String fName;
    private BigDecimal price;
    private Integer fCount;
    private String remark;

    public Integer getfId() {
        return fId;
    }

    public void setfId(Integer fId) {
        this.fId = fId;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getfCount() {
        return fCount;
    }

    public void setfCount(Integer fCount) {
        this.fCount = fCount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Fruit(Integer fId, String fName, BigDecimal price, Integer fCount, String remark) {
        this.fId = fId;
        this.fName = fName;
        this.price = price;
        this.fCount = fCount;
        this.remark = remark;
    }

    public Fruit(String fName, BigDecimal price, Integer fCount, String remark) {
        this.fName = fName;
        this.price = price;
        this.fCount = fCount;
        this.remark = remark;
    }

    public Fruit() {

    }

    @Override
    public String toString() {
        return "BookEntity{" +
                "fId=" + fId +
                ", fName='" + fName + '\'' +
                ", price=" + price +
                ", fCount=" + fCount +
                ", remark='" + remark + '\'' +
                '}';
    }
}
