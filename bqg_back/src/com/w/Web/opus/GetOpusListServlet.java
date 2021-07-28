package com.w.Web.opus;


import com.w.bean.opus.OpusBean;
import com.w.bean.user.UserBean;
import com.w.message.ResponseData;
import com.w.service.opus.OpusService;
import com.w.utils.WebUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/opus/getList")
public class GetOpusListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        doPost(req, resp);
    }

    private OpusService opusService=new OpusService();
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
            OpusBean opusBean=new OpusBean();
            opusBean.setUserId(bean.getId());

            List<OpusBean> list = opusService.getList(opusBean);
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
