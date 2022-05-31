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

public class Product_Model {

    public SimpleIntegerProperty productID = new SimpleIntegerProperty();
    public SimpleIntegerProperty productQuantity = new SimpleIntegerProperty();
    public SimpleStringProperty productName = new SimpleStringProperty();
    public SimpleStringProperty productStatus = new SimpleStringProperty();
    

    public Product_Model() {
    }

    public Integer getProductID() {
        return productID.getValue();
    }

    public Integer getProductQuantity() {
        return productQuantity.getValue();
    }

    public String getProductStatus() {
        return productStatus.getValue();
    }

    public String getProductName() {
        return productName.getValue();
    }

    Connector connector = new Connector();
    Connection conn = connector.connect();
    private Statement statement = null;

    public void createProductsTable() {
        try {

            Statement st = conn.createStatement();
            String sql = "CREATE TABLE product (productId int NOT NULL AUTO_INCREMENT, productQuantity int NOT NULL,productName VARCHAR(255), productStatus VARCHAR(255),productDescription VARCHAR(255),productPrice double, PRIMARY KEY(productId))";
            st.executeUpdate(sql);
            System.out.println("Created product table in given database...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
