package com.banyuan.utils;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {

    public static <E> E getBean(ResultSet rs,Class<E> clazz){
        E e = null;

        try {
            if (rs.next()) {
                //创建一个javaBean对象
                e = clazz.newInstance();
                //javabean对象创建完毕之后,我们需要从rs中获取数据, 放到JavaBean对象中
                //获取结果集中的表头
                ResultSetMetaData metaData = rs.getMetaData();
                //获取表头的数量
                int count = metaData.getColumnCount();
                //select userName from users;
                //select userName as un from users;
                //select * from users; id , userName ,password
                for (int i = 1; i <=count ; i++) {
                    //获取到表头名称,根据表头名称去JavaBean中找对应名称的成员变量
                    String columnName = metaData.getColumnLabel(i); //返回的结果就id
                    //根据遍历出来的表头的名称获取表头对应类的数据
                    Object obj = rs.getObject(i);
                    //根据遍历出来的字段名获取相同名称的JavaBean中的成员变量
                    Field field = clazz.getDeclaredField(columnName);
                    //开启允许
                    field.setAccessible(true);
                    //将获取到的数据放到成员变量上
                    field.set(e,obj);
                }
            }
        } catch (SQLException | InstantiationException | IllegalAccessException | NoSuchFieldException throwables) {
            throwables.printStackTrace();
        }

        return e;
    }


    public static <E> List<E> getBeanList(ResultSet rs, Class<E> clazz){
        List<E> list = new ArrayList<>();
        try {
            while (rs.next()) {
                //创建一个javaBean对象
                E e = clazz.newInstance();
                //javabean对象创建完毕之后,我们需要从rs中获取数据, 放到JavaBean对象中
                //获取结果集中的表头
                ResultSetMetaData metaData = rs.getMetaData();
                //获取表头的数量
                int count = metaData.getColumnCount();
                //select userName from users;
                //select userName as un from users;
                //select * from users; id , userName ,password
                for (int i = 1; i <=count ; i++) {
                    //获取到表头名称,根据表头名称去JavaBean中找对应名称的成员变量
                    String columnName = metaData.getColumnLabel(i); //返回的结果就id
                    //根据遍历出来的表头的名称获取表头对应类的数据
                    Object obj = rs.getObject(i);
                    //根据遍历出来的字段名获取相同名称的JavaBean中的成员变量
                    Field field = clazz.getDeclaredField(columnName);
                    //开启允许
                    field.setAccessible(true);
                    //将获取到的数据放到成员变量上
                    field.set(e,obj);
                }
                list.add(e);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (InstantiationException instantiationException) {
            instantiationException.printStackTrace();
        } catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.printStackTrace();
        } catch (NoSuchFieldException noSuchFieldException) {
            noSuchFieldException.printStackTrace();
        }
        return list;
    }
}
