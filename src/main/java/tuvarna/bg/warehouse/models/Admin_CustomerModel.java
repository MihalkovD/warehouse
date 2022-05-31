package tuvarna.bg.warehouse.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Connection;
import java.sql.Statement;

public class Admin_CustomerModel {
	
	public  SimpleIntegerProperty customerID = new SimpleIntegerProperty();
	   public  SimpleStringProperty customerFName= new SimpleStringProperty();
	   public  SimpleStringProperty customerLName= new SimpleStringProperty();
	   public  SimpleStringProperty customerAddress= new SimpleStringProperty();

    public Admin_CustomerModel() {
    }

	public int getCustomerID() {
		return customerID.getValue();
	}

	public void setCustomerID(SimpleIntegerProperty customerID) {
		this.customerID = customerID;
	}

	public String getCustomerFName() {
		return customerFName.getValue();
	}

	public void setCustomerFName(SimpleStringProperty customerFName) {
		this.customerFName = customerFName;
	}

	public String getCustomerLName() {
		return customerLName.getValue();
	}

	public void setCustomerLName(SimpleStringProperty customerLName) {
		this.customerLName = customerLName;
	}

	public String getCustomerAddress() {
		return customerAddress.getValue();
	}

	public void setCustomerAddress(SimpleStringProperty customerAddress) {
		this.customerAddress = customerAddress;
	}


	Connector connector = new Connector();
	Connection conn = connector.connect();
	private Statement statement = null;

	public void createCustomerTable() {
		try {

			Statement st = conn.createStatement();
			String sql = "CREATE TABLE customer (customerId int NOT NULL AUTO_INCREMENT, customerFirstName VARCHAR(255), customerLastName VARCHAR(255),customerUserName VARCHAR(255), customerPassword VARCHAR(255),customerEmailId VARCHAR(255),customerAddress VARCHAR(255), PRIMARY KEY(customerId))";
			st.executeUpdate(sql);
			System.out.println("Created customer table in given database...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

