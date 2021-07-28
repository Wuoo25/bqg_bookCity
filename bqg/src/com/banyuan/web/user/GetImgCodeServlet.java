package com.banyuan.web.user;

import cn.dsna.util.images.ValidateCode;
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

@WebServlet("/img/getImgCode")
public class GetImgCodeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    private ReaderService readerService = new ReaderService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ValidateCode validateCode = new ValidateCode(100, 30, 4, 200);

        String code = validateCode.getCode();
        req.getSession().setAttribute("imgCode", code);
        System.out.println(code);

        validateCode.write(resp.getOutputStream());
    }
}
