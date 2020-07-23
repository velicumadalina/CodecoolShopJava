package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.dao.implementation.OrderDaoMem;
import com.codecool.shop.model.Order;
import com.codecool.shop.util.EmailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "PaymentController", urlPatterns = {"/order-confirmation"}, loadOnStartup = 1)
public class PaymentController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());
        OrderDao orderDao = OrderDaoMem.getInstance();
        Order currentOrder = orderDao.find(1);
        String order = currentOrder.getOrderInfo();
        List<String> orderDetails = Arrays.asList(order.split("\\n"));
        EmailSender.sendMail(currentOrder.getEmail(), "webshopemail123@gmail.com", order, currentOrder);
        context.setVariable("orderDetails", orderDetails);
        engine.process("product/confirmation.html", context, response.getWriter());
    }




}



