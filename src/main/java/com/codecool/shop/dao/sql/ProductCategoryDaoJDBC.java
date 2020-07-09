package com.codecool.shop.dao.sql;

import com.codecool.shop.connection.dbConnection;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ProductCategoryDaoJDBC implements ProductCategoryDao {
    @Override
    public void add(ProductCategory category) {
        String query = "INSERT INTO categories (id, name, department, description) " +
                "VALUES (?,?,?,?) ON CONFLICT DO NOTHING";
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, category.getId());
            statement.setString(2, category.getName());
            statement.setString(3, category.getDepartment());
            statement.setString(4, category.getDescription());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ProductCategory find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<ProductCategory> getAll() {
        return null;
    }

    @Override
    public ProductCategory getByName(String name) {
        return null;
    }
}
