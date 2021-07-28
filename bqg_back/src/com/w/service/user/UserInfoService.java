package com.w.service.user;

import com.w.bean.user.UserInfoBean;
import com.w.dao.user.UserInfoDao;

public class UserInfoService {
    UserInfoDao userInfoDao=new UserInfoDao();

    /**
     *根据用户的id查询用户的详情信息
     * @param userInfoBean
     * @return
     */
    public UserInfoBean getByUserId(UserInfoBean userInfoBean){
        UserInfoBean bean=userInfoDao.getByUserId(userInfoBean);
        if(bean!=null){
            int sex=bean.getSex();
            bean.setSexName(sex==1?"男":"女");
        }
        return bean;
    }

    /**
     * 修改用户的详情信息
     * @param userInfoBean
     * @return
     */
    public boolean updateUserInfo(UserInfoBean userInfoBean) {
        //前端没有把数据传输给后端
        if (userInfoBean.getCellphone()==null||userInfoBean.getSex()==0||
                userInfoBean.getBirth()==null||userInfoBean.getIntro()==null) {
            return false;
        }
        int i = userInfoDao.updateInfo(userInfoBean);

        return i > 0;
    }
}
