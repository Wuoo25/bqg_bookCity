package com.w.service.opus;

import com.w.bean.opus.OpusBean;
import com.w.dao.opus.OpusDao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OpusService {
    private OpusDao opusDao=new OpusDao();

    /**
     * 插入作品分类信息
     * @param opusBean
     * @return
     */
    public boolean insertOpus(OpusBean opusBean){

        if (opusBean.getName() == null || "".equals(opusBean.getName())
                || opusBean.getIntro() == null || "".equals(opusBean.getIntro())) {
            return false;
        }
        //获取当前的系统时间,作为类型信息的创建和更新时间
        opusBean.setCreateTime(System.currentTimeMillis());
        opusBean.setUpdateTime(System.currentTimeMillis());

        int i = opusDao.insertOpus(opusBean);
        return i > 0;
    }

    /**
     *根据id删除作品
     * @param opusBean
     * @return
     */
    public boolean deleteOpus(OpusBean opusBean){
        int i=opusDao.deleteOpus(opusBean);
        return i>0;
    }
    /**
     *根据id修改作品
     * @param opusBean
     * @return
     */
    public boolean UpdateOpus(OpusBean opusBean) {
        opusBean.setUpdateTime(System.currentTimeMillis());
        int i = opusDao.updateType(opusBean);;
        return i > 0;
    }

    /**
     * 获取所有的类型信息 id, name
     * @return
     */
    public List<OpusBean> getList(OpusBean opusBean){

        List<OpusBean> list = opusDao.getList(opusBean);

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (OpusBean bean : list) {
            bean.setCreateDate(sdf.format(new Date(bean.getCreateTime())));
            bean.setUpdateDate(sdf.format(new Date(bean.getUpdateTime())));
        }

        return list;
    }

    /**
     * 根据id查询类型信息
     * @param opusBean
     * @return
     */
    public OpusBean getById(OpusBean opusBean) {

        OpusBean bean = opusDao.getById(opusBean);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        bean.setCreateDate(sdf.format(new Date(bean.getCreateTime())));
        return bean ;
    }

}
