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

@WebServlet("/bookshelf/delete")
public class DeleteBookServlet extends HttpServlet {

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
        BookShelfBean bookShelfBean = WebUtils.copy(req, BookShelfBean.class);
        bookShelfBean.setReaderId(readerBean.getId());
        try {
            boolean flg = bookShelfService.deleteBook(bookShelfBean);
            if (flg) {
                data.setCode(1);
                data.setMsg("添加成功");
            }else{
                data.setCode(-1);
                data.setMsg("添加失败");
            }
        } catch (Exception e) {
            data.setCode(-1);
            data.setMsg("添加失败");
        }

        WebUtils.writeToJson(resp, data);

    }
}
