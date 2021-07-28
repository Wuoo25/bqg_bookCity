package com.w.service.Chapter;

import com.w.bean.Chapter.ChapterBean;
import com.w.bean.opus.OpusBean;
import com.w.dao.Chapter.ChapterDao;
import com.w.dao.opus.OpusDao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ChapterService {
    private ChapterDao chapterDao=new ChapterDao();

    /**
     * 插入作品分类信息
     * @param chapterBean
     * @return
     */
    public boolean insertChapter(ChapterBean chapterBean){

        //获取当前的系统时间,作为类型信息的创建和更新时间
        chapterBean.setCreateTime(System.currentTimeMillis());
        chapterBean.setUpdateTime(System.currentTimeMillis());

        int i = chapterDao.insertChapter(chapterBean);
        return i > 0;
    }

    /**
     * 获取作品id获得章节信息
     * @return
     */
    public List<ChapterBean> getList(ChapterBean chapterBean){

        List<ChapterBean> list = chapterDao.getByOpusId(chapterBean);

        return list;
    }

    /**
     * 根据id查询类型信息
     * @param chapterBean
     * @return
     */
    public ChapterBean getById(ChapterBean chapterBean) {

        ChapterBean bean = chapterDao.getById(chapterBean);

        return bean ;
    }


    /**
     *根据id删除作品
     * @param chapterBeann
     * @return
     */
    public boolean deleteChapter(ChapterBean chapterBeann){
        int i=chapterDao.deleteOpus(chapterBeann);
        return i>0;
    }
    /**
     *根据id修改作品
     * @param chapterBean
     * @return
     */
    public boolean UpdateChapter(ChapterBean chapterBean) {
        chapterBean.setUpdateTime(System.currentTimeMillis());
        int i = chapterDao.updateType(chapterBean);;
        return i > 0;
    }


}
