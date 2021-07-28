package com.w.Web.Chapter;
import com.w.bean.Chapter.ChapterBean;
import com.w.bean.user.UserBean;
import com.w.message.ResponseData;
import com.w.service.Chapter.ChapterService;
import com.w.utils.WebUtils;

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
        UserBean userBean = (UserBean) req.getSession().getAttribute("login");
        if (userBean == null) {
            data.setCode(-2);
            data.setMsg("未登录");
            WebUtils.writeToJson(resp, data);
            return;
        }

        ChapterBean chapterBean = WebUtils.copy(req, ChapterBean.class);

        try {
            ChapterBean bean = chapterService.getById(chapterBean);

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
