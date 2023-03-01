package com.clement.myssm.myspringmvc;

import com.clement.fruit.utils.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: clement
 * @Date: 2023年02月26日  10:25
 * @Description:
 */
@WebServlet("*.do")
public class DispatcherServlet extends ViewBaseServlet {
    private static final Logger LOGGER = Logger.getLogger(DispatcherServlet.class.getName());
    private Map<String, Object> beanMap = new HashMap<>();

    public void init() throws ServletException {
        super.init();
        //读取XML配置文件
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("applicationContext.xml");
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(resourceAsStream);
            //获取到配置文件里面所有的bean的节点
            NodeList nodeList = document.getElementsByTagName("bean");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node beanNode = nodeList.item(i);
                if (beanNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element beanElement = (Element) beanNode;
                    //拿到bean节点里面的 id属性和class属性
                    String beanId = beanElement.getAttribute("id");
                    LOGGER.info("beanId:" + beanId);
                    //全路径类名
                    String className = beanElement.getAttribute("class");
                    //fruitController类型的Class的实例对象
                    Object beanObj = Class.forName(className).newInstance();
                    LOGGER.info("beanObj:" + beanObj);
                    beanMap.put(beanId, beanObj);
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        //获取请求路径  /fruit.do
        String servletPath = request.getServletPath();
        //截取 "/"  /fruit.do --> fruit.do
        servletPath = servletPath.substring(1);
        //截取 ".do"  fruit.do --> fruit
        int indexOf = servletPath.lastIndexOf(".do");
        servletPath = servletPath.substring(0, indexOf);

        Object controllerBeanObj = beanMap.get(servletPath);// fruit   com.clement.fruit.controllers.FruitController
        LOGGER.info("controllerBeanObj:" + controllerBeanObj);
        String operate = request.getParameter("operate");

        if (StringUtil.isEmpty(operate)) {
            operate = "index";
        }

        try {
            //1.controller组件中的方法调用
            Method[] methods = controllerBeanObj.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (operate.equals(method.getName())) {
                    //1.统一获取请求参数
                    //1-1获取当前方法的参数
                    Parameter[] parameters = method.getParameters();
                    //1.2同样大的一个数组
                    Object[] parametersValues = new Object[parameters.length];
                    for (int i = 0; i < parameters.length; i++) {
                        //1.3每一个不同的参数存起来
                        Parameter parameter = parameters[i];
                        //1.4获取参数名称
                        String parameterName = parameter.getName();
                        //1.5对参数做判断 request response session
                        if ("request".equals(parameterName)) {
                            parametersValues[i] = request;
                        } else if ("response".equals(parameterName)) {
                            parametersValues[i] = response;
                        } else if ("session".equals(parameterName)) {
                            parametersValues[i] = request.getSession();
                        } else {
                            //从请求中获取参数
                            String parameterValue = request.getParameter(parameterName);
                            String typeName = parameter.getType().getName();
                            Object parameterObj = parameterValue;
                            if (parameterObj != null) {
                                   if("java.lang.Integer".equals(typeName)){
                                       parameterObj = Integer.parseInt(parameterValue);
                                   }else if ("java.math.BigDecimal".equals(typeName)){
                                       parameterObj = new BigDecimal(parameterValue);
                                   }
                            }
                            parametersValues[i] = parameterObj;
                        }
                    }
                    method.setAccessible(true);
                    Object returnObj = method.invoke(controllerBeanObj, parametersValues);
                    LOGGER.info("returnObj:"+returnObj);
                    String methodReturnStr = (String) returnObj;
                    //3.视图处理
                    if (methodReturnStr.startsWith("redirect:")) {  //redirect:fruit.do
                        String redirectStr = methodReturnStr.substring("redirect:".length());
                        response.sendRedirect(redirectStr);
                    } else {
                        super.processTemplate(methodReturnStr, request, response); // "edit"
                    }
                } /*else {
                    throw new RuntimeException("运行时异常");
                }*/
            }

        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
