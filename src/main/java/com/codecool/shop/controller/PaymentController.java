package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "PaymentController", urlPatterns = {"/payment"}, loadOnStartup = 1)
public class PaymentController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        String name = request.getParameter("fname") + " " + request.getParameter("lname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String namesAndQuantities = request.getParameter("namesAndQuantities");
        namesAndQuantities = namesAndQuantities.substring(1, namesAndQuantities.length()-1);
        List<String> productsDetails = Arrays.asList(namesAndQuantities.split(","));
        System.out.println(namesAndQuantities);
        System.out.println(name);
        System.out.println(email);
        context.setVariable("name", name);
        context.setVariable("email", email);
        context.setVariable("phone", phone);
        context.setVariable("address", address);
        context.setVariable("productsDetails", productsDetails);
        engine.process("product/payment.html", context, response.getWriter());
    }



}
