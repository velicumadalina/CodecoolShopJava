package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryDaoMemTest {
    private ProductCategoryDao productCategoryDao;
    private ProductCategory productCat = new ProductCategory("ProductTest", "DepartmentTest", "DescriptionTest");


    @BeforeEach
    void setUp() {
        productCategoryDao = ProductCategoryDaoMem.getInstance();
        productCategoryDao.add(productCat);
    }

    @Test
    void testGetInstanceReturnsInstance() {
        assertNotNull(productCategoryDao);
    }

    @Test
    void testGetByNameReturnsValue() {
        String name = "ProductTest";
        assertNotNull(productCategoryDao.getByName(name));
    }

    @Test
    void testGetByNameReturnsCorrectValue() {
        String name = "ProductTest";
        assertEquals(productCat, productCategoryDao.getByName(name));
    }

    @Test
    void testGetByIdReturnsValue() {
        assertNotNull(productCategoryDao.find(productCat.getId()));
    }

    @Test
    void testGetByIdReturnsCorrectValue() {
        assertEquals(productCat, productCategoryDao.find(productCat.getId()));
    }
//
//    @Test
//    void TestGetAllReturnsValue() {
//        ProductCategory prodCat1 = new ProductCategory("ProductTest1", "DepartmentTest1", "DescriptionTest1");
//        ProductCategory prodCat2 = new ProductCategory("ProductTest2", "DepartmentTest2", "DescriptionTest2");
//        productCategoryDao.add(prodCat1);
//        productCategoryDao.add(prodCat2);
//        assertNotNull(productCategoryDao.getAll());
//    }

    @Test
    void TestGetAllReturnsCorrectValue() {
        ProductCategory prodCat1 = new ProductCategory("ProductTest1", "DepartmentTest1", "DescriptionTest1");
        ProductCategory prodCat2 = new ProductCategory("ProductTest2", "DepartmentTest2", "DescriptionTest2");
        productCategoryDao.add(prodCat1);
        productCategoryDao.add(prodCat2);
        List<ProductCategory> productCategoryList = new ArrayList<>();
        productCategoryList.add(prodCat1);
        productCategoryList.add(prodCat2);
        assertEquals(productCategoryList, productCategoryDao.getAll());
    }


    @Test
    void testFindRemovedIdReturnsNull() {
        productCategoryDao.remove(productCat.getId());
        assertNull(productCategoryDao.find(productCat.getId()));
    }
}