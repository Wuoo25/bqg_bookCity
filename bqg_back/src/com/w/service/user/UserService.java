package com.w.service.user;

import com.w.bean.user.UserBean;
import com.w.dao.user.UserDao;
import com.w.message.ResponseData;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * 第三步，写Service，引用Dao和req和后端交互，把值返回给responseData
 */
public class UserService {
    UserDao userDao=new UserDao();

    /**
     * 用户登录
     * @param userBean
     * @param req
     * @return
     */
    public ResponseData login(UserBean userBean, HttpServletRequest req){

        ResponseData rd=new ResponseData();

        //检查用户名密码是否合规
        if(userBean.getUserName()==null||"".equals(userBean.getUserName())||
                userBean.getPassword()==null||"".equals(userBean.getPassword())){
            rd.setCode(-1);
            rd.setMsg("对不起，用户名密码不能为空");
            return rd;
        }
        UserBean bean=userDao.getByUserName(userBean);
        if(bean==null){
            rd.setCode(-1);
            rd.setMsg("对不起，用户名密码不存在");
            return rd;
        }
        //对用户密码进行加密再对比
        MessageDigest messageDigest=null;
        try {
            messageDigest=MessageDigest.getInstance("md5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //加密时要先转为字节
        byte[] bs=messageDigest.digest(userBean.getPassword().getBytes());
        //base64可以将字节转为 字符进行比较
        String newStr= Base64.getEncoder().encodeToString(bs);

        if(!bean.getPassword().equals(newStr)){
            rd.setCode(-1);
            rd.setMsg("对不起，用户名密码错误");
            return rd;
        }
        req.getSession().setAttribute("login",bean);
        rd.setCode(1);
        rd.setMsg("登录成功");
        return rd;

    }

    /**
     * 根据id修改昵称
     * @param userBean
     * @return
     */
    public boolean updateNickName(UserBean userBean){
        if (userBean.getNickName()==null||"".equals(userBean.getNickName())){
            return false;
        }
       int  i= userDao.updateNicknameById(userBean);
        return i>0;
    }


    /**
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String str = "ee";

        MessageDigest messageDigest = MessageDigest.getInstance("md5");
        //bs : 加密过之后的字节数组
        byte[] bs = messageDigest.digest(str.getBytes());

        System.out.println(new String(bs));
        //需要将加密之后的字节数组转成字符串
        String newStr = Base64.getEncoder().encodeToString(bs);
        System.out.println(newStr);


        // abc -> [97,98,99]
        // md5 -> [101,102,103] -> ???
        // 011001 010110  011001 100111
        // ab -> [97,98]
        // md5 -> [101,102] -> ???
        // 011001 010110  011000 =
    }*/
}
