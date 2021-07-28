package com.banyuan.web.opus;

import com.banyuan.bean.opus.ChapterBean;
import com.banyuan.message.ResponseData;
import com.banyuan.service.opus.ChapterService;
import com.banyuan.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/chapter/get")
public class GetChapterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    private ChapterService chapterService = new ChapterService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ResponseData data = new ResponseData();

        ChapterBean opusBean = WebUtils.copy(req, ChapterBean.class);

        try {
            ChapterBean bean = chapterService.getById(opusBean);

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
