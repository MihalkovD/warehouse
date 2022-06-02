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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tuvarna.bg.warehouse.models.Connector;
import tuvarna.bg.warehouse.models.Customer_TrackOrdersModel;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Customer_TrackOrdersController implements Initializable{
	
	public static String sessionUser = null;
    public boolean isEdit;
    private static int count;
    CustomerController Cuser = new CustomerController();

    public static String getSessionUser() {
        return sessionUser;
    }

    public static void setSessionUser(String sessionUser) {
        Customer_TrackOrdersController.sessionUser = sessionUser;
    }
   
	 
	 @FXML
	    private Button refreshButton;
	 
	@FXML
    private Button logout;
	
	@FXML
    private Button BacktoProfile;
 
    @FXML
    private TableView<Customer_TrackOrdersModel> Orderstableview;

    @FXML
    TableColumn<Customer_TrackOrdersModel, Integer> OrderTableId;
    @FXML
    TableColumn<Customer_TrackOrdersModel, String>OrderTableCustomerAddress;
    
    @FXML
    TableColumn<Customer_TrackOrdersModel, Integer> OrderTableProductId;
    @FXML
    TableColumn<Customer_TrackOrdersModel, String> OrderTableProductDescription;
    @FXML
    TableColumn<Customer_TrackOrdersModel, String> OrderTableProductname;
    @FXML
    TableColumn<Customer_TrackOrdersModel, Integer>OrderTableOrderedQuantity;
    @FXML
    TableColumn<Customer_TrackOrdersModel, String>OrderTableProductPrice;
    @FXML
    TableColumn<Customer_TrackOrdersModel, String>orderStatusDetail;
   
       
    private Connector conn = new Connector();
    private Connection connection;
    private Statement statement;
    private ResultSet rslt;
    
    
    
    
    
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
		OrderTableId.setCellValueFactory(new PropertyValueFactory<Customer_TrackOrdersModel, Integer>("ordID"));
		OrderTableCustomerAddress.setCellValueFactory(new PropertyValueFactory<Customer_TrackOrdersModel, String>("cusaddress"));
		OrderTableProductId.setCellValueFactory(new PropertyValueFactory<Customer_TrackOrdersModel, Integer>("pordID"));
		OrderTableProductDescription.setCellValueFactory(new PropertyValueFactory<Customer_TrackOrdersModel, String>("ProdDescp"));
		OrderTableProductname.setCellValueFactory(new PropertyValueFactory<Customer_TrackOrdersModel, String>("prodName"));
		OrderTableOrderedQuantity.setCellValueFactory(new PropertyValueFactory<Customer_TrackOrdersModel, Integer>("pordQuant"));
        OrderTableProductPrice.setCellValueFactory(new PropertyValueFactory<Customer_TrackOrdersModel, String>("prodPrice"));
        orderStatusDetail.setCellValueFactory(new PropertyValueFactory<Customer_TrackOrdersModel, String>("orderStatus"));
		try {
	            //buildData();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	    
		
	}
    
   
	private ObservableList<Customer_TrackOrdersModel> data;
private String currentUser;
    public void buildData() {
        try {
            data = FXCollections.observableArrayList();
            connection = conn.connect();
            statement = connection.createStatement();
            //System.out.println("track orders:"+sessionUser);
            String customerNameSql = "select customerFirstName from customer where customerUserName='"+sessionUser+"';";
            ResultSet rs1 = statement.executeQuery(customerNameSql);
            while(rs1.next()){
                currentUser = rs1.getString("customerFirstName");
            }
            String SQL = "Select * from orders where customerName='"+currentUser+"';";

            ResultSet rs = statement.executeQuery(SQL);

            while (rs.next()) {
            	Customer_TrackOrdersModel cm = new Customer_TrackOrdersModel();
                //System.out.println("rs id"+rs.getInt("adminId"));
                cm.ordID.set(rs.getInt("orderId"));
                cm.cusaddress.set(rs.getString("customerAddress"));
                cm.pordID.set(rs.getInt("productId"));
                cm.ProdDescp.set(rs.getString("productDescription"));
                cm.prodName.set(rs.getString("productName"));
                cm.pordQuant.set(rs.getInt("productQuantity"));
                cm.prodPrice.set(rs.getString("productPrice"));
                cm.orderStatus.set(rs.getString("orderStatus"));
                data.add(cm);
            }

            Orderstableview.setItems(data);
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
    
   

}
