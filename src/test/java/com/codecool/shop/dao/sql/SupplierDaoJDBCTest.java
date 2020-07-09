//package com.codecool.shop.dao.sql;
//
//import com.codecool.shop.dao.SupplierDao;
//import com.codecool.shop.model.Supplier;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class SupplierDaoJDBCTest {
//    Supplier supplier = new Supplier("test", "test");
//    static SupplierDao supplierDao;
//
//    @BeforeAll
//    static void setup(){
//        supplierDao = new SupplierDaoJDBC();
//    }
//
//    @Test
//    void testAddValue() {
//        supplierDao.add(supplier);
//        assertNotEquals(0, supplier.getId());
//    }
//
//    @Test
//    void testFindValidIdReturnsCorrectValue() {
//        Supplier supplierToFind = supplierDao.find(2);
//        assertEquals(2, supplierToFind.getId());
//    }
//
//    @Test
//    void testRemoveIdReturnsNull() {
//        int id = 4;
//        supplierDao.remove(id);
//        assertNull(supplierDao.find(id));
//    }
//
//    @Test
//    void testGetAllReturnsCorrectValue() {
//        int supplierCount = 3;
//        assertEquals(supplierCount, supplierDao.getAll().size());
//    }
//
//    @Test
//    void testGetByNameReturnsCorrectValue() {
//        supplierDao.add(supplier);
//        String name = "test";
//        assertEquals(name, supplierDao.getByName(name).getName());
//    }
//}