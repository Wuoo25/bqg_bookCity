package com.banyuan.web.opus;

import com.banyuan.bean.opus.TypeBean;
import com.banyuan.bean.user.ReaderBean;
import com.banyuan.message.ResponseData;
import com.banyuan.service.opus.TypeService;
import com.banyuan.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/type/get")
public class GetTypeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    private TypeService typeService = new TypeService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ResponseData data = new ResponseData();
        ReaderBean userBean = (ReaderBean) req.getSession().getAttribute("login");
        if (userBean == null) {
            data.setCode(-2);
            data.setMsg("未登录");
            WebUtils.writeToJson(resp, data);
            return;
        }

        TypeBean typeBean = WebUtils.copy(req, TypeBean.class);

        try {
            TypeBean bean = typeService.getTypeById(typeBean);

            data.setCode(1);
            data.setMsg("类型添加成功");
            data.setData(bean);

        } catch (Exception e) {
            e.printStackTrace();
            data.setCode(-1);
            data.setMsg("类型添加失败");
        }

        WebUtils.writeToJson(resp, data);
    }
}
