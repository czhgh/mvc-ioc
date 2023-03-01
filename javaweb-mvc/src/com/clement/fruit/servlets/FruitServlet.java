package com.clement.fruit.servlets;

import com.clement.fruit.dao.FruitDAO;
import com.clement.fruit.dao.impl.FruitDAOImpl;
import com.clement.fruit.entity.Fruit;
import com.clement.fruit.utils.StringUtil;
import com.clement.myssm.myspringmvc.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: clement
 * @Date: 2023年02月25日  22:35
 * @Description:
 */
@WebServlet("/fruit.do")
public class FruitServlet extends ViewBaseServlet {
    private FruitDAO fruitDAO = new FruitDAOImpl();


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String operate = request.getParameter("operate");
        if(StringUtil.isEmpty(operate)){
            operate = "index" ;
        }

        switch(operate){
            case "index":
                index(request,response);
                break;
            case "add":
                add(request,response);
                break;
            case "del":
                del(request,response);
                break;
            case "edit":
                edit(request,response);
                break;
            case "update":
                update(request,response);
                break;
            default:
                throw new RuntimeException("operate值非法!");
        }
    }

    private void index(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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

    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String fName = req.getParameter("fName");
        String price1 = req.getParameter("price");
        BigDecimal price = new BigDecimal(price1);
        String fCount = req.getParameter("fCount");
        Integer fcount = Integer.parseInt(fCount);
        String remark = req.getParameter("remark");
        try {
            fruitDAO.addFruit(new Fruit(fName,price,fcount,remark));
        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.sendRedirect("/fruit.do");
    }

    private void del(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fId = req.getParameter("fid");
        if (StringUtil.isNotEmpty(fId)) {
            int fid = Integer.parseInt(fId);
            try {
                fruitDAO.delFruit(fid);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        resp.sendRedirect("/fruit.do");
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.设置编码
        req.setCharacterEncoding("utf-8");
        //2.获取参数
        HttpSession session = req.getSession();
        Object pageNo = session.getAttribute("pageNo");
        String fId = req.getParameter("fId");
        int fid = Integer.parseInt(fId);
        String fName = req.getParameter("fName");
        String price1 = req.getParameter("price");
        BigDecimal price = new BigDecimal(price1);
        String fCount = req.getParameter("fCount");
        int fcount = Integer.parseInt(fCount);
        String remark = req.getParameter("remark");
        //执行修改
        FruitDAO fruitDAO = new FruitDAOImpl();
        try {
            fruitDAO.updateFruit(new Fruit(fid,fName,price,fcount,remark));
            session.setAttribute("pageNo",pageNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //重定向  将修改后的资源再发一次请求到index请求 更改首页数据
        resp.sendRedirect("/fruit.do");
    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fId = req.getParameter("fid");
        FruitDAO fruitDao = new FruitDAOImpl();

        if(StringUtil.isNotEmpty(fId)){
            int fid = Integer.parseInt(fId);
            Fruit fruit = null;
            try {
                fruit = fruitDao.getFruitByFid(fid);
            } catch (Exception e) {
                e.printStackTrace();
            }
            req.setAttribute("fruit",fruit);
            super.processTemplate("edit",req,resp);
        }

    }
}
