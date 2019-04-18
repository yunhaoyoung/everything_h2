package com.github.everything.core.dao;

import com.alibaba.druid.pool.DruidDataSource;


import javax.sql.DataSource;
import java.io.*;
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
                    /**
                     * 这是MySQL的配置
                     * instance = new DruidDataSource();
                     * instance.setUrl("jdbc:mysql://127.0.0.1:3306/everything_h2");
                     * instance.setUsername("root");
                     * instance.setPassword("123456");
                     *  instance.setDriverClassName("com.mysql.jdbc.Driver");
                     */
                    /**
                     * 这是链接H2数据库的配置
                     */
                    instance = new DruidDataSource();
                    instance.setTestWhileIdle(false);
                    String path = System.getProperty("user.dir")+ File.separator+"everything_h2";
                    instance.setUrl("jdbc:h2:"+path);
                    instance.setDriverClassName("org.h2.Driver");

                    //数据库创建完成之后初始化表结构
                    databaseInit(false);
                }
            }
        }
        return instance;
    }

    public static void databaseInit(boolean buildIndex){
        StringBuilder sb = new StringBuilder();
        try(InputStream in =DataSourceFactory.class.getClassLoader().getResourceAsStream("database.sql")){
            if(in != null){
                try(BufferedReader reader = new BufferedReader(new InputStreamReader(in))){
                    String line = null;
                    while ((line = reader.readLine()) != null){
                        sb.append(line);
                    }
                } catch (IOException e){
                    e.printStackTrace();
                }
            } else {
                throw  new RuntimeException("database.sql script can't load please check it");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        String sql = sb.toString();
        try(Connection connection =getInstance().getConnection();
            ) {
            if(buildIndex){
                try(PreparedStatement statement = connection.prepareStatement("drop table if exists thing;")){
                    statement.executeUpdate();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            try(PreparedStatement statement = connection.prepareStatement(sql)){
                statement.executeUpdate();
            } catch (SQLException e){
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
