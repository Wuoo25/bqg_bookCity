package com.banyuan.service.opus;

import com.banyuan.bean.opus.ChapterBean;
import com.banyuan.bean.opus.OpusBean;
import com.banyuan.dao.opus.ChapterDao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ChapterService {

    private ChapterDao chapterDao = new ChapterDao();

    /**
     * 添加章节信息
     * @param chapterBean
     * @return
     */
    public boolean insertChapter(ChapterBean chapterBean) {
        chapterBean.setCreateTime(System.currentTimeMillis());
        chapterBean.setUpdateTime(System.currentTimeMillis());

        int i = chapterDao.insertChapter(chapterBean);
        return i > 0;
    }

    /**
     * 根据作品id获取章节信息
     * @param chapterBean
     * @return
     */
    public List<ChapterBean> getList(ChapterBean chapterBean) {

        List<ChapterBean> list = chapterDao.getByOpusId(chapterBean);

        return list;
    }

    /**
     * 根据作品id和用户id查询作品信息
     * @param chapterBean
     * @return
     */
    public ChapterBean getById(ChapterBean chapterBean) {

        ChapterBean bean = chapterDao.getById(chapterBean);

        return bean;
    }

    /**
     * 根据作品id和用户id删除作品信息
     * @param chapterBean
     * @return
     */
    public boolean deleteChapter(ChapterBean chapterBean) {

        int i = chapterDao.deleteChapter(chapterBean);

        return i > 0;
    }

    /**
     * 根据id和用户id更新作品信息
     * @param chapterBean
     * @return
     */
    public boolean UpdateChapter(ChapterBean chapterBean) {

        chapterBean.setUpdateTime(System.currentTimeMillis());
        int i = chapterDao.UpdateChapter(chapterBean);

        return i > 0;
    }

}
