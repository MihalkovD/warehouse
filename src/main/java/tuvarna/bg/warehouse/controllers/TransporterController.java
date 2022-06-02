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
import tuvarna.bg.warehouse.models.Orders_Model;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


public class TransporterController implements Initializable {

    public static String sessionUser = null;
    @FXML
    private Button refreshButton, BacktoProfileButton, logout, confirmButton;

    @FXML
    private TableView<Orders_Model> pendingOrdersDetails;

    @FXML
    TableColumn<Orders_Model, Integer> orderIdDetail;
    @FXML
    TableColumn<Orders_Model, Integer> customerIdDetail;
    @FXML
    TableColumn<Orders_Model, Integer> productIdDetail;
    @FXML
    TableColumn<Orders_Model, Integer> orderedQuantityDetail;

    @FXML
    TableColumn<Orders_Model, String> orderStatusDetail;

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
    private TextField OrdertfProductQuantity;
    @FXML
    private Label processOrderLabel;
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
    private void BacktoProfileButtonOnClick(Event event) {
        try {
            FXMLLoader fxload = new FXMLLoader();
            fxload.setLocation(getClass().getResource("/tuvarna/bg/warehouse/views/EmployeeLogin.fxml"));
            fxload.load();
            Parent parent = fxload.getRoot();
            ((Node) event.getSource()).getScene().getWindow().hide();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Employee Panel");
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            //Logger.getLogger(AddAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        orderIdDetail.setCellValueFactory(new PropertyValueFactory<Orders_Model, Integer>("orderId"));
        customerIdDetail.setCellValueFactory(new PropertyValueFactory<Orders_Model, Integer>("customerId"));
        productIdDetail.setCellValueFactory(new PropertyValueFactory<Orders_Model, Integer>("productId"));
        orderedQuantityDetail.setCellValueFactory(new PropertyValueFactory<Orders_Model, Integer>("orderQunatity"));
        orderStatusDetail.setCellValueFactory(new PropertyValueFactory<Orders_Model, String>("orderStatus"));

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

            String SQL = "Select * from orders where orderStatus = 'Processed';";

            ResultSet rs = statement.executeQuery(SQL);

            while (rs.next()) {
                Orders_Model cm = new Orders_Model();
                //System.out.println("rs id"+rs.getInt("adminId"));
                cm.orderId.set(rs.getInt("orderId"));
                cm.customerId.set(rs.getInt("customerId"));
                cm.productId.set(rs.getInt("productId"));
                cm.orderQunatity.set(rs.getInt("productQuantity"));
                cm.orderStatus.set(rs.getString("orderStatus"));
                data.add(cm);
            }

            pendingOrdersDetails.setItems(data);
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
        if (pendingOrdersDetails.getSelectionModel().getSelectedItems().size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You need to select at least one row!", ButtonType.OK);
            alert.showAndWait();

            return false;
        } else {
            return true;
        }
    }

    @FXML
    private void processOrderButtonOnClick(Event event) {
        if (ifRowSelected()) {
            getRowDetails();
            confirmButton.setDisable(false);
            //uniqueRandomOrderID();
            //getCustomerDetails();
            processOrderLabel.setText("");

        }

    }

    public void getRowDetails() {
        try {
            TablePosition pos = pendingOrdersDetails.getSelectionModel().getSelectedCells().get(0);
            int row = pos.getRow();

            Orders_Model item = pendingOrdersDetails.getItems().get(row);
            int orderID = item.getOrderId();
            //System.out.println("admin id: " + adminID);
            connection = conn.connect();
            statement = connection.createStatement();

            //String sql = "Select * from admin where adminId=" + adminID + ";";
            //String sql = "Select * from product where productId=1;";
            String sql = "Select * from orders where orderId=" + orderID + ";";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {

                OrdertfName.setText(rs.getString("customerName"));
                OrdertfCustomerId.setText(rs.getString("customerId"));
                OrdertfCustomerEmailId.setText(rs.getString("customerEmailId"));
                OrdertfId.setText(String.valueOf(rs.getInt("orderId")));
                OrdertfCustomerAddress.setText(rs.getString("customerAddress"));
                OrdertfProductId.setText(String.valueOf(rs.getInt("productId")));
                OrdertfProductDescription.setText(rs.getString("productDescription"));
                OrdertfProductName.setText(rs.getString("productName"));
                OrdertfProductQuantity.setText(String.valueOf(rs.getInt("productQuantity")));
                //productQuantity = rs.getInt("productQuantity");
            }
            connection.close();
            statement.close();

            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            //Logger.getLogger(AddAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void confirmButtonOnClick(Event event) {
        try {
            connection = conn.connect();
            statement = connection.createStatement();

            processOrderLabel.setTextFill(Color.web("green"));
            processOrderLabel.setText("The Order has been Shipped!!");

            String updateStatus = "update orders set orderStatus = 'Shipped' where orderId=" + Integer.parseInt(OrdertfId.getText()) + ";";
            statement.executeUpdate(updateStatus);
            setAllFieldClearOnClick();
            confirmButton.setDisable(true);
            refreshButtonOnClick(event);
            connection.close();
            statement.close();

        } catch (SQLException e) {
            processOrderLabel.setTextFill(Color.web("red"));
            processOrderLabel.setText("Order not placed. Contact Admin ");
            System.out.println("Order not placed");
            e.printStackTrace();
        }
    }

}
