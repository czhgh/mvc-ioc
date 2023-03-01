package com.clement.fruit.servlets;

import com.clement.fruit.dao.FruitDao;
import com.clement.fruit.dao.impl.FruitDaoImpl;
import com.clement.fruit.entity.Fruit;
import com.clement.fruit.utils.StringUtil;
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
        HttpSession session = req.getSession();
        String fName = req.getParameter("fName");
        String pageNoStr = req.getParameter("pageNo");
        int pageNo = 1;
        if (StringUtil.isNotEmpty(pageNoStr)) {
            pageNo = Integer.parseInt(pageNoStr);
        }
        FruitDao fruitDao = new FruitDaoImpl();
        List<Fruit> list = null;
        int fruitCount = 0;
        try {
            list = fruitDao.getAll(fName, pageNo);
            session.setAttribute("list", list);
            fruitCount = fruitDao.fruitCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
        session.setAttribute("pageNo", pageNo);
        int pageCount = (fruitCount + 3 - 1) / 3;
        session.setAttribute("pageCount", pageCount);
        super.processTemplate("index", req, resp);
    }
}
