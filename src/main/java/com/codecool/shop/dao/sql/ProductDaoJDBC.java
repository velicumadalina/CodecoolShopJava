package com.codecool.shop.dao.sql;

import com.codecool.shop.connection.dbConnection;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.w3c.dom.ls.LSOutput;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoJDBC implements ProductDao {
    private ProductCategoryDao productCategoryDao = new ProductCategoryDaoJDBC();
    private SupplierDao supplierDao = new SupplierDaoJDBC();


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
        Product tempProduct = null;
        String query = "SELECT * FROM products WHERE id = ?";
        try (Connection connection = dbConnection.getConnection();) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println(resultSet.getInt("category_id"));
                tempProduct = new Product(
                        resultSet.getString("name"),
                        resultSet.getInt("price"),
                        resultSet.getString("currency"),
                        resultSet.getString("description"),
                        productCategoryDao.find(resultSet.getInt("category_id")),
                        supplierDao.find(resultSet.getInt("supplier_id")));
                tempProduct.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tempProduct;
    }

    @Override
    public void remove(int id) {
        String query = "DELETE FROM products WHERE id = ?";
        try (Connection connection = dbConnection.getConnection();) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Product> getAll() {
        Product tempProduct;
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products";
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tempProduct = new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("price"),
                        resultSet.getString("currency"),
                        resultSet.getString("description"),
                        productCategoryDao.find(resultSet.getInt("category_id")),
                        supplierDao.find(resultSet.getInt("supplier_id")));
                products.add(tempProduct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        Product tempProduct;
        String query = "SELECT * FROM products WHERE supplier_id = ?";
        List<Product> products = new ArrayList<>();
        try (Connection connection = dbConnection.getConnection();) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, supplier.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tempProduct = new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("price"),
                        resultSet.getString("currency"),
                        resultSet.getString("description"),
                        productCategoryDao.find(resultSet.getInt("category_id")),
                        supplierDao.find(resultSet.getInt("supplier_id")));
                products.add(tempProduct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        Product tempProduct;
        String query = "SELECT * FROM products WHERE category_id = ?";
        List<Product> products = new ArrayList<>();
        try (Connection connection = dbConnection.getConnection();) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, productCategory.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tempProduct = new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("price"),
                        resultSet.getString("currency"),
                        resultSet.getString("description"),
                        productCategoryDao.find(resultSet.getInt("category_id")),
                        supplierDao.find(resultSet.getInt("supplier_id")));
                products.add(tempProduct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Product> filterBy(String category, String supplier) {
        Product tempProduct;
        String query = "SELECT * FROM products WHERE category_id = ? AND supplier_id = ?";
        List<Product> products = new ArrayList<>();
        try (Connection connection = dbConnection.getConnection();) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, productCategoryDao.getByName(category).getId());
            statement.setInt(2, supplierDao.getByName(supplier).getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tempProduct = new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("price"),
                        resultSet.getString("currency"),
                        resultSet.getString("description"),
                        productCategoryDao.find(resultSet.getInt("category_id")),
                        supplierDao.find(resultSet.getInt("supplier_id")));
                products.add(tempProduct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}
