package com.clement.fruit.controller;

import com.clement.fruit.biz.fruitservice.FruitService;
import com.clement.fruit.pojo.Fruit;
import com.clement.fruit.utils.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
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
public class FruitController {
    private FruitService fruitService ;

    private String index(Integer pageNo, String oper, String keyword, HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        if (pageNo == null) {
            pageNo = 1;
        }
        if (StringUtil.isNotEmpty(oper) && "search".equals(oper)) {
            pageNo = 1;
            session.setAttribute("keyword", keyword);
        } else {
            Object keywordObj = session.getAttribute("keyword");
            if (keywordObj != null) {
                keyword = (String) keywordObj;
            } else {
                keyword = "";
            }
        }
        session.setAttribute("pageNo", pageNo);
        List<Fruit> fruitList = fruitService.getFruitList(keyword, pageNo);
        session.setAttribute("fruitList", fruitList);

        int pageCount = fruitService.getPageCount(keyword);
        session.setAttribute("pageCount", pageCount);
        return "index";
    }

    private String edit(Integer fid,HttpServletRequest request) throws ServletException {
        if (fid != null) {
            Fruit fruit = fruitService.getFruitByFid(fid);
            request.setAttribute("fruit",fruit);
            return "edit";
        }
        return "error";

    }

    private String add(String fName, BigDecimal price, Integer fCount, String remark) {
        fruitService.addFruit(new Fruit(0, fName, price, fCount, remark));
        return "redirect:fruit.do";
    }

    private String del(Integer fid) {
        if (fid != null) {
            fruitService.delFruit(fid);
            return "redirect:fruit.do";
        }
        return "error";
    }

    private String update(Integer fid, String fName, BigDecimal price, Integer fCount, String remark) {

        fruitService.updateFruit(new Fruit(fid, fName, price, fCount, remark));
        return "redirect:fruit.do";
    }


}
