package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.sql.ProductDaoJDBC;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@WebServlet(name = "CartController", urlPatterns = {"/" + "cart"}, loadOnStartup = 1)
public class CartController extends HttpServlet {
    CartDao cartDao = CartDaoMem.getInstance();
    private Cart myCart = cartDao.find(1);
    private float total = 0;
    List<Product> cart = myCart.getDistinctProducts();
    List<Integer> productQuantities = myCart.getFrequencies();
    List<String> namesAndQuantities = myCart.getNamesAndQuantities();


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ProductDao productDao = new ProductDaoJDBC();
        String itemToAdd = request.getParameter("add_product");
        if (itemToAdd != null) {
            Product product = productDao.find((Integer.parseInt(itemToAdd)));
            myCart.add(product);
            total += product.getPrice();
            response.sendRedirect("/");
        }
        if (request.getParameter("quantity") != null) {
            updateQuantity(request, response);
        }
    }

    private void generateCart(HttpServletResponse response, TemplateEngine engine, WebContext context) throws IOException {
        context.setVariable("cart", cart);
        context.setVariable("totalPrice", total);
        context.setVariable("quantities", productQuantities);
        context.setVariable("totalItems", myCart.totalItems(productQuantities));
        context.setVariable("namesAndQuantities", namesAndQuantities);
        engine.process("product/cart.html", context, response.getWriter());
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        cart = myCart.getCartContent().stream().distinct().collect(Collectors.toList());
        productQuantities = myCart.getFrequencies();
        namesAndQuantities = myCart.getNamesAndQuantities();
        System.out.println(productQuantities);
        generateCart(response, engine, context);
    }

    public void updateQuantity(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        ProductDao productDao = new ProductDaoJDBC();
        int newQuantity = Integer.parseInt(request.getParameter("quantity"));
        int originalQuantity = Integer.parseInt(request.getParameter("original-qty"));
        int qtyIndex = Integer.parseInt(request.getParameter("index"));
        if (newQuantity != originalQuantity) {
            Product product = productDao.find(Integer.parseInt(request.getParameter("product_id")));
            System.out.println("BEFORE " +productQuantities);
            productQuantities.set(qtyIndex, newQuantity);
            System.out.println("AFTER " +productQuantities);
            namesAndQuantities = myCart.getNamesAndQuantities();
            if (newQuantity > originalQuantity) {
                for (int i = 0; i < (newQuantity - originalQuantity); i++) {
                    myCart.getCartContent().add(product);
                    total += product.getPrice();
                }
            } else if ((newQuantity < originalQuantity) && (newQuantity != 0)) {
                for (int i = 0; i < (originalQuantity - newQuantity); i++) {
                    total -= product.getPrice();
                    myCart.getCartContent().remove(product);
                }
            } else if (newQuantity == 0) {
                total -= product.getPrice();
                myCart.remove(product);
                cart.remove(qtyIndex);
                productQuantities.remove(qtyIndex);
                namesAndQuantities = myCart.getNamesAndQuantities();
            }
            generateCart(response, engine, context);
        }
    }
}