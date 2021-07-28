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

@WebServlet("/user/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }
    private ReaderService readerService = new ReaderService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ResponseData rd = new ResponseData();
        //将request中的数据,映射到一个UserBean对象中
        ReaderBean readerBean = WebUtils.copy(req, ReaderBean.class);

        boolean flg = readerService.register(readerBean);
        if (flg) {
            rd.setCode(1);
            rd.setMsg("注册成功");
        }else{
            rd.setCode(-1);
            rd.setMsg("注册失败");
        }
        WebUtils.writeToJson(resp, rd);

    }
}
