package com.clement.myssm.myspringmvc;

import com.clement.fruit.utils.StringUtil;
import com.clement.myssm.io.BeanFactory;
import com.clement.myssm.io.ClassPathXmlApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.math.BigDecimal;
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

    private BeanFactory beanFactory;

    public DispatcherServlet() {

    }

    public void init() throws ServletException {
        super.init();
        beanFactory = new ClassPathXmlApplicationContext();

    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取请求路径  /fruit.do
        String servletPath = request.getServletPath();
        //截取 "/"  /fruit.do --> fruit.do
        servletPath = servletPath.substring(1);
        //截取 ".do"  fruit.do --> fruit
        int indexOf = servletPath.lastIndexOf(".do");
        servletPath = servletPath.substring(0, indexOf);

        Object controllerBeanObj = beanFactory.getBean(servletPath);// fruit   com.clement.fruit.controllers.FruitController
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
                                if ("java.lang.Integer".equals(typeName)) {
                                    parameterObj = Integer.parseInt(parameterValue);
                                } else if ("java.math.BigDecimal".equals(typeName)) {
                                    parameterObj = new BigDecimal(parameterValue);
                                }
                            }
                            parametersValues[i] = parameterObj;
                        }
                    }
                    method.setAccessible(true);
                    Object returnObj = method.invoke(controllerBeanObj, parametersValues);
                    LOGGER.info("returnObj:" + returnObj);
                    String methodReturnStr = (String) returnObj;
                    //3.视图处理
                    if (methodReturnStr.startsWith("redirect:")) {  //redirect:fruit.do
                        String redirectStr = methodReturnStr.substring("redirect:".length());
                        response.sendRedirect(redirectStr);
                    } else {
                        super.processTemplate(methodReturnStr, request, response); // "edit"
                    }
                } else {
                    throw new RuntimeException("运行时异常");
                }
            }

        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
