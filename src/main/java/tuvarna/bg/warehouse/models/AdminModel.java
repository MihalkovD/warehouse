package tuvarna.bg.warehouse.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Connection;
import java.sql.Statement;

public class AdminModel {
	
	public  SimpleIntegerProperty adminID = new SimpleIntegerProperty();
	   public  SimpleStringProperty FName= new SimpleStringProperty();
	   public  SimpleStringProperty LName= new SimpleStringProperty();
	   public  SimpleStringProperty city= new SimpleStringProperty();

    public AdminModel() {
    }

    public int getAdminID() {
        return adminID.getValue();
    }

    public String getFName() {
        return FName.getValue();
    }

    public String getLName() {
        return LName.getValue();
    }

    public String getCity() {
        return city.getValue();
    }



    Connector connector = new Connector();
    Connection conn = connector.connect();
    private Statement statement = null;

    public void createAdminTable() {
        try {

            Statement st = conn.createStatement();
            String sql = "CREATE TABLE admin (adminId int NOT NULL AUTO_INCREMENT, adminFirstName VARCHAR(255), adminLastName VARCHAR(255),adminUserName VARCHAR(255), adminPassword VARCHAR(255),adminEmailId VARCHAR(255),adminCity VARCHAR(255), PRIMARY KEY(adminId))";
            st.executeUpdate(sql);
            System.out.println("Created table in given database...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

