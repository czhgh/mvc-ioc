package com.clement.fruit.servlets;

import com.clement.fruit.dao.BookDao;
import com.clement.fruit.dao.impl.BookDaoImpl;
import com.clement.fruit.entity.BookEntity;
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
 * @Date: 2022??12??18??  21:37
 * @Description:
 */
@WebServlet("/index")
public class IndexServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fName = req.getParameter("fName");
        BookDao bookDao = new BookDaoImpl();
        List<BookEntity> list = null;
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
