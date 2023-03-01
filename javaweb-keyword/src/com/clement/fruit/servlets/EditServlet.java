package com.clement.fruit.servlets;

import com.clement.fruit.dao.FruitDAO;
import com.clement.fruit.dao.impl.FruitDAOImpl;
import com.clement.fruit.entity.Fruit;
import com.clement.fruit.utils.StringUtil;
import com.clement.myssm.myspringmvc.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: clement
 * @Date: 2022Äê12ÔÂ19ÈÕ  21:17
 * @Description:
 */

@WebServlet("/edit")
public class EditServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fId = req.getParameter("fid");
        FruitDAO fruitDao = new FruitDAOImpl();

        if(StringUtil.isNotEmpty(fId)){
            int fid = Integer.parseInt(fId);
            Fruit fruit = null;
            try {
                 fruit = fruitDao.getFruitByFid(fid);
            } catch (Exception e) {
                e.printStackTrace();
            }
            req.setAttribute("fruit",fruit);
            super.processTemplate("edit",req,resp);
        }

    }
}
