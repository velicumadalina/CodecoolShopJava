package com.codecool.shop.dao.sql;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoJDBCTest {
    private static ProductDao productDao;
    private static SupplierDao supplierDao;
    private static ProductCategoryDao productCategoryDao;
    private static Supplier supplier;
    private static ProductCategory productCategory;
    private static Product product;


    @BeforeAll
    static void setUp() {
        productDao = new ProductDaoJDBC();
        supplierDao = new SupplierDaoJDBC();
        productCategoryDao = new ProductCategoryDaoJDBC();
        supplier = supplierDao.find(1);
        productCategory = productCategoryDao.find(1);
        product = new Product("test", 499.99f, "USD",
                "test", productCategory, supplier);
    }

    @Test
    void testAdd() {
        productDao.add(product);
        assertEquals(1, product.getId());
    }


    @Test
    void find() {
        product = new Product("test2", 499.99f, "USD",
                "test", productCategory, supplier);
        productDao.add(product);
        assertEquals(3, productDao.find(3).getId());
    }

    @Test
    void remove() {
        int id = 1;
        productDao.remove(id);
        assertNull(productDao.find(id));
    }

    @Test
    void getAll() {
        productDao.add(product);
        Product product2 = new Product("test2", 499.99f, "USD",
                "test", productCategory, supplier);
        productDao.add(product2);
        int productCount = 2;
        assertEquals(productCount, productDao.getAll().size());
    }

    @Test
    void getBy() {


    }

    @Test
    void testGetBy() {
    }
}