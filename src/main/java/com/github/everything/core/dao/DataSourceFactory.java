package com.github.everything.core.dao;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.everything.core.model.Thing;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DataSourceFactory {

    private static volatile DruidDataSource instance;
    private DataSourceFactory(){

    }

    public static DataSource getInstance() {
        if (instance == null){
            synchronized (DataSource.class) {
                if (instance == null) {
                    instance = new DruidDataSource();
                    instance.setUrl("jdbc:mysql://127.0.0.1:3306/everything_h2");
                    instance.setUsername("root");
                    instance.setPassword("123456");
                    instance.setDriverClassName("com.mysql.jdbc.Driver");
                }
            }
        }
        return instance;
    }
}
