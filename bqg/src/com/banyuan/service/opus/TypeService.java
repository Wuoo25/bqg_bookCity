package com.banyuan.service.opus;

import com.banyuan.bean.opus.TypeBean;
import com.banyuan.dao.opus.TypeDao;
import com.banyuan.message.PageData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TypeService {

    private TypeDao typeDao = new TypeDao();
    /**
     * 插入作品分类信息
     * @param typeBean
     * @return
     */
    public boolean insertType(TypeBean typeBean) {

        if (typeBean.getName() == null || "".equals(typeBean.getName())
                || typeBean.getIntro() == null || "".equals(typeBean.getIntro())) {
            return false;
        }
        //获取当前的系统时间,作为类型信息的创建和更新时间
        typeBean.setCreateTime(System.currentTimeMillis());
        typeBean.setUpdateTime(System.currentTimeMillis());

        int i = typeDao.insertType(typeBean);
        return i > 0;
    }

    /**
     * 查询所有的分类信息, 转换时间格式
     * @param typeBean
     * @return
     */
    public PageData getTypeList(TypeBean typeBean){

        List<TypeBean> list = typeDao.getList(typeBean);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //遍历所有的类型信息, 将创建时间和修改时间转换成固定格式的字符串
        for (TypeBean bean : list) {
            String datetime = sdf.format(new Date(bean.getCreateTime()));
            bean.setCreateDate(datetime);
            datetime = sdf.format(new Date(bean.getUpdateTime()));
            bean.setUpdateDate(datetime);
        }

        //查询数量
        int count =  typeDao.getListCount();

        PageData pageData = new PageData();
        pageData.setPageNo(typeBean.getPageNo());
        pageData.setPageSize(typeBean.getPageSize());
        pageData.setCount(count);
        pageData.setList(list);
        return pageData;
    }

    /**
     * 根据id删除类型
     * @param typeBean
     * @return
     */
    public boolean deleteType(TypeBean typeBean) {
        int i = typeDao.deleteType(typeBean);;
        return i > 0;
    }

    /**
     * 根据id修改类型信息
     * @param typeBean
     * @return
     */
    public boolean UpdateType(TypeBean typeBean) {
        typeBean.setUpdateTime(System.currentTimeMillis());
        int i = typeDao.updateType(typeBean);;
        return i > 0;
    }

    /**
     * 根据id查询类型信息
     * @param typeBean
     * @return
     */
    public TypeBean getTypeById(TypeBean typeBean) {

        TypeBean bean = typeDao.getById(typeBean);;
        return bean ;
    }

    /**
     * 获取所有的类型信息 id, name
     * @return
     */
    public List<TypeBean> getTypeListAll(){

        List<TypeBean> list = typeDao.getListALl();

        return list;
    }
}
