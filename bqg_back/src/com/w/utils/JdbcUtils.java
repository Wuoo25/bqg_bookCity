package com.w.utils;

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.*;

/**
 * 此方法用于简化代码，方法为建立连接和关闭连接
 */
public class JdbcUtils {

    //匿名内部类
    //静态代码块，在第一次加载JdbcUtils的时候只执行一次，利用数据库线程池（Druid），但是使用完成后必须关闭
    private static DruidDataSource dataSource=new DruidDataSource();
    static{
        //设置驱动类
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        //设置连接地址
        dataSource.setUrl("jdbc:mysql://localhost:3306/bqg_back");
        //设置用户名
        dataSource.setUsername("root");
        //设置密码
        dataSource.setPassword("123456");
    }
    //获取数据库连接
    public static Connection getConnecton() {

        try {
            return dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    //关闭数据库连接
    public static void release(PreparedStatement ps , Connection cn){

        release(null,ps,cn);
    }

    public static void release(ResultSet rs , PreparedStatement ps , Connection cn){

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (cn != null) {
            try {
                cn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

}
