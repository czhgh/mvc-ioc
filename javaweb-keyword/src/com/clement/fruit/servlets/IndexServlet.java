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
 * @Date: 2022��12��18��  21:37
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
        //ͨ�����ύ������
        //��ʱ��pageNoӦ�û�ԭΪ1 �� keywordӦ�ô���������л�ȡ
        if(StringUtil.isNotEmpty(oper) && "search".equals(oper)){
            pageNo = 1;
            keyword = request.getParameter("keyword");
            if (StringUtil.isEmpty(keyword)) {
                keyword = "";
            }
            //����������������
            session.setAttribute("keyword", keyword);
        }else {
            //ͨ����һҳ��һҳ��ַ����ת������
            //��ʱkeywordӦ�ô�session�������ȡ
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
        //�ܼ�¼����
        int fruitCount = fruitDAO.getFruitCount(keyword);
        //��ҳ��
        int pageCount = (fruitCount + 3 - 1) / 3;
        session.setAttribute("pageCount", pageCount);
        //�˴�����ͼ������ index
        //��ôthymeleaf�Ὣ��� �߼���ͼ���� ��Ӧ�� ������ͼ ������ȥ
        //�߼���ͼ���� ��   index
        //������ͼ���� ��   view-prefix + �߼���ͼ���� + view-suffix
        //������ʵ����ͼ�����ǣ�      /       index       .html8967666`
        super.processTemplate("index", request, response);
    }
}