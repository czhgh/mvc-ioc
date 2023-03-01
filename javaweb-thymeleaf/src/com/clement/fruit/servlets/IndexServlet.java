package com.clement.fruit.servlets;

import com.clement.fruit.dao.FruitDao;
import com.clement.fruit.dao.impl.FruitDaoImpl;
import com.clement.fruit.entity.Fruit;
import com.clement.myssm.myspringmvc.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: clement
 * @Date: 2022Äê12ÔÂ18ÈÕ  21:37
 * @Description:
 */
@WebServlet("/index")
public class IndexServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String fName = req.getParameter("fName");
        FruitDao bookDao = new FruitDaoImpl();
        List<Fruit> list = null;
        try {
            list = bookDao.getAll(fName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpSession session = req.getSession();
        session.setAttribute("list", list);
        super.processTemplate("index", req, resp);
    }
}
