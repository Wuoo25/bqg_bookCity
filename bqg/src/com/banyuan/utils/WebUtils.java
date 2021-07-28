package com.banyuan.utils;

import com.alibaba.fastjson.JSONObject;
import com.banyuan.message.ResponseData;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class WebUtils {

    public static<T> T copy(HttpServletRequest req,Class<T> clazz){

        try {
            T t = clazz.newInstance();
            BeanUtils.populate(t, req.getParameterMap());
            return t;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void writeToJson(HttpServletResponse response, ResponseData rd){
        try {
            response.getWriter().write(JSONObject.toJSONString(rd));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
