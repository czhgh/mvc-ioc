package com.clement.service;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: clement
 * @Date: 2022年12月14日  19:08
 * @Description:
 */
public class AddServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Enumeration<String> headerNames = req.getHeaderNames();
        req.setCharacterEncoding("utf-8");
        String fname = req.getParameter("fname");
        String price = req.getParameter("price");
        String fcount = req.getParameter("fcount");
        String remark = req.getParameter("remark");
        System.out.println("结果打印："+(fname+"::"+price+"::"+fcount+"::"+remark));
    }
}
