package com.w.Web.Img;

import com.w.bean.user.UserBean;
import com.w.message.ResponseData;
import com.w.service.type.TypeService;
import com.w.utils.WebUtils;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/img/upload")
public class ImgUploadServlet extends HttpServlet {

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
        //上传
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            FileUpload upload = new FileUpload(factory);
            List<FileItem> list = upload.parseRequest(new ServletRequestContext(req));

            FileItem item = list.get(0);

            String name = item.getName();
            String newName = UUID.randomUUID().toString() + System.currentTimeMillis() + name.substring(name.lastIndexOf("."));

            IOUtils.copy(item.getInputStream(), new FileOutputStream("C:/Users/Administrator/Desktop/imgs/" + newName));

            data.setCode(1);
            data.setMsg("上传成功");
            data.setData(newName);
        } catch (Exception e) {
            e.printStackTrace();
            data.setCode(-1);
            data.setMsg("上传失败");
        }

        WebUtils.writeToJson(resp, data);
    }
}
