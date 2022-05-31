package tuvarna.bg.warehouse.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Customer_TrackOrdersModel {
	
	public  SimpleIntegerProperty ordID = new SimpleIntegerProperty();
	   public  SimpleStringProperty cusaddress= new SimpleStringProperty();
	   public  SimpleIntegerProperty pordID = new SimpleIntegerProperty();
	   public  SimpleStringProperty ProdDescp= new SimpleStringProperty();
	   public  SimpleStringProperty prodName= new SimpleStringProperty();
	   public  SimpleIntegerProperty pordQuant = new SimpleIntegerProperty();

	   public  SimpleStringProperty prodPrice = new SimpleStringProperty();
	   
	   public  SimpleStringProperty orderStatus= new SimpleStringProperty();
	   
	   
	   public Customer_TrackOrdersModel() {
	    }

    public String getOrderStatus() {
        return orderStatus.getValue();
    }
	

	public int getOrdID() {
		return ordID.getValue();
	}

	public String getCusaddress() {
		return cusaddress.getValue();
	}

	public int getPordID() {
		return pordID.getValue();
	}

	public String getProdDescp() {
		return ProdDescp.getValue();
	}

	public String getProdName() {
		return prodName.getValue();
	}

	public int getPordQuant() {
		return pordQuant.getValue();
	}

	public String getProdPrice() {
		return prodPrice.getValue();
	}


	
	
	
}

	
 

