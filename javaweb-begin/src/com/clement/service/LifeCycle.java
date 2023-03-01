package com.clement.service;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: clement
 * @Date: 2022年12月17日  21:15
 * @Description:
 */
public class LifeCycle extends HttpServlet {

    public LifeCycle() {
        System.out.println("servlet实例化了");
    }

    @Override
    public void init() throws ServletException {
        System.out.println("servlet初始化了");
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        System.out.println("servlet在服务中");
    }

    @Override
    public void destroy() {
        System.out.println("servlet销毁了");
    }
}
