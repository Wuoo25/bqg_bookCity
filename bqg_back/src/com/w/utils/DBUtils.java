package com.w.utils;

import org.apache.taglibs.standard.lang.jstl.GreaterThanOperator;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 将数据库中的值映射到JavaBean中
 * 结果集自动映射
 */
public class DBUtils {

    public static <E> E getBean(ResultSet rs,Class<E> clazz){
        E e=null;

        //下一行有值循环读取
        try {
            //rs.next中有数据，指针往下读取
            if(rs.next()){
                //创建一个新的JavaBean对象
                e = clazz.newInstance();
                //获取结果集中的表头
                ResultSetMetaData metaData = rs.getMetaData();
                //获取表头数量
                int count=metaData.getColumnCount();
                //循环映射
                for (int i=1;i<=count;i++){
                    //基本需要使用别名，用getColumnLabel方法
                    //i代表第i个表头
                    String columnName=metaData.getColumnLabel(i);
                    //根据遍历出来的表头的名称获取表头对应类的数据
                    Object obj = rs.getObject(i);
                    //根据遍历出来的字段名获取相同名称的JavaBean中的成员变量，由于是private需要使用getDeclareField方法
                    //此处即为映射
                    Field field = clazz.getDeclaredField(columnName);
                    //开启允许，针对private变量
                    field.setAccessible(true);
                    //将获取到的数据放到成员变量上
                    field.set(e,obj);
                }
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
        return e;
    }


    /**
     * 映射多个结果集到JavaBean中
     * @param rs
     * @param clazz
     * @param <E>
     * @return
     */
    public static <E> List<E> getBeanList(ResultSet rs, Class<E> clazz){
        List<E> list=new ArrayList<>();
        //下一行有值循环读取
        try {
            //循环读取
            while(rs.next()){
                //创建一个新的JavaBean对象
                E e = clazz.newInstance();
                //获取结果集中的表头
                ResultSetMetaData metaData = rs.getMetaData();
                //获取表头数量
                int count=metaData.getColumnCount();
                //循环映射
                for (int i=1;i<=count;i++){
                    //基本需要使用别名，用getColumnLabel方法
                    //i代表第i个表头
                    String columnName=metaData.getColumnLabel(i);
                    //根据遍历出来的表头的名称获取表头对应类的数据
                    Object obj = rs.getObject(i);
                    //根据遍历出来的字段名获取相同名称的JavaBean中的成员变量，由于是private需要使用getDeclareField方法
                    //此处即为映射
                    Field field = clazz.getDeclaredField(columnName);
                    //开启允许，针对private变量
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
