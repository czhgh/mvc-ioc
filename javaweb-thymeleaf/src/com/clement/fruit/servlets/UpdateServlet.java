package com.clement.fruit.servlets;

import com.clement.fruit.dao.FruitDao;
import com.clement.fruit.dao.impl.FruitDaoImpl;
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
 * @Date: 2022��12��19��  22:43
 * @Description:
 */
@WebServlet("/update.do")
public class UpdateServlet extends ViewBaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.���ñ���
        req.setCharacterEncoding("utf-8");
        //2.��ȡ����
        String fId = req.getParameter("fId");
        int fid = Integer.parseInt(fId);
        String fName = req.getParameter("fName");
        String price1 = req.getParameter("price");
        BigDecimal price = new BigDecimal(price1);
        String fCount = req.getParameter("fCount");
        int fcount = Integer.parseInt(fCount);
        String remark = req.getParameter("remark");
        //ִ���޸�
        FruitDao fruitDao = new FruitDaoImpl();
        try {
            fruitDao.updateFruit(new Fruit(fid,fName,price,fcount,remark));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //�ض���  ���޸ĺ����Դ�ٷ�һ������index���� ������ҳ����
        resp.sendRedirect("index");
    }
}
