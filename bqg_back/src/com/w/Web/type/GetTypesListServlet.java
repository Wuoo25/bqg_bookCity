package com.w.Web.type;


import com.w.bean.Type.TypeBean;
import com.w.bean.user.UserBean;
import com.w.message.PageDate;
import com.w.message.ResponseData;
import com.w.service.type.TypeService;
import com.w.utils.WebUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/type/getList")
public class GetTypesListServlet extends HttpServlet {
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

        //如果客户端传递一个pageNo名称的参数过来, 我们在接受的时候,会调用setPageNo这个方法
        //我们在setPageNo这个方法内计算分页的起始位置

        TypeBean typeBean= WebUtils.copy(req,TypeBean.class);
        try {
            //不论是否成功，一定会返回值
            PageDate pageDate=typeService.getTypeList(typeBean);
            data.setCode(1);
            data.setMsg("查询类型成功");
            data.setData(pageDate);

            WebUtils.writeToJson(resp,data);
        }catch (Exception e){
            e.printStackTrace();
            data.setCode(-1);
            data.setMsg("查询类型失败");
        }
    }

}
