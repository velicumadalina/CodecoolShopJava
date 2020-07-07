package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

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
        String total = request.getParameter("total");
        System.out.println(namesAndQuantities);
        System.out.println(name);
        System.out.println(email);
        context.setVariable("name", name);
        context.setVariable("email", email);
        context.setVariable("phone", phone);
        context.setVariable("address", address);
        context.setVariable("total", total);
        context.setVariable("productsDetails", productsDetails);
        String orderInfo = getOrderInfo(name, phone, address, productsDetails, total);
        sendMail(email, "webshopemail123@gmail.com", orderInfo);
        System.out.println(orderInfo);
        engine.process("product/payment.html", context, response.getWriter());
    }

    public void sendMail(String to, String from, String content) {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", "smtp.gmail.com");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.port", "587");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, "123asd456fgh!");
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Order #1");
            message.setText(content);
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public String getOrderInfo(String name, String phone, String address, List<String> productDetails, String total){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name).append(", thank you for your order!").append("\n").append(" Order summary: ");
        for(String elem:productDetails){
            stringBuilder.append(elem).append("\n");
        }
        stringBuilder.append("Total: ").append(total).append("\n");
        stringBuilder.append("Shipping information: ").append("\n"+name).append("\n"+phone).append("\n"+address).append("\n");
        stringBuilder.append("Thank you for choosing our shop!");
        return stringBuilder.toString();
    }


}



