package com.banyuan.dao.user;

import com.banyuan.bean.user.ReaderBean;
import com.banyuan.utils.DBUtils;
import com.banyuan.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReaderDao {
    /**
     * 根据用户名获取用户信息
     * @param readerBean
     * @return
     */
    public ReaderBean getByUserName(ReaderBean readerBean) {

        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = JdbcUtils.getConnecton();
            ps = cn.prepareStatement("select * from readers where userName=?");
            ps.setString(1,readerBean.getUserName());
            rs = ps.executeQuery();
            return DBUtils.getBean(rs, ReaderBean.class);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.release(rs, ps, cn);
        }
        return null;
    }

    /**
     * 根据用户的id更新用户的昵称
     * @param readerBean
     * @return
     */
    public int insertReader(ReaderBean readerBean) {

        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = JdbcUtils.getConnecton();
            ps = cn.prepareStatement("insert into readers values(default ,?,?,?)");
            ps.setString(1, readerBean.getUserName());
            ps.setString(2,readerBean.getPassword());
            ps.setString(3, readerBean.getNickName());
            int i = ps.executeUpdate();
            return i;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.release(ps, cn);
        }
        return 0;
    }
}
