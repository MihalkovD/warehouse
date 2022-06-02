/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuvarna.bg.warehouse.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tuvarna.bg.warehouse.models.Connector;
import tuvarna.bg.warehouse.models.Customer_AvailableModel;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Customer_PlaceOrdersController implements Initializable{
	
	public static String sessionUser = null;
    public boolean isEdit;
    private static int count;
    private int productQuantity;
    CustomerController Cuser = new CustomerController();
   
	  @FXML
	    private TextField OrdertfName;
	    @FXML
	    private TextField OrdertfCustomerId;
	    @FXML
	    private TextField OrdertfCustomerEmailId;
	    @FXML
	    private TextField OrdertfId;
	   
	    @FXML
	    private TextField OrdertfCustomerAddress;
	    @FXML
	    private TextField OrdertfProductId;
	    @FXML
	    private TextField OrdertfProductDescription;
	    @FXML
	    private TextField OrdertfProductName;
        @FXML
        private TextField OrdertfProductPrice;
	    @FXML
	    private TextField OrdertfProductQuantity;

	 @FXML
	    private Button refreshButton;
	 
	 @FXML
	    private Button viewSelectOrder;
	
	 @FXML
	    private Label PlaceOrderLabel;
	@FXML
    private Button logout;
	
	@FXML
    private Button BacktoProfile;
 
    @FXML
    private TableView<Customer_AvailableModel> availableProductstableview;

    @FXML
    TableColumn<Customer_AvailableModel, Integer> ProductIdTableColumn;
    @FXML
    TableColumn<Customer_AvailableModel, Integer> AvailableQuantityTableColumn;
    
    @FXML
    TableColumn<Customer_AvailableModel, String> ProductNameTableColumn;
    @FXML
    TableColumn<Customer_AvailableModel, String> ProductStatusTableColumn;
    @FXML
    TableColumn<Customer_AvailableModel, String> ProductDescriptionTableColumn;
    @FXML
    TableColumn<Customer_AvailableModel, String> ProductPriceTableColumn;
   
       
    private Connector conn = new Connector();
    private Connection connection;
    private Statement statement;
    private ResultSet rslt;
    
    
    private void setAllFieldDisableOnClick() {
    	OrdertfName.setDisable(true);
    	OrdertfCustomerId.setDisable(true);
    	OrdertfCustomerEmailId.setDisable(true);
    	OrdertfId.setDisable(true);
    	OrdertfCustomerAddress.setDisable(true);
    	OrdertfProductId.setDisable(true);
    	OrdertfProductDescription.setDisable(true);
    	OrdertfProductName.setDisable(true);
        OrdertfProductPrice.setDisable(true);
    	//OrdertfProductQuantity.setDisable(true);
    }
    
    private void setQuantityFieldDisableOnClick() {
    	OrdertfProductQuantity.setDisable(true);
    }
    
    
    private void setAllFieldClearOnClick() {
    	OrdertfName.clear();
    	OrdertfCustomerId.clear();
    	OrdertfCustomerEmailId.clear();
    	OrdertfId.clear();
    	OrdertfCustomerAddress.clear();
    	OrdertfProductId.clear();
    	OrdertfProductDescription.clear();
    	OrdertfProductName.clear();
    	OrdertfProductQuantity.clear();
        OrdertfProductPrice.clear();
    }
    @FXML
    private void setLogoutButtonClick(Event event) throws IOException {

        FXMLLoader fxload = new FXMLLoader();
        fxload.setLocation(getClass().getResource("/tuvarna/bg/warehouse/Login.fxml"));
        fxload.load();
        Parent parent = fxload.getRoot();
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.setTitle("Login Page - Warehouse Management System");
        stage.show();
    }
    
    
    
    
    @FXML
    private void backtoCustomerProfileOnClick(Event event) {
        try {
            FXMLLoader fxload = new FXMLLoader();
            fxload.setLocation(getClass().getResource("/tuvarna/bg/warehouse/views/Customer.fxml"));
            fxload.load();
            Parent parent = fxload.getRoot();
            ((Node) event.getSource()).getScene().getWindow().hide();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Customer Panel");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AddAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
		{
		ProductIdTableColumn.setCellValueFactory(new PropertyValueFactory<Customer_AvailableModel, Integer>("productID"));
		AvailableQuantityTableColumn.setCellValueFactory(new PropertyValueFactory<Customer_AvailableModel, Integer>("availQuant"));
		ProductNameTableColumn.setCellValueFactory(new PropertyValueFactory<Customer_AvailableModel, String>("proName"));
		ProductStatusTableColumn.setCellValueFactory(new PropertyValueFactory<Customer_AvailableModel, String>("ProStatus"));
		ProductDescriptionTableColumn.setCellValueFactory(new PropertyValueFactory<Customer_AvailableModel, String>("ProDesc"));
        ProductPriceTableColumn.setCellValueFactory(new PropertyValueFactory<Customer_AvailableModel, String>("ProPrice"));
	        try {
	            buildData();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	    
		
	}
    
   
	private ObservableList<Customer_AvailableModel> data;

    public void buildData() {
        try {
            data = FXCollections.observableArrayList();
            connection = conn.connect();
            statement = connection.createStatement();

            String SQL = "Select * from product where productStatus = 'Available';";

            ResultSet rs = statement.executeQuery(SQL);

            while (rs.next()) {
            	Customer_AvailableModel cm = new Customer_AvailableModel();
                //System.out.println("rs id"+rs.getInt("adminId"));
                cm.productID.set(rs.getInt("productId"));
                cm.availQuant.set(rs.getInt("productQuantity"));
                cm.proName.set(rs.getString("productName"));
                cm.ProStatus.set(rs.getString("productStatus"));
                cm.ProDesc.set(rs.getString("productDescription"));
                cm.ProPrice.set(rs.getString("ProductPrice"));
                data.add(cm);
            }

            availableProductstableview.setItems(data);
            connection.close();
            statement.close();

            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }
 
    @FXML
    private void refreshButtonOnClick(Event event) {
        try {
            buildData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean ifRowSelected() {
        if (availableProductstableview.getSelectionModel().getSelectedItems().size() == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "You need to select atleast one row!", ButtonType.OK);
            alert.showAndWait();

            return false;
        } else {
            return true;
        }
    }
    
    @FXML
    private void viewSelectOrderOnClick(Event event) {
        if (ifRowSelected()) {
            getRowDetails();
            uniqueRandomOrderID();
          getCustomerDetails();
          PlaceOrderLabel.setText("");
            setAllFieldDisableOnClick();
           OrdertfProductQuantity.setDisable(false);
           
        }

    }
    
    
    public void getCustomerDetails(){
    	
    	String CustomUser= Cuser.getSessionUser();
    	//System.out.println(CustomUser);
    	 isEdit = true;
         String sqlQuery = "select * FROM customer where customerUserName ='" + CustomUser + "'; ";
         try {

             connection = conn.connect();
             statement = connection.createStatement();
             rslt = statement.executeQuery(sqlQuery);
             while (rslt.next()) {
            	 OrdertfName.setText(rslt.getString("customerFirstName") );
            	 OrdertfCustomerId.setText(rslt.getString("customerId"));
            	 OrdertfCustomerEmailId.setText(rslt.getString("customerEmailId"));
                 OrdertfCustomerAddress.setText(rslt.getString("customerAddress"));
             }
             connection.close();
             statement.close();

         } catch (SQLException e) {
             e.printStackTrace();
         }

     }
    	
    
    
    
    public void getRowDetails() {
        try {
            TablePosition pos = availableProductstableview.getSelectionModel().getSelectedCells().get(0);
            int row = pos.getRow();

            Customer_AvailableModel item = availableProductstableview.getItems().get(row);
            int productID = item.getProductID();
            //System.out.println("admin id: " + adminID);
            connection = conn.connect();
            statement = connection.createStatement();

            //String sql = "Select * from admin where adminId=" + adminID + ";";
            //String sql = "Select * from product where productId=1;";
            String sql = "Select * from product where productId=" + productID + ";";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
            	
            	OrdertfProductId.setText(String.valueOf(rs.getInt("productId")));
            	OrdertfProductDescription.setText(rs.getString("productDescription"));
            	OrdertfProductName.setText(rs.getString("productName"));
                productQuantity = rs.getInt("productQuantity");
                OrdertfProductPrice.setText(rs.getString("productPrice"));
            }
            connection.close();
            statement.close();

            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(AddAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void uniqueRandomOrderID(){
    	ArrayList<Integer> numbers = new ArrayList<Integer>();
        for(int i = 0; i < 400; i++)
        {
        numbers.add(i+1);
      }
        Collections.shuffle(numbers);
       int orderNumber = numbers.get(1);
       String orderNumberAsString = Integer.toString(orderNumber);
       OrdertfId.setText(orderNumberAsString);
        
      }
    
    public boolean checkFieldsEmpty() {
        if (OrdertfName.getText().equals("") || OrdertfCustomerId.getText().equals("") || OrdertfCustomerEmailId.getText().equals("")
                || OrdertfId.getText().equals("") || OrdertfProductQuantity.getText().equals("") ||OrdertfCustomerAddress.getText().equals("") || OrdertfProductId.getText().equals("")) {
            
        	PlaceOrderLabel.setTextFill(Color.web("red"));
        	PlaceOrderLabel.setText("Please Select the Product!");

            return true;
        } else {
            return false;
        }
    }
    
    
    
  /*  private void UpdateProductQuantity(Event event) {
        if (!checkFieldsEmpty()) {
            try {
                connection = conn.connect();
                statement = connection.createStatement();

                statement.executeUpdate("update admin set adminFirstName ='" + addAdmintfFirstName.getText() + "',adminLastName='" + addAdmintfLastName.getText() + "',adminUserName ='" + addAdmintfUserName.getText() + "',adminPassword ='" + addAdminpfPassword.getText() + "',adminEmailId ='" + addAdmintfEmailID.getText() + "',adminCity='" + addAdmintfCity.getText() + "' where adminId=" + addAdmintfID.getText() + ";");
                //System.out.println("Your Details have been updated successfully!");
                addAdminSaveLabel.setTextFill(Color.web("green"));
                addAdminSaveLabel.setText("Details updated successfully!");
                setAllFieldDisableOnClick();
                setAllFieldClearOnClick();
                save.setDisable(false);
                refreshButtonOnClick(event);
                saveChanges.setDisable(true);
                connection.close();
                statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(AddAdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }*/
    
    
    @FXML
    private void setSaveplaceOrder(Event event) {
           try {

            connection = conn.connect();
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select count(*) from orders");
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }

           // addAdmintfID.setText(String.valueOf(count + 1));
            if (checkFieldsEmpty()) {

                return;
            } 
            if(!OrdertfProductQuantity.getText().matches("^[1-9][0-9]*$"))
            {
                PlaceOrderLabel.setTextFill(Color.web("red"));
                PlaceOrderLabel.setText("Quantity should be a number greater than 0");
                return;
            }
            
            if(Integer.parseInt(OrdertfProductQuantity.getText()) > productQuantity){
                PlaceOrderLabel.setTextFill(Color.web("red"));
                PlaceOrderLabel.setText("Quantity not available");
                return;
            }
           // if(OrdertfProductQuantity.getText()
                    else {
                //save.setDisable(false);
            	
                String sqlQuery = "insert into orders (customerName,customerId,customerEmailId,orderId,customerAddress,productId,productDescription,productName,productPrice, productQuantity,orderStatus )" + "values ('" + OrdertfName.getText() + "' , '" + OrdertfCustomerId.getText() + "','" + OrdertfCustomerEmailId.getText() + "','" + OrdertfId.getText() + "','" + OrdertfCustomerAddress.getText() + "','" + OrdertfProductId.getText() + "','" + OrdertfProductDescription.getText() + "','" + OrdertfProductName.getText() + "', '" + OrdertfProductPrice.getText() + "', '" + OrdertfProductQuantity.getText() + "', 'New')";
                
               // System.out.println(sqlQuery);
                
                statement.executeUpdate(sqlQuery);
                PlaceOrderLabel.setTextFill(Color.web("green"));
                PlaceOrderLabel.setText("Thank you ..!! Order Placed");
                
                String sqlQunatity = "update product set productQuantity ="+productQuantity+" - "+Integer.parseInt(OrdertfProductQuantity.getText())+" where productId="+Integer.parseInt(OrdertfProductId.getText())+";";
                statement.executeUpdate(sqlQunatity);
                productQuantity = productQuantity - Integer.parseInt(OrdertfProductQuantity.getText());
                
                if(productQuantity == 0){
                    String updateStatus ="update product set productStatus = 'Not Available' where productId="+Integer.parseInt(OrdertfProductId.getText())+";"; 
                    statement.executeUpdate(updateStatus);
                }
                
                
                
//                String sqlProductStatus = "selct * from product";
//                ResultSet rs = statement.executeQuery(sqlProductStatus);
//
//            while (rs.next()) {
//                productQuantity = rs.getInt("productQuantity");
//            }
            
                setAllFieldClearOnClick();
                setQuantityFieldDisableOnClick();
                setAllFieldDisableOnClick();
               // UpdateProductQuantity();
                
                refreshButtonOnClick(event);
            }
            connection.close();
            statement.close();
            resultSet.close();

        } catch (SQLException e) {
        	PlaceOrderLabel.setTextFill(Color.web("red"));
        	PlaceOrderLabel.setText("Order not placed. Contact Admin ");
            System.out.println("Order not placed");
            e.printStackTrace();
        }
    }


}
