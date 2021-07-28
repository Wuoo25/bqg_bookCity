package com.banyuan.service.user;

import com.banyuan.bean.user.ReaderBean;
import com.banyuan.dao.user.ReaderDao;
import com.banyuan.message.ResponseData;
import com.banyuan.utils.SecurityUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

public class ReaderService {
    private ReaderDao readerDao = new ReaderDao();

    /**
     * 用户登录
     * @param readerBean
     * @param req
     * @return
     */
    public ResponseData login(ReaderBean readerBean, HttpServletRequest req) {
        ResponseData rd = new ResponseData();

        //检车用户密码是否合规
        if (readerBean.getUserName() == null || "".equals(readerBean.getUserName())
                || readerBean.getPassword() == null || "".equals(readerBean.getPassword())) {
            rd.setCode(-1);
            rd.setMsg("对不起,用户名密码不能为空");
            return rd;
        }

        ReaderBean bean = readerDao.getByUserName(readerBean);
        if (bean == null) {
            rd.setCode(-1);
            rd.setMsg("对不起,用户名不存在");
            return rd;
        }
        //对密码进行加密,
        String newPassword = SecurityUtils.md5(readerBean.getPassword());
        //将加密之后的密码和数据库中存储的密码比较
        if (!bean.getPassword().equals(newPassword)) {
            rd.setCode(-1);
            rd.setMsg("对不起,用户名密码错误");
            return rd;
        }

        req.getSession().setAttribute("login", bean);
        rd.setCode(1);
        rd.setMsg("登录成功");
        return rd;
    }

    /**
     * 读者注册, 对密码进行加密
     * @param readerBean
     * @return
     */
    public boolean register(ReaderBean readerBean) {
        
        //检车用户密码是否合规
        if (readerBean.getUserName() == null || "".equals(readerBean.getUserName())
                || readerBean.getPassword() == null || "".equals(readerBean.getPassword())) {
            return false;
        }
        if (!readerBean.getPassword().equals(readerBean.getRepassword())) {
            return false;
        }

        ReaderBean bean = readerDao.getByUserName(readerBean);
        if (bean != null) {
           return false;
        }
        //对密码进行加密,
        String newPassword = SecurityUtils.md5(readerBean.getPassword());
        readerBean.setPassword(newPassword);

        //生成昵称
        if (readerBean.getNickName() == null || "".equals(readerBean.getNickName())) {
            String nickName = "游客"+new Random().nextInt(10000);
            readerBean.setNickName(nickName);
        }
        int i = readerDao.insertReader(readerBean);
        return i > 0;

    }
    
}
