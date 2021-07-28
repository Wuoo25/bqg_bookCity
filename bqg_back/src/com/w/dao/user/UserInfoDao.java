package com.w.dao.user;


import com.w.bean.user.UserInfoBean;
import com.w.utils.DBUtils;
import com.w.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 第二步从dao开始写，从数据库功能往前写
 */
public class UserInfoDao {

    /**
     *通过用户id获取用户信息
     * 查询语句返回对象，增删改返回行数int
     * @param infoBean
     * @return
     */
    public UserInfoBean getByUserId(UserInfoBean infoBean){
        Connection cn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try {
            cn=JdbcUtils.getConnecton();
            ps=cn.prepareStatement("select * from userinfo where userId=?");
            ps.setInt(1,infoBean.getUserId());
            rs=ps.executeQuery();
            return DBUtils.getBean(rs,UserInfoBean.class);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtils.release(rs,ps,cn);
        }
        return null;
    }



    /**
     * 更新详情
     * @param infoBean
     * @return
     */
    public int updateInfo(UserInfoBean infoBean){
        Connection cn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            cn=JdbcUtils.getConnecton();
            ps=cn.prepareStatement("update userinfo set cellphone=?,sex=?,birth=?,intro=? where userId=?");
            ps.setString(1,infoBean.getCellphone());
            ps.setInt(2, infoBean.getSex());
            ps.setDate(3, infoBean.getBirth());
            ps.setString(4, infoBean.getIntro());
            ps.setInt(5, infoBean.getUserId());
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
