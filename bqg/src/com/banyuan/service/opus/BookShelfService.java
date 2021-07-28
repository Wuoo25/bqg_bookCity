package com.banyuan.service.opus;

import com.banyuan.bean.opus.BookShelfBean;
import com.banyuan.dao.opus.BookShelfDao;

import java.util.List;

public class BookShelfService {

    private BookShelfDao bookShelfDao = new BookShelfDao();

    /**
     * 添加图书到书架
     * @param bookShelfBean
     * @return
     */
    public boolean insertBook(BookShelfBean bookShelfBean) {

        int i = bookShelfDao.insertBook(bookShelfBean);
        return i > 0;
    }

    /**
     * 根据书架id和读者id删除书架中的图书
     * @param bookShelfBean
     * @return
     */
    public boolean deleteBook(BookShelfBean bookShelfBean) {
        int i = bookShelfDao.deleteBook(bookShelfBean);
        return i > 0;
    }

    /**
     * 批量删除
     * @param bookShelfBean
     * @return
     */
    public boolean deleteBookBatch(BookShelfBean bookShelfBean) {
        if (bookShelfBean.getIds() == null || bookShelfBean.getIds().length == 0) {
            return false;
        }
        int i = bookShelfDao.deleteBookBatch(bookShelfBean);
        return i > 0;
    }

    /**
     * 根据读者id查询书架中的图书
     * @param bookShelfBean
     * @return
     */
    public List<BookShelfBean> getList(BookShelfBean bookShelfBean) {
        List<BookShelfBean> list = bookShelfDao.getBooks(bookShelfBean);
        return list;
    }
}
