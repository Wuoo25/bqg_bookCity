package com.banyuan.dao.opus;

import com.banyuan.bean.opus.OpusBean;
import com.banyuan.utils.DBUtils;
import com.banyuan.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class OpusDao {

    /**
     * 根据类型分页查询作品
     * @param opusBean
     * @return
     */
    public List<OpusBean> getList(OpusBean opusBean) {

        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = JdbcUtils.getConnecton();

            String sql = "select op.* ,t.name as typeName from opus op " +
                    " left join types t on t.id = op.typeId " +
                    "where 1=1 " ;
            //如果有类型条件, 拼接类型查询
            if (opusBean.getTypeId() != 0) {
                sql+= "and op.typeId=? ";
            }
            //如果有名称条件, 拼接名称条件
            if (opusBean.getName() != null) {
                sql+=" and op.name like '%" + opusBean.getName() + "%'";
            }
            sql+=" limit ? ,?";

            ps = cn.prepareStatement(sql);
            //因为查询条件的数量不固定, 所以参数的数量也不能固定
            //设置参数是的需要就不能写死
            int index = 1;
            if (opusBean.getTypeId() != 0) {
                ps.setInt(index++,opusBean.getTypeId());
            }
            ps.setInt(index++,opusBean.getStart());
            ps.setInt(index++,opusBean.getPageSize());

            rs = ps.executeQuery();

            return DBUtils.getBeanList(rs, OpusBean.class);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.release(rs, ps, cn);
        }
        return null;
    }

    /**
     * 根据条件获取作品的数量
     * @param opusBean
     * @return
     */
    public int getListCount(OpusBean opusBean) {

        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = JdbcUtils.getConnecton();

            String sql = "select count(1) from opus op " +
                    "where 1=1 " ;
            if (opusBean.getTypeId() != 0) {
                sql+= "and op.typeId=? ";
            }
            if (opusBean.getName() != null) {
                sql+=" and op.name like '%" + opusBean.getName() + "%'";
            }

            ps = cn.prepareStatement(sql);

            int index = 1;
            if (opusBean.getTypeId() != 0) {
                ps.setInt(index++,opusBean.getTypeId());
            }

            rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.release(rs, ps, cn);
        }
        return 0;
    }
    /**
     * 根据id查询作品信息
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
                    " where op.id=? ");
            ps.setInt(1,opusBean.getId());
            rs = ps.executeQuery();
            return DBUtils.getBean(rs, OpusBean.class);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.release(rs, ps, cn);
        }
        return null;
    }

}
