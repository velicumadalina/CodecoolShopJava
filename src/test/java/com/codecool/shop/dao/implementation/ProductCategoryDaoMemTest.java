package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryDaoMemTest {
    private static ProductCategoryDao productCategoryDao;
    private static ProductCategory productCat = new ProductCategory("ProductTest", "DepartmentTest", "DescriptionTest");
    ProductCategory prodCat1 = new ProductCategory("ProductTest1", "DepartmentTest1", "DescriptionTest1");
    ProductCategory prodCat2 = new ProductCategory("ProductTest2", "DepartmentTest2", "DescriptionTest2");


    @BeforeAll
    public static void setUp() {
        productCategoryDao = ProductCategoryDaoMem.getInstance();
        productCategoryDao.add(productCat);
    }
//
//    @Test
//    @Order(1)
//    void testGetByIdReturnsCorrectValue() {
//        System.out.println("1");
//        assertEquals(productCat, productCategoryDao.find(productCat.getId()));
//    }


//    @Test
//    void TestGetAllReturnsCorrectValue() {
//        System.out.println("2");
//        productCategoryDao.add(prodCat1);
//        productCategoryDao.add(prodCat2);
//        List<ProductCategory> productCategoryList = new ArrayList<>();
//        productCategoryList.add(productCat);
//        productCategoryList.add(prodCat1);
//        productCategoryList.add(prodCat2);
//        assertEquals(productCategoryList, productCategoryDao.getAll());
//    }

    @Test
    void testGetInstanceReturnsInstance() {
        System.out.println("3");
        assertNotNull(productCategoryDao);
    }

    @Test
    void testGetByNameReturnsValue() {
        System.out.println("4");
        String name = "ProductTest";
        assertNotNull(productCategoryDao.getByName(name));
    }

    //
//    ////
    @Test
    void testGetByNameReturnsCorrectValue() {
        System.out.println("5");
        String name = "ProductTest";
        assertEquals(productCat, productCategoryDao.getByName(name));
    }

    //
//    ////
    @Test
    void testGetByIdReturnsValue() {
        System.out.println("6");
        assertNotNull(productCategoryDao.find(productCat.getId()));
    }

    //
//    //
//
//
    @Test
    void TestGetAllReturnsValue() {
        System.out.println("7");
//        ProductCategory prodCat1 = new ProductCategory("ProductTest1", "DepartmentTest1", "DescriptionTest1");
//        ProductCategory prodCat2 = new ProductCategory("ProductTest2", "DepartmentTest2", "DescriptionTest2");
        productCategoryDao.add(prodCat1);
        productCategoryDao.add(prodCat2);
        assertNotNull(productCategoryDao.getAll());
    }
//
//
    @Test
    void testFindRemovedIdReturnsNull() {
        System.out.println("8");
        productCategoryDao.remove(productCat.getId());
        assertNull(productCategoryDao.find(productCat.getId()));
    }


}