package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoMemTest {
    private ProductDao productDao;
    private SupplierDao supplierDao;
    private ProductCategoryDao productCategoryDao;
    private Supplier supplier;
    private ProductCategory productCategory;
    private Product product1;

    @BeforeEach
    void setUp() {
        productDao = ProductDaoMem.getInstance();
        supplierDao = SupplierDaoMem.getInstance();
        productCategoryDao = ProductCategoryDaoMem.getInstance();
        supplier = new Supplier("Supplier", "TestDescriptions");
        supplierDao.add(supplier);
        productCategory = new ProductCategory("ProductCategory", "Hardware", "A laptop");
        productCategoryDao.add(productCategory);
        product1 = new Product("TestName", 49.9f, "USD", "TestDescription", productCategory, supplier);
        productDao.add(product1);
    }

    @Test
    void testGetInstanceReturnsValue() {
        assertNotNull(productDao);
    }

    @Test
    void testFilterByReturnsValue() {
        Supplier filteredSupp = new Supplier("GoodSupplier", "GoodDescriptions");
        supplierDao.add(filteredSupp);
        Product product2 = new Product("GoodName", 68.9f, "USD", "TestDescription", productCategory, filteredSupp);
        productDao.add(product2);
        List<Product> products = new ArrayList<>();
        products.add(product2);
        assertEquals(products, productDao.filterBy("ProductCategory", "GoodSupplier"));
    }

    @Test
    void testAdd() {
        assertNotNull(productDao.find(product1.getId()));
    }

    @Test
    void testFindReturnsCorrectValue() {
        assertEquals(product1, productDao.find(product1.getId()));
    }

    @Test
    void testRemoveCorrectValue() {
        productDao.remove(product1.getId());
        assertNull(productDao.find(product1.getId()));
    }

    @Test
    void getAll() {
    }

    @Test
    void testGetBySupplier() {
        Supplier filteredSupp = new Supplier("GoodSupplier", "GoodDescriptions");
        supplierDao.add(filteredSupp);
        Product product2 = new Product("GoodName", 68.9f, "USD", "TestDescription", productCategory, filteredSupp);
        productDao.add(product2);
        List<Product> products = new ArrayList<>();
        products.add(product2);
        assertEquals(products, productDao.getBy(supplier));
    }

    @Test
    void testGetBy() {
        ProductCategory productCategory1 = new ProductCategory("GoodCategory", "GoodDepartment", "Description");
        productCategoryDao.add(productCategory1);
        Product product3 = new Product("GoodName", 68.9f, "USD", "TestDescription", productCategory1, supplier);
        productDao.add(product3);
        List<Product> products = new ArrayList<>();
        products.add(product3);
        assertEquals(products, productDao.getBy(productCategory1));
    }
}