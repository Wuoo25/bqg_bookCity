package com.w.service.type;

import com.w.bean.Type.TypeBean;
import com.w.dao.type.TypeDao;
import com.w.message.PageDate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TypeService {
    private TypeDao typeDao=new TypeDao();

    /**
     * 插入作品分类信息
     * @param typeBean
     * @return
     */
    public boolean insertType(TypeBean typeBean){
        if(typeBean.getName()==null||"".equals(typeBean.getName())||
                typeBean.getIntro()==null||"".equals(typeBean.getIntro())){
            return false;
        }
        //获取当前的系统时间,作为类型信息的创建和更新时间
        typeBean.setCreateTime(System.currentTimeMillis());
        typeBean.setUpdateTime(System.currentTimeMillis());

        int i=typeDao.insertType(typeBean);
        return i>0;

    }

    /**
     * 查询分类信息转换时间格式
     * 返回pagedate，前端受到的是pagedate
     * @param typeBean
     * @return
     */
    public PageDate getTypeList(TypeBean typeBean){
        List<TypeBean> list= typeDao.getList(typeBean);

        //将long转为时间格式
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //迭代器循环快捷键：iter+tab
        //遍历类型信息转化两个时间为固定格式字符串
        for (TypeBean bean : list) {
            String dateTime=sdf.format(new Date(bean.getCreateTime()));
            bean.setCreateDate(dateTime);
             dateTime=sdf.format(new Date(bean.getUpdateTime()));
             bean.setUpdateDate(dateTime);

        }
        //查询数量
        int count =typeDao.getListCount();

        PageDate pageDate=new PageDate();
        pageDate.setPageNo(typeBean.getPageNo());
        pageDate.setPageSize(typeBean.getPageSize());
        pageDate.setCount(count);
        pageDate.setList(list);

        return pageDate;
    }

    /**
     *根据id删除类型
     * @param typeBean
     * @return
     */
    public boolean deleteType(TypeBean typeBean){
        int i=typeDao.deleteType(typeBean);
        return i>0;
    }
    /**
     *根据id修改类型
     * @param typeBean
     * @return
     */
    public boolean UpdateType(TypeBean typeBean) {
        typeBean.setUpdateTime(System.currentTimeMillis());
        int i = typeDao.updateType(typeBean);;
        return i > 0;
    }
    /**
     * 获取所有的类型信息 id, name
     * @return
     */
    public List<TypeBean> getTypeListAll(){

        List<TypeBean> list = typeDao.getListALl();

        return list;
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


}
