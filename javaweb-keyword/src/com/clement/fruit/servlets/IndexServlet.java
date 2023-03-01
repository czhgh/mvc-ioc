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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: clement
 * @Date: 2022年12月18日  21:37
 * @Description:
 */
@WebServlet("/index")
public class IndexServlet extends ViewBaseServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Integer pageNo = 1;
        String oper = request.getParameter("oper");
        String keyword = null;
        //通过表单提交过来的
        //此时，pageNo应该还原为1 ， keyword应该从请求参数中获取
        if(StringUtil.isNotEmpty(oper) && "search".equals(oper)){
            pageNo = 1;
            keyword = request.getParameter("keyword");
            if (StringUtil.isEmpty(keyword)) {
                keyword = "";
            }
            //保存在作用域里面
            session.setAttribute("keyword", keyword);
        }else {
            //通过上一页下一页地址栏跳转过来的
            //此时keyword应该从session作用域获取
            String pageNoStr = request.getParameter("pageNo");
            if (StringUtil.isNotEmpty(pageNoStr)) {
                pageNo = Integer.parseInt(pageNoStr);
            }
            Object keywordObj = session.getAttribute("keyword");
            if (keywordObj != null) {
                keyword = (String) keywordObj;
            } else {
                keyword = "";
            }

        }

        session.setAttribute("pageNo", pageNo);
        FruitDAO fruitDAO = new FruitDAOImpl();
        List<Fruit> fruitList = fruitDAO.getFruitList(keyword, pageNo);
        session.setAttribute("fruitList", fruitList);
        //总记录条数
        int fruitCount = fruitDAO.getFruitCount(keyword);
        //总页数
        int pageCount = (fruitCount + 3 - 1) / 3;
        session.setAttribute("pageCount", pageCount);
        //此处的视图名称是 index
        //那么thymeleaf会将这个 逻辑视图名称 对应到 物理视图 名称上去
        //逻辑视图名称 ：   index
        //物理视图名称 ：   view-prefix + 逻辑视图名称 + view-suffix
        //所以真实的视图名称是：      /       index       .html8967666`
        super.processTemplate("index", request, response);
    }
}