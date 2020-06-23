package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "CartController", urlPatterns = {"/cart"}, loadOnStartup = 1)
public class CartController extends HttpServlet {
    private Cart myCart = new Cart();
    private float total = 0;



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String itemToAdd = request.getParameter("add_product");
        ProductDao productDao = ProductDaoMem.getInstance();
        Product product = productDao.find((Integer.parseInt(itemToAdd)));
        myCart.add(product);
        total += product.getPrice();
        System.out.println(product);
        response.sendRedirect("/");
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        context.setVariable("cart", myCart);
        context.setVariable("total", total);
        engine.process("product/cart.html", context, response.getWriter());
    }
}