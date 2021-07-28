package com.banyuan.dao.opus;

import com.banyuan.bean.opus.BookShelfBean;
import com.banyuan.bean.opus.ChapterBean;
import com.banyuan.bean.user.ReaderBean;
import com.banyuan.utils.DBUtils;
import com.banyuan.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class BookShelfDao {

    public int insertBook(BookShelfBean bookShelfBean) {

        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = JdbcUtils.getConnecton();
            ps = cn.prepareStatement("insert into bookshelf values(default ,?,?)");
            ps.setInt(1, bookShelfBean.getReaderId());
            ps.setInt(2, bookShelfBean.getOpusId());

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
     * 根据图书id和用户di删除书架中的图书
     * @param bookShelfBean
     * @return
     */
    public int deleteBook(BookShelfBean bookShelfBean) {

        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = JdbcUtils.getConnecton();
            ps = cn.prepareStatement("delete from bookshelf where id = ? and readerId=?");

            ps.setInt(1, bookShelfBean.getId());
            ps.setInt(2, bookShelfBean.getReaderId());

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
     * 批量移除书架中的图书
     * @param bookShelfBean
     * @return
     */
    public int deleteBookBatch(BookShelfBean bookShelfBean) {

        Connection cn = null;
        PreparedStatement ps = null;

        try {
            cn = JdbcUtils.getConnecton();

            String sql = "delete from bookshelf where ";
            if (bookShelfBean.getIds() != null && bookShelfBean.getIds().length > 0) {
                sql += " id in (";
                int[] ids = bookShelfBean.getIds();
                for (int i = 0; i < ids.length; i++) {
                    if (i == ids.length - 1) {
                        sql += "?) ";
                    }else{
                        sql += "?,";
                    }
                }
            }
            sql += "and readerId=?";

            ps = cn.prepareStatement(sql);
            int index = 1;
            if (bookShelfBean.getIds() != null && bookShelfBean.getIds().length > 0) {
                sql += "id in (";
                int[] ids = bookShelfBean.getIds();
                for (int i = 0; i < ids.length; i++) {
                    ps.setInt(index++, ids[i]);
                }
            }
            ps.setInt(index++, bookShelfBean.getReaderId());

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
     * 根据读者的id查询书架中的所有图书
     * @param bookShelfBean
     * @return
     */
    public List<BookShelfBean> getBooks(BookShelfBean bookShelfBean) {

        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cn = JdbcUtils.getConnecton();
            String sql = "select bs.id,bs.opusId, op.name, op.icon  from bookshelf bs " +
                    "left join opus op on op.id = bs.opusId " +
                    " where readerId=?";

            ps = cn.prepareStatement(sql);
            ps.setInt(1,bookShelfBean.getReaderId());
            rs = ps.executeQuery();
            return DBUtils.getBeanList(rs, BookShelfBean.class);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.release(rs, ps, cn);
        }
        return null;
    }
}
