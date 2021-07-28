package com.w.Web.user;
import com.w.bean.user.UserBean;
import com.w.bean.user.UserInfoBean;
import com.w.message.ResponseData;
import com.w.service.user.UserInfoService;
import com.w.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/userInfo/update")
public class UpdateInfoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    private UserInfoService infoService = new UserInfoService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ResponseData data = new ResponseData();
        UserBean bean = (UserBean) req.getSession().getAttribute("login");
        if (bean == null) {
            data.setCode(-2);
            data.setMsg("未登录");
            WebUtils.writeToJson(resp, data);
            return;
        }

        //接收数据
        UserInfoBean infoBean = WebUtils.copy(req, UserInfoBean.class);
        infoBean.setUserId(bean.getId());

        try {
            boolean flg = infoService.updateUserInfo(infoBean);

            if (flg) {
                data.setCode(1);
                data.setMsg("更新成功");
            }else{
                data.setCode(1);
                data.setMsg("更新失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            data.setCode(-1);
            data.setMsg("更新失败");
        }

        WebUtils.writeToJson(resp, data);
    }
}
