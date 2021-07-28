package com.w.Web.type;


import com.w.bean.Type.TypeBean;
import com.w.bean.user.UserBean;
import com.w.message.ResponseData;
import com.w.service.type.TypeService;
import com.w.utils.WebUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/type/delete")
public class DeleteTypesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        doPost(req, resp);
    }

    private TypeService typeService =new TypeService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        ResponseData data=new ResponseData();
        //判断登录信息是否为空
        UserBean bean=(UserBean)req.getSession().getAttribute("login");
        if(bean==null){
            data.setCode(-2);
            data.setMsg("未登录");
            WebUtils.writeToJson(resp,data);
            return;
        }

        TypeBean typeBean= WebUtils.copy(req,TypeBean.class);
        try {
            //不论是否成功，一定会返回值
            boolean flg= typeService.deleteType(typeBean);
            if(flg){
                data.setCode(1);
                data.setMsg("删除成功");
            }else {
                data.setCode(-1);
                data.setMsg("删除失败");
            }
            WebUtils.writeToJson(resp,data);
        }catch (Exception e){
            e.printStackTrace();
            data.setCode(-1);
            data.setMsg("删除失败");
        }
    }

}
