package com.banyuan.web.opus;

import com.banyuan.bean.opus.OpusBean;
import com.banyuan.message.ResponseData;
import com.banyuan.service.opus.OpusService;
import com.banyuan.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/opus/get")
public class GetOpusServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    private OpusService opusService = new OpusService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ResponseData data = new ResponseData();

        OpusBean opusBean = WebUtils.copy(req, OpusBean.class);

        try {
            OpusBean bean = opusService.getById(opusBean);

            data.setCode(1);
            data.setMsg("查询成功");
            data.setData(bean);

        } catch (Exception e) {
            e.printStackTrace();
            data.setCode(-1);
            data.setMsg("查询失败");
        }

        WebUtils.writeToJson(resp, data);
    }
}
