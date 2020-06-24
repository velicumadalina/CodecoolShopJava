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

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


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
        List<Integer> productQuantities = new ArrayList<>();
        for (Product obj : myCart.getCartContent()) {
            productQuantities.add(getQuantity(obj.getName()));
        }
        productQuantities = productQuantities.stream().distinct().collect(Collectors.toList());
        System.out.println(productQuantities.toString());
        List<Product> cart = myCart.getCartContent().stream().distinct().collect(Collectors.toList());
        context.setVariable("cart", cart);
        context.setVariable("totalPrice", total);
        context.setVariable("quantities", productQuantities);
        context.setVariable("totalItems", totalItems(productQuantities));
        engine.process("product/cart.html", context, response.getWriter());
    }

    public int getQuantity(String name) {
        Product product = getProductByName(name);
        return Collections.frequency(myCart.getCartContent(), product);
    }

    public Product getProductByName(String name){
        for (Product prod : myCart.getCartContent()) {
            if (prod.getName().equals(name)) {
                return prod;
            }
        }
        return null;
    }

    public int totalItems(List<Integer> cart) {
        int total = 0;
        for (Integer quantity : cart) {
            total += quantity;
        }
        return total;
    }
}