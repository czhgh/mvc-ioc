package com.clement.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: clement
 * @Date: 2022年12月18日  18:27
 * @Description:
 */
public class Demo03 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //System.out.println("这里是demo03，即将转发到demo04");
        //req.getRequestDispatcher("demo04").forward(req,resp);
        System.out.println("这里是demo03 即将重定向到demo04");
        resp.sendRedirect("demo04");
    }
}
