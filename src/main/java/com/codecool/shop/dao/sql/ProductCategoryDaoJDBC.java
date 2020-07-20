package com.codecool.shop.dao.sql;

import com.codecool.shop.connection.dbConnection;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        ProductCategory tempCategory = null;
        String query = "SELECT * FROM categories WHERE id = ?";
        try (Connection connection = dbConnection.getConnection();) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                tempCategory = new ProductCategory(
                        resultSet.getString("name"),
                        resultSet.getString("department"),
                        resultSet.getString("description"));
                tempCategory.setId(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tempCategory;
    }

    @Override
    public void remove(int id) {
        String query = "DELETE FROM categories WHERE id = ?";
        try (Connection connection = dbConnection.getConnection();) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<ProductCategory> getAll() {
        ProductCategory tempCategory;
        List<ProductCategory> products = new ArrayList<>();
        String query = "SELECT * FROM categories";
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tempCategory = new ProductCategory(
                        resultSet.getString("name"),
                        resultSet.getString("department"),
                        resultSet.getString("description"));
                tempCategory.setId(resultSet.getInt("id"));
                products.add(tempCategory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public ProductCategory getByName(String name) {
        ProductCategory tempCategory = null;
        String query = "SELECT * FROM categories WHERE name = ?";
        try (Connection connection = dbConnection.getConnection();) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                tempCategory = new ProductCategory(
                        resultSet.getString("name"),
                        resultSet.getString("department"),
                        resultSet.getString("description"));
                tempCategory.setId(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tempCategory;
    }
}
