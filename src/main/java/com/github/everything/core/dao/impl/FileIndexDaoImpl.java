package com.github.everything.core.dao.impl;

import com.github.everything.core.dao.FileIndexDao;
import com.github.everything.core.model.Condition;
import com.github.everything.core.model.Thing;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class FileIndexDaoImpl implements FileIndexDao {
    private final DataSource dataSource;

    public FileIndexDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void insert(Thing thing) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = this.dataSource.getConnection();
            String sql = "insert into everything_h2 () values (?,?,?,?)";
            statement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Thing thing) {

    }

    @Override
    public List<Thing> query(Condition condition) {
        return null;
    }
}
