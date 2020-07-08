package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SupplierDaoMemTest {
    private SupplierDao supplierDao;
    private Supplier supplier = new Supplier("TestSupplier", "TestDescription");


    @BeforeEach
    void setUp() {
        supplierDao = SupplierDaoMem.getInstance();
        supplierDao.add(supplier);
    }

    @Test
    void getInstance() {
        assertNotNull(supplierDao);
    }

    @Test
    void getByName() {
        String name = "TestSupplier";
        assertEquals(supplier, supplierDao.getByName(name));
    }

    @Test
    void add() {
        Supplier supplier2 = new Supplier("TestSupplier2", "TestDescription2");
        supplierDao.add(supplier2);
        assertEquals(2, supplierDao.getAll().size());

    }

    @Test
    void find() {
        assertEquals(supplier, supplierDao.find(supplier.getId()));
    }

    @Test
    void remove() {
        supplierDao.remove(supplier.getId());
        assertEquals(0, supplierDao.getAll().size());
    }

    @Test
    void getAll() {
        Supplier supplier3 = new Supplier("TestSupplier3", "TestDescription3");
        supplierDao.add(supplier3);
        List<Supplier> supplierList = new ArrayList<>();
        supplierList.add(supplier);
        supplierList.add(supplier3);
        assertEquals(supplierList, supplierDao.getAll());
    }
}