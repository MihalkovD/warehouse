package tuvarna.bg.warehouse.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Customer_AvailableModel {
	
	public  SimpleIntegerProperty productID = new SimpleIntegerProperty();
	   public  SimpleIntegerProperty availQuant= new SimpleIntegerProperty();
	   public  SimpleStringProperty proName= new SimpleStringProperty();
	   public  SimpleStringProperty ProStatus= new SimpleStringProperty();
	   public  SimpleStringProperty ProDesc= new SimpleStringProperty();

	   public  SimpleStringProperty ProPrice= new SimpleStringProperty();
	   
	   
	   
	   
	   public Customer_AvailableModel() {
	    }
	   
	   public int getProductID() {
		return productID.getValue();
	}

	

	public int getAvailQuant() {
		return availQuant.getValue();
	}
	
	
	public String getProName() {
		return proName.getValue();
	}
	
	public String getProStatus() {
		return ProStatus.getValue();
	}
	
	
	public String getProDesc() {
		return ProDesc.getValue();
	}

	public String getProPrice() {
		return ProPrice.getValue();
	}
	
	public void setProductID(SimpleIntegerProperty productID) {
		this.productID = productID;
	}

	public void setAvailQuant(SimpleIntegerProperty availQuant) {
		this.availQuant = availQuant;
	}

	public void setProName(SimpleStringProperty proName) {
		this.proName = proName;
	}

	public void setProStatus(SimpleStringProperty proStatus) {
		ProStatus = proStatus;
	}

	public void setProDesc(SimpleStringProperty proDesc) {
		ProDesc = proDesc;
	}

	public void setProPrice(SimpleStringProperty proPrice) {
		ProPrice = proPrice;
	}

}



 

