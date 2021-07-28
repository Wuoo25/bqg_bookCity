package com.w.dao.user;


import com.w.bean.user.UserBean;
import com.w.utils.DBUtils;
import com.w.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 第二步从dao开始写，从数据库功能往前写
 */
public class UserDao {

    /**
     *通过用户名获取用户信息
     * 查询语句返回对象，增删改返回行数int
     * @param userBean
     * @return
     */
    public UserBean getByUserName(UserBean userBean){
        Connection cn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try {
            cn=JdbcUtils.getConnecton();
            ps=cn.prepareStatement("select * from users where userName=?");
            ps.setString(1,userBean.getUserName());
            rs=ps.executeQuery();
            return DBUtils.getBean(rs,UserBean.class);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtils.release(rs,ps,cn);
        }
        return null;
    }

    /**
     * 根据id修改昵称
     * @param userBean
     * @return
     */
    public int updateNicknameById(UserBean userBean){
        Connection cn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try {
            cn=JdbcUtils.getConnecton();
            ps=cn.prepareStatement("update users set nickName=? where id=?");
            ps.setString(1,userBean.getNickName());
            ps.setInt(2,userBean.getId());
            int i=ps.executeUpdate();
            return i;

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtils.release(rs,ps,cn);
        }
        return 0;
    }
}
