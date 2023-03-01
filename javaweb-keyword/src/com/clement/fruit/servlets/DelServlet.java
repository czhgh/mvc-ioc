package com.clement.fruit.servlets;

import com.clement.fruit.dao.FruitDAO;
import com.clement.fruit.dao.impl.FruitDAOImpl;
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
 * @Date:
 * @Description:
 */
@WebServlet("/delFruit.do")
public class DelServlet extends ViewBaseServlet {
    private FruitDAO fruitDAO = new FruitDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fId = req.getParameter("fId");
        if (StringUtil.isNotEmpty(fId)) {
            int fid = Integer.parseInt(fId);
            try {
                fruitDAO.delFruit(fid);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
       resp.sendRedirect("index");
    }
}
