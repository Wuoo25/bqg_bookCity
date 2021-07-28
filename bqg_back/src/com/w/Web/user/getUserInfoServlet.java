package com.w.Web.user;


import com.w.bean.user.UserBean;
import com.w.bean.user.UserInfoBean;
import com.w.message.ResponseData;
import com.w.service.user.UserInfoService;
import com.w.utils.WebUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/userInfo/getUserInfo")
public class getUserInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        doPost(req, resp);
    }

    private UserInfoService userInfoService =new UserInfoService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        ResponseData data=new ResponseData();
        //判断登录信息是否为空
        UserBean bean=(UserBean)req.getSession().getAttribute("login");
        if(bean==null){
            data.setCode(-2);
            data.setMsg("未登录");
            WebUtils.writeToJson(resp,data);
            return;
        }

        UserInfoBean infoBean= WebUtils.copy(req,UserInfoBean.class);
        infoBean.setUserId(bean.getId());

        try {
            UserInfoBean userInfoBean =userInfoService.getByUserId(infoBean);
            data.setCode(1);
            data.setMsg("个人信息查询成功");
            data.setData(userInfoBean);
        }catch (Exception e){
            e.printStackTrace();
            data.setCode(-1);
            data.setMsg("个人信息查询失败");
        }
        WebUtils.writeToJson(resp,data);
    }

}
