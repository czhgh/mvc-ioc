package com.clement.fruit.filter;

import com.clement.fruit.utils.StringUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: clement
 * @Date: 2023年02月27日  15:09
 * @Description:
 */
@WebServlet(urlPatterns = {"*.do"},initParams = { @WebInitParam(name = "encoding",value = "utf-8")})
public class CharacterEncodeFilter implements Filter {

    private static String encode = "utf-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String encodingStr = filterConfig.getInitParameter("encoding");
        if(StringUtil.isNotEmpty(encodingStr)){
            encode = encodingStr;
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ((HttpServletRequest)servletRequest).setCharacterEncoding(encode);
           filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
