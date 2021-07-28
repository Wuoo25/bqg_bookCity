package com.w.dao.Chapter;

import com.w.bean.Chapter.ChapterBean;
import com.w.bean.opus.OpusBean;
import com.w.utils.DBUtils;
import com.w.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class ChapterDao {

    /**
     * 添加章节
     * @param chapterBean
     * @return
     */
    public int insertChapter(ChapterBean chapterBean){
        Connection cn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try {
            cn=JdbcUtils.getConnecton();
            ps=cn.prepareStatement("insert into chapters values(default ,?,?,?,?,?)");
            ps.setInt(1, chapterBean.getOpusId());
            ps.setString(2, chapterBean.getName());
            ps.setString(3, chapterBean.getContent());
            ps.setLong(4, chapterBean.getCreateTime());
            ps.setLong(5, chapterBean.getUpdateTime());

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
     * 查询所有的章节信息
     * @param chapterBean
     * @return
     */
    public List<ChapterBean> getByOpusId(ChapterBean chapterBean){
        Connection cn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try {
            cn= JdbcUtils.getConnecton();
            //使用左连接查询，将两个表相结合进行查询
            String sql= "select id,name from chapters where opusId=? order by createTime ";
            if(chapterBean.getOrder()==null){
                sql += "asc";
            }else {
                sql+=chapterBean.getOrder();
            }
            ps=cn.prepareStatement(sql);
            ps.setInt(1,chapterBean.getOpusId());
            rs=ps.executeQuery();
            return DBUtils.getBeanList(rs,ChapterBean.class);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtils.release(rs,ps,cn);
        }
        return null;
    }

    /**
     * 根据类型id获取作品信息
     * @param chapterBean
     * @return
     */
    public ChapterBean getById(ChapterBean chapterBean) {

        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = JdbcUtils.getConnecton();
            ps = cn.prepareStatement("select id,name ,content from chapters where id=?");
            ps.setInt(1, chapterBean.getId());
            rs = ps.executeQuery();
            return DBUtils.getBean(rs, ChapterBean.class);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.release(rs, ps, cn);
        }
        return null;
    }

    /**
     * 根据id删除作品
     * @param
     * @return
     */
    public int deleteOpus(ChapterBean chapterBean){
        Connection cn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try {
            cn=JdbcUtils.getConnecton();
            ps=cn.prepareStatement("delete from chapters where id=?");
            ps.setInt(1,chapterBean.getId());


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
     * @param chapterBean
     * @return
     */
    public int updateType(ChapterBean chapterBean) {

        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = JdbcUtils.getConnecton();
            ps = cn.prepareStatement("update chapters set name=?, content=?,updateTime=? where id=?");
            ps.setString(1,chapterBean.getName());
            ps.setString(2,chapterBean.getContent());
            ps.setLong(3,chapterBean.getUpdateTime());
            ps.setInt(4,chapterBean.getId());

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
