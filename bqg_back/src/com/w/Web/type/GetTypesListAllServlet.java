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
import java.util.List;

@WebServlet("/type/getListAll")
public class GetTypesListAllServlet extends HttpServlet {
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

        try {

            List<TypeBean> list = typeService.getTypeListAll();
            data.setCode(1);
            data.setMsg("查询类型成功");
            data.setData(list);

            WebUtils.writeToJson(resp,data);
        }catch (Exception e){
            e.printStackTrace();
            data.setCode(-1);
            data.setMsg("查询类型失败");
        }
    }

}
