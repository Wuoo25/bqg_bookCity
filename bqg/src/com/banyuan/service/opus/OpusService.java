package com.banyuan.service.opus;

import com.banyuan.bean.opus.OpusBean;
import com.banyuan.bean.opus.TypeBean;
import com.banyuan.dao.opus.OpusDao;
import com.banyuan.dao.opus.TypeDao;
import com.banyuan.message.PageData;

import javax.swing.text.Style;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OpusService {

    private OpusDao opusDao = new OpusDao();
    private TypeDao typeDao = new TypeDao();
    /**
     * 根据条件分页查询作品
     * @param
     * @return
     */
    public PageData getList(OpusBean opusBean) {

        List<OpusBean> list = opusDao.getList(opusBean);
        int count = opusDao.getListCount(opusBean);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (OpusBean bean : list) {
            bean.setCreateDate(sdf.format(new Date(bean.getCreateTime())));
            bean.setUpdateDate(sdf.format(new Date(bean.getUpdateTime())));
        }

        PageData pageData = new PageData();
        pageData.setPageNo(opusBean.getPageNo());
        pageData.setPageSize(opusBean.getPageSize());
        pageData.setCount(count);
        pageData.setList(list);
        return pageData;
    }

    /**
     * 根据作品id和用户id查询作品信息
     * @param opusBean
     * @return
     */
    public OpusBean getById(OpusBean opusBean) {

        OpusBean bean = opusDao.getById(opusBean);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        bean.setCreateDate(sdf.format(new Date(bean.getCreateTime())));

        return bean;
    }
    //获取推荐的小说
    public List getRecommend() {
        List<List<OpusBean>> recommend = new ArrayList<>();
        //获取所有类型
        List<TypeBean> types = typeDao.getListALl();
        //遍历所有的类型,查询对应类型的两本作品
        types.forEach((type)->{
            OpusBean opusBean = new OpusBean();
            opusBean.setTypeId(type.getId());
            opusBean.setPageSize(2);
            List<OpusBean> list = opusDao.getList(opusBean);
            recommend.add(list);
        });

        return recommend;
    }
}
