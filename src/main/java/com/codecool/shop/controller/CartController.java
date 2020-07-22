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
import java.util.*;
import java.util.stream.Collectors;


@WebServlet(name = "CartController", urlPatterns = {"/" + "cart"}, loadOnStartup = 1)
public class CartController extends HttpServlet {
    CartDao cartDao = CartDaoMem.getInstance();
    private Cart myCart = cartDao.find(1);
    List<Product> cartContents = myCart.getCartContent();
    private float total = 0;
    List<Product> cart;
    //    = myCart.getCartContent().stream().distinct().collect(Collectors.toList());
    List<Integer> productQuantities;
    //    = getFrequencies(cart, myCart.getCartContent());
    List<String> namesAndQuantities;
//    = getNamesAndQuantities(cart, productQuantities);


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String itemToAdd = request.getParameter("add_product");
        String newQuantity = request.getParameter("quantity");
        String originalQuantity = request.getParameter("original-qty");
        String qtyIndex = request.getParameter("index");
        if (itemToAdd != null) {
            ProductDao productDao = new ProductDaoJDBC();
            Product product = productDao.find((Integer.parseInt(itemToAdd)));
            cartContents.add(product);
            total += product.getPrice();
            response.sendRedirect("/");
        } else if (Integer.parseInt(newQuantity) != Integer.parseInt(originalQuantity)) {
            ProductDao productDao = new ProductDaoJDBC();
            Product product = productDao.find(Integer.parseInt(request.getParameter("product_id")));
            productQuantities.set(Integer.parseInt(qtyIndex), Integer.parseInt(newQuantity));
            namesAndQuantities = getNamesAndQuantities(cart, productQuantities);
            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
            WebContext context = new WebContext(request, response, request.getServletContext());
            if (Integer.parseInt(newQuantity) > Integer.parseInt(originalQuantity)) {
                for (int i = 0; i < (Integer.parseInt(newQuantity) - Integer.parseInt(originalQuantity)); i++) {
                    total += product.getPrice();
                    cartContents.add(product);
                    cart = getDistinctProductsJDBC();
                    productQuantities = getFrequencies(cart, cartContents);
                    namesAndQuantities = getNamesAndQuantities(cart, productQuantities);
                }
            } else if (Integer.parseInt(newQuantity) < Integer.parseInt(originalQuantity) && (Integer.parseInt(newQuantity) != 0)) {
                for (int i = 0; i < (Integer.parseInt(originalQuantity) - Integer.parseInt(newQuantity)); i++) {
                    total -= product.getPrice();
                    removeFirstOccurenceByName(product, cartContents);
                    cart = getDistinctProductsJDBC();
                    productQuantities = getFrequencies(cart, cartContents);
                    System.out.println("AAAAAAAA " + cartContents);
                    namesAndQuantities = getNamesAndQuantities(cart, productQuantities);
                }
            } else if (Integer.parseInt(newQuantity) == 0) {
                total -= product.getPrice();
                removeByName(product, cartContents);
                cart = getDistinctProductsJDBC();
                productQuantities = getFrequencies(cart, cartContents);
                namesAndQuantities = getNamesAndQuantities(cart, productQuantities);
            }
            generateCart(response, engine, context);
        }
    }

    private void generateCart(HttpServletResponse response, TemplateEngine engine, WebContext context) throws IOException {
        context.setVariable("cart", cart);
        System.out.println("CART " + cart);
        context.setVariable("totalPrice", total);
        context.setVariable("quantities", productQuantities);
        System.out.println("QUANTITIES" + productQuantities);
        context.setVariable("totalItems", cartContents.size());
        context.setVariable("namesAndQuantities", namesAndQuantities);
        engine.process("product/cart.html", context, response.getWriter());
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        cart = getDistinctProductsJDBC();
        productQuantities = getFrequencies(cart, cartContents);
        namesAndQuantities = getNamesAndQuantities(cart, productQuantities);
        generateCart(response, engine, context);
    }

    public List<Integer> getFrequencies(List<Product> distinct, List<Product> allProducts) {
        List<Integer> freqs = new ArrayList<>();
        for (Product elem : distinct) {
            freqs.add(getProductFrequency(elem, allProducts));
        }
        return freqs;
    }

    public int getProductFrequency(Product product, List<Product> cartContent) {
        int frequency = 0;
        for (Product elem : cartContent) {
            if (elem.getName().equals(product.getName())) {
                frequency++;
            }
        }
        return frequency;
    }

    public List<String> getNamesAndQuantities(List<Product> distinct, List<Integer> quantities) {
        List<String> namesAndQuantities = new ArrayList<>();
        for (int i = 0; i < distinct.size(); i++) {
            namesAndQuantities.add(distinct.get(i).getName() + " - " + quantities.get(i) + " item(s)");
        }
        return namesAndQuantities;
    }

    public List<Product> getDistinctProductsJDBC() {
        Set<String> set = new HashSet<>(cartContents.size());
        return cartContents.stream().filter(p -> set.add(p.getName())).collect(Collectors.toList());
    }

    public void removeByName(Product product, List<Product> productList) {
        productList.removeIf(prod -> prod.getName().equals(product.getName()));
    }

    public void removeFirstOccurenceByName(Product product, List<Product> productList) {
        for(Product prod: productList){
            if(prod.getName().equals(product.getName())){
                productList.remove(prod);
                break;
            }
        }
    }
}