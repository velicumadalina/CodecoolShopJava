package com.codecool.shop.dao.sql;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.codecool.shop.connection.dbConnection;

public class SupplierDaoJDBC implements SupplierDao {
    private DataSource dataSource;
//
//    public SupplierDaoJDBC() throws SQLException {
//        this(dbConnection.getConnection());
//    }
//
//    public SupplierDaoJDBC(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }

    @Override
    public void add(Supplier supplier) {
        String query = "INSERT INTO suppliers (id, name, description) " +
                "VALUES (?,?,?)";
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, supplier.getId());
            statement.setString(2, supplier.getName());
            statement.setString(3, supplier.getDescription());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Supplier find(int id) {
        Supplier tempSupplier = null;
        String query = "SELECT * FROM suppliers WHERE id = ?";
        try (Connection connection = dataSource.getConnection();) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                tempSupplier = new Supplier(
                        resultSet.getString("name"),
                        resultSet.getString("description"));
                tempSupplier.setId(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tempSupplier;
    }

    @Override
    public void remove(int id) {
        String query = "DELETE FROM suppliers WHERE id = ?";
        try (Connection connection = dataSource.getConnection();) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Supplier> getAll() {
        Supplier tempSupplier = null;
        List<Supplier> suppliers = new ArrayList<>();
        String query = "SELECT * FROM suppliers";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tempSupplier = new Supplier(
                        resultSet.getString("name"),
                        resultSet.getString("description"));
                tempSupplier.setId(resultSet.getInt("id"));
                suppliers.add(tempSupplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }

    @Override
    public Supplier getByName(String name) {
        Supplier tempSupplier = null;
        String query = "SELECT * FROM suppliers WHERE name = ?";
        try (Connection connection = dataSource.getConnection();) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                tempSupplier = new Supplier(
                        resultSet.getString("name"),
                        resultSet.getString("description"));
                tempSupplier.setId(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tempSupplier;
    }
}
