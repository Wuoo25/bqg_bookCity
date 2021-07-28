package com.w.Web.opus;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/opus/toOpus")
public class ToOpusServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //登陆验证否则直接跳转登陆页面
        Object object=req.getSession().getAttribute("login");
        if(object==null){
            resp.sendRedirect("/bqg_back/user/toLogin");
            return;
        }

        //转发到主页面
        req.getRequestDispatcher("/WEB-INF/pages/opus.jsp").forward(req,resp);
    }
}
