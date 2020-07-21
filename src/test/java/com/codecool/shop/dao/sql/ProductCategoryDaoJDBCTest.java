//package com.codecool.shop.dao.sql;
//
//import com.codecool.shop.dao.ProductCategoryDao;
//import com.codecool.shop.model.ProductCategory;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class ProductCategoryDaoJDBCTest {
//    ProductCategoryDao productCategoryDao = new ProductCategoryDaoJDBC();
//
//    @Test
//    void add() {
//        ProductCategory productCategory = new ProductCategory("test", "test", "test");
//        productCategoryDao.add(productCategory);
//        assertNotNull(productCategoryDao.find(productCategory.getId()));
//    }
//
//    @Test
//    void find() {
//        ProductCategory productCategory = new ProductCategory("test", "test", "test");
//        productCategoryDao.add(productCategory);
//        assertEquals(2, productCategoryDao.find(2).getId());
//    }
//
//    @Test
//    void remove() {
//    }
//
//    @Test
//    void getAll() {
//    }
//
//    @Test
//    void getByName() {
//    }
//}