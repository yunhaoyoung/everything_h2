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
                    instance.setPassword("root");
                    instance.setDriverClassName("com.mysql.jdbc.Driver");
                }
            }
        }
        return instance;
    }



    public static void main(String[] args) {
        Thing thing = new Thing();
        DataSource dataSource = DataSourceFactory.getInstance();
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("insert into thing (name,path,depth,file_type) values (?,?,?,?)")){
            /**statement.setString("简历.pdf");
            statement.setString("D:\\abc\\简历");
            statement.setInt(2);
            statement.setString("DOC");*/

        }catch (SQLException e){

        }
    }
}
