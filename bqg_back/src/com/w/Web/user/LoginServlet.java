package com.w.Web.user;


import com.w.bean.user.UserBean;
import com.w.message.ResponseData;
import com.w.service.user.UserService;
import com.w.utils.WebUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * servlet单纯面向前端收数据，面向后端收data，转成json
 */
@WebServlet("/user/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        doPost(req, resp);
    }

    private UserService userService =new UserService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){

        UserBean userBean= WebUtils.copy(req,UserBean.class);
        ResponseData data= userService.login(userBean,req);

        WebUtils.writeToJson(resp,data);
    }
}
