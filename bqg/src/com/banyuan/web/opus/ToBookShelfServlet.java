package com.banyuan.web.opus;

import com.banyuan.bean.user.ReaderBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/bookshelf/toBookShelf")
public class ToBookShelfServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ReaderBean readerBean = (ReaderBean) req.getSession().getAttribute("login");
        if (readerBean == null) {
            resp.sendRedirect("/bqg/user/toLogin");
            return;
        }
        //转发到主页面
        req.getRequestDispatcher("/WEB-INF/pages/bookshelf.jsp").forward(req, resp);
    }
}
