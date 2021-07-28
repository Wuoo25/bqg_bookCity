package com.w.Web.opus;


import com.w.bean.opus.OpusBean;
import com.w.bean.user.UserBean;
import com.w.message.ResponseData;
import com.w.service.opus.OpusService;
import com.w.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/opus/insert")
public class InsertOpusServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    private OpusService opusService = new OpusService();
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

        OpusBean opusBean = WebUtils.copy(req, OpusBean.class);
        opusBean.setUserId(bean.getId());

        try {
            boolean flg = opusService.insertOpus(opusBean);
            if (flg) {
                data.setCode(1);
                data.setMsg("作品添加成功");
            }else{
                data.setCode(-1);
                data.setMsg("作品添加失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            data.setCode(-1);
            data.setMsg("作品添加失败");
        }

        WebUtils.writeToJson(resp, data);
    }
}
