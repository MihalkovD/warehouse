/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuvarna.bg.warehouse.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Connection;
import java.sql.Statement;

public class Orders_Model {
    public SimpleIntegerProperty orderId = new SimpleIntegerProperty();
    public SimpleIntegerProperty customerId = new SimpleIntegerProperty();
    public SimpleIntegerProperty productId = new SimpleIntegerProperty();
    public SimpleIntegerProperty orderQunatity = new SimpleIntegerProperty();
    public SimpleStringProperty orderStatus = new SimpleStringProperty();
    public SimpleStringProperty customerName = new SimpleStringProperty();
    public SimpleStringProperty productName = new SimpleStringProperty();
    public SimpleStringProperty customerEmail = new SimpleStringProperty();
    public SimpleStringProperty customerAddress = new SimpleStringProperty();
    public SimpleStringProperty productDescription = new SimpleStringProperty();

    public Orders_Model() {
    }

    public String getCustomerEmail() {
        return customerEmail.getValue();
    }

    public String getCustomerAddress() {
        return customerAddress.getValue();
    }

    public String getProductDescription() {
        return productDescription.getValue();
    }

    
    public Integer getOrderId() {
        return orderId.getValue();
    }

    public Integer getCustomerId() {
        return customerId.getValue();
    }

    public Integer getProductId() {
        return productId.getValue();
    }

    public Integer getOrderQunatity() {

        return orderQunatity.getValue();
    }

    public String getOrderStatus() {
        return orderStatus.getValue();
    }

    public String getCustomerName() {
        return customerName.getValue();
    }

    public String getProductName() {
        return productName.getValue();
    }


    Connector connector = new Connector();
    Connection conn = connector.connect();
    private Statement statement = null;

    public void createOrderTable() {
        try {

            Statement st = conn.createStatement();
            String sql = "CREATE TABLE orders ( customerName VARCHAR(255),customerId int , customerEmailId VARCHAR(255),orderId int ,customerAddress VARCHAR(255),productId int , productDescription VARCHAR(255),productName VARCHAR(255),productQuantity int ,orderStatus VARCHAR(255), PRIMARY KEY(orderId))";
            st.executeUpdate(sql);
            System.out.println("Created orders table in given database...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
