package com.w.dao.type;

import com.w.bean.Type.TypeBean;
import com.w.utils.DBUtils;
import com.w.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class TypeDao {

    /**
     * 查询所有的分类信息
     * @param typeBean
     * @return
     */
    public List<TypeBean> getList(TypeBean typeBean){
        Connection cn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try {
            cn= JdbcUtils.getConnecton();
            ps=cn.prepareStatement("select * from types limit ?, ?");
            ps.setInt(1,typeBean.getStart());
            ps.setInt(2,typeBean.getPageSize());

            rs=ps.executeQuery();
            return DBUtils.getBeanList(rs,TypeBean.class);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtils.release(rs,ps,cn);
        }
        return null;
    }

    /**
     * 查询类型表中的数，与getlist相互配合使用
     * @param
     * @return
     */
    public int getListCount(){
        Connection cn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try {
            cn= JdbcUtils.getConnecton();
            ps=cn.prepareStatement("select count(1) from types");

            rs=ps.executeQuery();
            rs.next();
            return rs.getInt(1);//查询数量返回单行单列，此句即返回第一字段

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtils.release(rs,ps,cn);
        }
        return 0;
    }

    /**
     * 添加类型
     * @param typeBean
     * @return
     */
    public int insertType(TypeBean typeBean){
        Connection cn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try {
            cn=JdbcUtils.getConnecton();
            ps=cn.prepareStatement("insert into types values(default ,?,?,?,?)");
            ps.setString(1,typeBean.getName());
            ps.setString(2,typeBean.getIntro());
            ps.setLong(3,typeBean.getCreateTime());
            ps.setLong(4,typeBean.getUpdateTime());
            int i=ps.executeUpdate();
            return i;

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtils.release(rs,ps,cn);
        }
        return 0;
    }

    /**
     * 根据id删除类型
     * @param typeBean
     * @return
     */
    public int deleteType(TypeBean typeBean){
        Connection cn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try {
            cn=JdbcUtils.getConnecton();
            ps=cn.prepareStatement("delete from types where id=?");
            ps.setInt(1,typeBean.getId());

            int i=ps.executeUpdate();
            return i;

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtils.release(rs,ps,cn);
        }
        return 0;
    }

    /**
     * 根据id修改类型
     * @param typeBean
     * @return
     */
    public int updateType(TypeBean typeBean) {

        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = JdbcUtils.getConnecton();
            ps = cn.prepareStatement("update types set name=? ,intro=? , updateTime=? where id=?");
            ps.setString(1, typeBean.getName());
            ps.setString(2, typeBean.getIntro());
            ps.setLong(3, typeBean.getUpdateTime());
            ps.setInt(4,typeBean.getId());

            int i = ps.executeUpdate();
            return i;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.release(ps, cn);
        }
        return 0;
    }

    /**
     * 获取所有的类型信息
     * @param
     * @return
     */
    public List<TypeBean> getListALl() {

        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = JdbcUtils.getConnecton();
            ps = cn.prepareStatement("select id,name from types ");

            rs = ps.executeQuery();
            return DBUtils.getBeanList(rs, TypeBean.class);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.release(rs, ps, cn);
        }
        return null;
    }

    /**
     * 根据类型id获取类型信息
     * @param typeBean
     * @return
     */
    public TypeBean getById(TypeBean typeBean) {

        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = JdbcUtils.getConnecton();
            ps = cn.prepareStatement("select * from types where id =? ");
            ps.setInt(1, typeBean.getId());

            rs = ps.executeQuery();
            return DBUtils.getBean(rs, TypeBean.class);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.release(rs, ps, cn);
        }
        return null;
    }
}
