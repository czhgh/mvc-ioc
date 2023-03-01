package com.clement.fruit.servlets;

import com.clement.fruit.dao.FruitDAO;

import com.clement.fruit.dao.impl.FruitDAOImpl;
import com.clement.fruit.entity.Fruit;
import com.clement.myssm.myspringmvc.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: clement
 * @Date: 2022年12月20日  11:57
 * @Description:
 */
@WebServlet("/doAdd.do")
public class DoAddServlet extends ViewBaseServlet {
    private FruitDAO fruitDAO = new FruitDAOImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String fName = req.getParameter("fName");
        String price1 = req.getParameter("price");
        BigDecimal price = new BigDecimal(price1);
        String fCount = req.getParameter("fCount");
        Integer fcount = Integer.parseInt(fCount);
        String remark = req.getParameter("remark");
        try {
            fruitDAO.addFruit(new Fruit(fName,price,fcount,remark));
        } catch (Exception e) {
            e.printStackTrace();
        }
      resp.sendRedirect("index");
    }
}
