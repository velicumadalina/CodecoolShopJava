package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "PaymentController", urlPatterns = {"/payment"}, loadOnStartup = 1)
public class PaymentController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());

//

    }



//    public String getOrderInfo(String name, String phone, String address, List<String> productDetails, String total){
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(name).append(", thank you for your order!").append("\n").append(" Order summary: ");
//        for(String elem:productDetails){
//            stringBuilder.append(elem).append("\n");
//        }
//        stringBuilder.append("Total: ").append(total).append("\n");
//        stringBuilder.append("Shipping information: ").append("\n"+name).append("\n"+phone).append("\n"+address).append("\n");
//        stringBuilder.append("Thank you for choosing our shop!");
//        return stringBuilder.toString();
//    }


}



