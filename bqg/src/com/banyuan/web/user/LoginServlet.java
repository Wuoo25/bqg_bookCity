package com.banyuan.web.user;

import com.banyuan.bean.user.ReaderBean;
import com.banyuan.message.ResponseData;
import com.banyuan.service.user.ReaderService;
import com.banyuan.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    private ReaderService readerService = new ReaderService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ReaderBean readerBean = WebUtils.copy(req, ReaderBean.class);

        ResponseData data = readerService.login(readerBean, req);

        WebUtils.writeToJson(resp, data);

    }
}
