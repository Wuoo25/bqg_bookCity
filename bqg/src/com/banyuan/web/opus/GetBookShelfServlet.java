package com.banyuan.web.opus;

import com.banyuan.bean.opus.BookShelfBean;
import com.banyuan.bean.user.ReaderBean;
import com.banyuan.message.ResponseData;
import com.banyuan.service.opus.BookShelfService;
import com.banyuan.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/bookshelf/getList")
public class GetBookShelfServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    private BookShelfService bookShelfService = new BookShelfService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ResponseData data = new ResponseData();

        ReaderBean readerBean = (ReaderBean) req.getSession().getAttribute("login");
        if (readerBean == null) {
            data.setCode(-2);
            data.setMsg("对不起,请先登录");
            WebUtils.writeToJson(resp, data);
            return;
        }

        BookShelfBean bookShelfBean = new BookShelfBean();
        bookShelfBean.setReaderId(readerBean.getId());
        try {
            List<BookShelfBean> list = bookShelfService.getList(bookShelfBean);

            data.setCode(1);
            data.setMsg("查询成功");
            data.setData(list);

        } catch (Exception e) {
            data.setCode(-1);
            data.setMsg("查询失败");
        }

        WebUtils.writeToJson(resp, data);

    }
}
