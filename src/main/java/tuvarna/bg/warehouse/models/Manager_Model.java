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

public class Manager_Model {
    public SimpleIntegerProperty mngID = new SimpleIntegerProperty();
    public SimpleStringProperty FName = new SimpleStringProperty();
    public SimpleStringProperty LName = new SimpleStringProperty();
    public SimpleStringProperty department = new SimpleStringProperty();

    public Manager_Model() {
    }

    public int getManagerID() {
        return mngID.getValue();
    }

    public String getFName() {
        return FName.getValue();
    }

    public String getLName() {
        return LName.getValue();
    }

    public String getDepartment() {
        return department.getValue();
    }

    Connector connector = new Connector();
    Connection conn = connector.connect();
    private Statement statement = null;

    public void createManagerTable() {
        try {

            Statement st = conn.createStatement();
            // String sql = "CREATE TABLE admin (adminId int NOT NULL AUTO_INCREMENT, adminFirstName VARCHAR(255), adminLastName VARCHAR(255),adminUserName VARCHAR(255), adminPassword VARCHAR(255),adminEmailId VARCHAR(255),adminCity VARCHAR(255), PRIMARY KEY(adminId))";
            String sqlManager = "CREATE TABLE manager (managerId int NOT NULL AUTO_INCREMENT, managerFirstName VARCHAR(255), managerLastName VARCHAR(255),managerUserName VARCHAR(255), managerPassword VARCHAR(255),managerEmailId VARCHAR(255),managerCity VARCHAR(255),managerDepartment VARCHAR(255), PRIMARY KEY(managerId))";

            st.executeUpdate(sqlManager);
            //st.executeUpdate(sql);
            System.out.println("Created table in given database...");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}