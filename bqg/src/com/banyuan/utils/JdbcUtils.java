package com.banyuan.utils;

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.*;

public class JdbcUtils {
    private static DruidDataSource dataSource = new DruidDataSource();
    static{
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/bqg_back");
        dataSource.setUsername("root");
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
