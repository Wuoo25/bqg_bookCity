package com.w.dao.opus;

import com.w.bean.opus.OpusBean;
import com.w.utils.DBUtils;
import com.w.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class OpusDao {

    /**
     * 查询所有的作品信息
     * @param opusBean
     * @return
     */
    public List<OpusBean> getList(OpusBean opusBean){
        Connection cn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try {
            cn= JdbcUtils.getConnecton();
            //使用左连接查询，将两个表相结合进行查询
            ps=cn.prepareStatement("select op.id,op.icon,op.name,t.name as typeName,op.intro,op.createTime,op.updateTime from opus op " +
                    "left join types t on t.id = op.typeId " +
                    "where userId=?");
            ps.setInt(1,opusBean.getUserId());

            rs=ps.executeQuery();
            return DBUtils.getBeanList(rs,OpusBean.class);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtils.release(rs,ps,cn);
        }
        return null;
    }

    /**
     * 根据类型id获取作品信息
     * @param opusBean
     * @return
     */
    public OpusBean getById(OpusBean opusBean) {

        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = JdbcUtils.getConnecton();
            ps = cn.prepareStatement("select op.* ,t.name as typeName from opus op " +
                    "left join types t on t.id = op.typeId" +
                    " where op.id=? and op.userId=?");
            ps.setInt(1, opusBean.getId());
            ps.setInt(2,opusBean.getUserId());
            rs = ps.executeQuery();
            return DBUtils.getBean(rs, OpusBean.class);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.release(rs, ps, cn);
        }
        return null;
    }


    /**
     * 添加作品
     * @param opusBean
     * @return
     */
    public int insertOpus(OpusBean opusBean){
        Connection cn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try {
            cn=JdbcUtils.getConnecton();
            ps=cn.prepareStatement("insert into opus values(default ,?,?,?,?,?,?,?)");
            ps.setInt(1, opusBean.getUserId());
            ps.setString(2, opusBean.getName());
            ps.setInt(3, opusBean.getTypeId());
            ps.setString(4, opusBean.getIntro());
            ps.setString(5, opusBean.getIcon());
            ps.setLong(6, opusBean.getCreateTime());
            ps.setLong(7, opusBean.getUpdateTime());

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
     * 根据id删除作品
     * @param
     * @return
     */
    public int deleteOpus(OpusBean opusBean){
        Connection cn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try {
            cn=JdbcUtils.getConnecton();
            ps=cn.prepareStatement("delete from opus where id=? and userId=?");
            ps.setInt(1,opusBean.getId());
            ps.setInt(2,opusBean.getUserId());

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
     * 根据id修改作品
     * @param opusBean
     * @return
     */
    public int updateType(OpusBean opusBean) {

        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = JdbcUtils.getConnecton();
            ps = cn.prepareStatement("update opus set name=? ,typeId=?,intro=? , icon=?,updateTime=? where id=? and userId=?");
            ps.setString(1,opusBean.getName());
            ps.setInt(2,opusBean.getTypeId());
            ps.setString(3,opusBean.getIntro());
            ps.setString(4,opusBean.getIcon());
            ps.setLong(5,opusBean.getUpdateTime());
            ps.setInt(6,opusBean.getId());
            ps.setInt(7,opusBean.getUserId());

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
