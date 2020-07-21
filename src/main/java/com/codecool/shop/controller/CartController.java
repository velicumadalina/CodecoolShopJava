package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
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
        String itemToAdd = request.getParameter("add_product");
        String newQuantity = request.getParameter("quantity");
        String originalQuantity = request.getParameter("original-qty");
        String qtyIndex = request.getParameter("index");
        if (itemToAdd != null) {
            ProductDao productDao = new ProductDaoJDBC();
            Product product = productDao.find((Integer.parseInt(itemToAdd)));
            System.out.println(itemToAdd);
            System.out.println(product);
            myCart.add(product);
            total += product.getPrice();
            System.out.println(product);
            response.sendRedirect("/");
        } else if (Integer.parseInt(newQuantity) != Integer.parseInt(originalQuantity)) {
            ProductDao productDao = new ProductDaoJDBC();
            Product product = productDao.find(Integer.parseInt(request.getParameter("product_id")));
            productQuantities.set(Integer.parseInt(qtyIndex), Integer.parseInt(newQuantity));
            System.out.println(productQuantities + "  index:  " + qtyIndex + "  vechi:  " + originalQuantity + " nou: " + newQuantity);
            namesAndQuantities = myCart.getNamesAndQuantities();
            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
            WebContext context = new WebContext(request, response, request.getServletContext());
            if (Integer.parseInt(newQuantity) > Integer.parseInt(originalQuantity)) {
                for (int i = 0; i < (Integer.parseInt(newQuantity) - Integer.parseInt(originalQuantity)); i++) {
                    total += product.getPrice();
                    myCart.getCartContent().add(product);
                }
            } else if (Integer.parseInt(newQuantity) < Integer.parseInt(originalQuantity) && (Integer.parseInt(newQuantity) != 0)) {
                for (int i = 0; i < (Integer.parseInt(originalQuantity) - Integer.parseInt(newQuantity)); i++) {
                    total -= product.getPrice();
                    myCart.getCartContent().remove(product);
                }
            } else if (Integer.parseInt(newQuantity) == 0) {
                total -= product.getPrice();
                myCart.remove(product);
                cart.remove(Integer.parseInt(qtyIndex));
                productQuantities.remove(Integer.parseInt(qtyIndex));
                namesAndQuantities = myCart.getNamesAndQuantities();
            }
            generateCart(response, engine, context);
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
        cart = myCart.getDistinctProductsJDBC();
        System.out.println(cart);
        productQuantities = myCart.getFrequencies();
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" +productQuantities);
        System.out.println("Cartul intreg este: " + myCart.getCartContent() + "\n" + " cartul fara repetitii este " + cart + "\n"+ " si frecventele sunt " + productQuantities);
        namesAndQuantities = myCart.getNamesAndQuantities();
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println(cart);
        System.out.println(productQuantities);
        System.out.println(namesAndQuantities);
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa");
        generateCart(response, engine, context);
    }
}