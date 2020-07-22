package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.dao.sql.ProductDaoJDBC;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet(name = "ProductDetailsController", urlPatterns = {"/details"}, loadOnStartup = 1)
public class ProductDetailsController extends HttpServlet {
    ProductDao productDao = new ProductDaoJDBC();
    Product product;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        int productId = Integer.parseInt(request.getParameter("product"));
        product = productDao.find(productId);
        System.out.println("DIN DETALII DE SUS"+productId);
        context.setVariable("product", product);
        response.sendRedirect("/details");
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        System.out.println("DIN DETALII "+product);
        context.setVariable("product", product);
        engine.process("product/product_details.html", context, response.getWriter());
    }
}


