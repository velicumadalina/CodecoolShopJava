package com.codecool.shop.dao.sql;

import com.codecool.shop.connection.dbConnection;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ProductDaoJDBC implements ProductDao {
    @Override
    public void add(Product product) {
        String query = "INSERT INTO products (id, supplier_id, category_id, name, description, price, currency) " +
                "VALUES (?,?,?, ?,?,?,?) ON CONFLICT DO NOTHING ";
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, product.getId());
            statement.setInt(2, product.getSupplier().getId());
            statement.setInt(3, product.getProductCategory().getId());
            statement.setString(4, product.getName());
            statement.setString(5, product.getDescription());
            statement.setFloat(6, product.getPrice());
            statement.setString(7, product.getDefaultCurrency().toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product find(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public List<Product> getAll() {
        return null;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return null;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        return null;
    }

    @Override
    public List<Product> filterBy(String category, String supplier) {
        return null;
    }
}
