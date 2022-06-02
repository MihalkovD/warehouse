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
import tuvarna.bg.warehouse.models.Orders_Model;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;


public class ViewCustomerOrdersController implements Initializable {

    @FXML
    private Button backToManagerButton, logout;

    @FXML
    private TableView<Orders_Model> customerOrdersDetails;

    @FXML
    TableColumn<Orders_Model, Integer> orderIdDetail;
    @FXML
    TableColumn<Orders_Model, Integer> customerIdDetail;
    @FXML
    TableColumn<Orders_Model, Integer> productIdDetail;
    @FXML
    TableColumn<Orders_Model, Integer> orderQuantityDetail;
    @FXML
    TableColumn<Orders_Model, String> orderStatusDetail;
//    @FXML
//    TableColumn<OrdersPOJO, String> orderHandledByDetail;
//    @FXML
//    TableColumn<OrdersPOJO, String> transporterIdDetail;
    @FXML
    TableColumn<Orders_Model, String> customerNameDetail;
    @FXML
    TableColumn<Orders_Model, String> productNameDetail;

    private Connector conn = new Connector();
    private Connection connection;
    private Statement statement;
    private ResultSet rslt;

    @FXML
    private void backToManagerButtonOnClick(Event event) {
        try {
            FXMLLoader fxload = new FXMLLoader();
            fxload.setLocation(getClass().getResource("/tuvarna/bg/warehouse/views/ManagerLogin.fxml"));
            fxload.load();
            Parent parent = fxload.getRoot();
            ((Node) event.getSource()).getScene().getWindow().hide();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Manager Panel");
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        orderIdDetail.setCellValueFactory(new PropertyValueFactory<Orders_Model, Integer>("orderId"));
        customerIdDetail.setCellValueFactory(new PropertyValueFactory<Orders_Model, Integer>("customerId"));
        productIdDetail.setCellValueFactory(new PropertyValueFactory<Orders_Model, Integer>("productId"));
        orderQuantityDetail.setCellValueFactory(new PropertyValueFactory<Orders_Model, Integer>("orderQunatity"));
        orderStatusDetail.setCellValueFactory(new PropertyValueFactory<Orders_Model, String>("orderStatus"));
        //orderHandledByDetail.setCellValueFactory(new PropertyValueFactory<OrdersPOJO, String>("employeeName")); 
        //transporterIdDetail.setCellValueFactory(new PropertyValueFactory<OrdersPOJO, String>("transporterName"));
        customerNameDetail.setCellValueFactory(new PropertyValueFactory<Orders_Model, String>("customerName"));
        productNameDetail.setCellValueFactory(new PropertyValueFactory<Orders_Model, String>("productName"));
        try {
            buildData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ObservableList<Orders_Model> data;

    public void buildData() {
        try {
            data = FXCollections.observableArrayList();
            connection = conn.connect();
            statement = connection.createStatement();

            String SQL = "Select * from orders;";

            ResultSet rs = statement.executeQuery(SQL);

            while (rs.next()) {
                Orders_Model cm = new Orders_Model();
                //System.out.println("rs id"+rs.getInt( "adminId"));
                cm.orderId.set(rs.getInt("orderId"));
                cm.customerId.set(rs.getInt("customerId"));
                cm.productId.set(rs.getInt("productId"));
                cm.orderQunatity.set(rs.getInt("productQuantity"));
                cm.orderStatus.set(rs.getString("orderStatus"));
                cm.customerName.set(rs.getString("customerName"));
                cm.productName.set(rs.getString("productName"));
//                cm.employeeName.set(rs.getString("employeeName"));
//                cm.transporterName.set(rs.getString("transporterName"));
                data.add(cm);
            }

            customerOrdersDetails.setItems(data);
            connection.close();
            statement.close();

            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

}
