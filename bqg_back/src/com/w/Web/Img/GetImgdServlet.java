package com.w.Web.Img;

import com.w.bean.user.UserBean;
import com.w.message.ResponseData;
import com.w.service.type.TypeService;
import com.w.utils.WebUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

@WebServlet("/img/getImg")
public class GetImgdServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    private TypeService typeService = new TypeService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        ResponseData data = new ResponseData();
        UserBean bean = (UserBean) req.getSession().getAttribute("login");
        if (bean == null) {
            data.setCode(-2);
            data.setMsg("未登录");
            WebUtils.writeToJson(resp, data);
            return;
        }

        String imgName = req.getParameter("imgName");

        try {
            IOUtils.copy(new FileInputStream("C:/Users/Administrator/Desktop/imgs/" + imgName), resp.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            data.setCode(-1);
            data.setMsg("对不起, 偷我图片");
            WebUtils.writeToJson(resp, data);
        }

    }
}
