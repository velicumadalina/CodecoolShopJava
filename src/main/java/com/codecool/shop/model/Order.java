package com.codecool.shop.model;

import java.util.List;

public class Order extends BaseModel {
    List<String> productsAndQuantities;
    String total;
    String name;
    String email;
    String phone;
    String address;

    public Order(int id,String total, String name, String email, String phone, String address, List<String> productsAndQuantities){
        super();
        this.address = address;
        this.email = email;
        this.name = name;
        this.total = total;
        this.productsAndQuantities = productsAndQuantities;
        this.phone = phone;
        this.id = id;
    }

    public String getOrderInfo(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name).append(", thank you for your order!").append("\n").append(" Order summary: ");
        for(String elem:productsAndQuantities){
            stringBuilder.append(elem).append("\n");
        }
        stringBuilder.append("Total: ").append(total).append("\n");
        stringBuilder.append("Shipping information: ").append("\n"+name).append("\n"+phone).append("\n"+address).append("\n");
        stringBuilder.append("Thank you for choosing our shop!");
        return stringBuilder.toString();
    }



}
