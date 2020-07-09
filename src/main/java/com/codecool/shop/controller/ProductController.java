package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.dao.sql.ProductCategoryDaoJDBC;
import com.codecool.shop.dao.sql.ProductDaoJDBC;
import com.codecool.shop.dao.sql.SupplierDaoJDBC;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = new ProductDaoJDBC();
        ProductCategoryDao productCategoryDataStore = new ProductCategoryDaoJDBC();
        SupplierDao supplierCategory = new SupplierDaoJDBC();
        System.out.println("Din controller" + productCategoryDataStore.getAll());
        System.out.println(productDataStore.getAll());
        System.out.println(supplierCategory.getAll());
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("category", productCategoryDataStore.getAll());
        context.setVariable("products", productDataStore.getAll());
        context.setVariable("suppliers", supplierCategory.getAll());
        engine.process("product/index.html", context, resp.getWriter());
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String categoryToSort = request.getParameter("sort_category");
        String supplierToSort =  request.getParameter("sort_supplier");
        ProductDao productDao = new ProductDaoJDBC();
        SupplierDao supplierDao = new SupplierDaoJDBC();
        ProductCategoryDao prodDao = new ProductCategoryDaoJDBC();
        List<Product> product = new ArrayList<>();
        if(!supplierToSort.equals("choose_supplier") && categoryToSort.equals("choose_category")){
            product = productDao.getBy(supplierDao.getByName(supplierToSort));
        }
        if(!categoryToSort.equals("choose_category") && supplierToSort.equals("choose_supplier")) {
            product = productDao.getBy(prodDao.getByName(categoryToSort));
        }
        if(!supplierToSort.equals("choose_supplier") && !categoryToSort.equals("choose_category")){
            product = productDao.filterBy(categoryToSort, supplierToSort);
        }
        WebContext context = new WebContext(request, response, request.getServletContext());
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        context.setVariable("category", productCategoryDataStore.getAll());
        context.setVariable("products", product);
        context.setVariable("productToSort", categoryToSort);
        context.setVariable("supplierToSort", supplierToSort);
        context.setVariable("suppliers", supplierDao.getAll());
        System.out.println(categoryToSort);
        System.out.println(product.toString());
        engine.process("product/index.html", context, response.getWriter());
    }

}
