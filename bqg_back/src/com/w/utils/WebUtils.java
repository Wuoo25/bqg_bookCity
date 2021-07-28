package com.w.utils;

import com.alibaba.fastjson.JSONObject;
import com.w.message.ResponseData;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * 将收到的数据封装进javaBean中
 */
public class WebUtils {
    //T是泛型
        public static<T> T copy(HttpServletRequest req,Class<T> clazz){


            try {
                T t= clazz.newInstance();
                BeanUtils.populate(t,req.getParameterMap());
                return t;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
            return null;
        }

        //将java对象转为json语句
        public static void writeToJson(HttpServletResponse response, ResponseData rd){
            try {
                response.getWriter().write(JSONObject.toJSONString(rd));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

}
