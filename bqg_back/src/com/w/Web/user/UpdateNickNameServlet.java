package com.w.Web.user;


import com.w.bean.user.UserBean;
import com.w.message.ResponseData;
import com.w.service.user.UserService;
import com.w.utils.WebUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/user/update")
public class UpdateNickNameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        doPost(req, resp);
    }

    private UserService userService =new UserService();
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

        UserBean userBean= WebUtils.copy(req,UserBean.class);
        userBean.setId(bean.getId());

        try {
            //不论是否成功，一定会返回值
            boolean flg= userService.updateNickName(userBean);
            if(flg){
                //更新session中的昵称
                bean.setNickName(userBean.getNickName());
                data.setCode(1);
                data.setMsg("修改成功");
            }else {
                data.setCode(-1);
                data.setMsg("修改失败");
            }
            WebUtils.writeToJson(resp,data);
        }catch (Exception e){
            e.printStackTrace();
            data.setCode(-1);
            data.setMsg("修改失败");
        }
    }

}
