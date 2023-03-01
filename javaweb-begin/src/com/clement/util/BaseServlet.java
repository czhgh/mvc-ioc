package com.clement.util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: clement
 * @Date: 2022年12月16日  9:51
 * @Description:
 */
public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        String method = req.getParameter("method");
        System.out.println("调用了：" + method);
        System.out.println("this:"+this);
        Class<? extends BaseServlet> aclass = this.getClass();// 拿到字节码
        try {
            Method method2 = aclass.getMethod(method, HttpServletRequest.class,
                    HttpServletResponse.class);// 找到方法
            Object invoke = method2.invoke(this, req, resp);
            if (invoke != null) {
                req.getRequestDispatcher("/"+(String) invoke).forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
