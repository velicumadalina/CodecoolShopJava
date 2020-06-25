package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CheckoutController", urlPatterns = {"/check-out"}, loadOnStartup = 1)
public class CheckoutController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        String namesAndQuantities = request.getParameter("namesAndQuantities");
        context.setVariable("namesAndQuantities", namesAndQuantities);
        engine.process("product/check_out.html", context, response.getWriter());
    }




    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        engine.process("product/check_out.html", context, response.getWriter());
    }


}
