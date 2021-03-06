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
import tuvarna.bg.warehouse.models.Product_Model;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;



public class ManageProductsController implements Initializable {

    @FXML
    private TextField productId;
    @FXML
    private TextField productName;
    @FXML
    private TextField productStatus;
    @FXML
    private TextField productDescription;
    @FXML
    private Spinner<Integer> productQuantity;
    @FXML
    private Label productLabel;

    @FXML
    private Button backToManagerButton, logout, viewProductsButton, editProductsButton, deleteProductsButton, refreshProductsButton,
            cancelButton, saveChangesButton, saveProductButton;

    @FXML
    private TableView<Product_Model> productDetailsTable;

    @FXML
    TableColumn<Product_Model, Integer> productIdDetail;
    @FXML
    TableColumn<Product_Model, Integer> productQuantityDetail;
    @FXML
    TableColumn<Product_Model, String> productNameDetail;
    @FXML
    TableColumn<Product_Model, String> productStatusDetail;

    private Connector conn = new Connector();
    private Connection connection;
    private Statement statement;
    private ResultSet rslt;

    @FXML
    private void setAllFieldDisableOnClick() {
        productId.setDisable(true);
        productQuantity.setDisable(true);
        productName.setDisable(true);
        productStatus.setDisable(true);
        productDescription.setDisable(true);

    }

    @FXML
    private void setAllFieldEnableOnClick() {
        //productId.setDisable(true);
        productQuantity.setDisable(false);
        productName.setDisable(false);
        //productStatus.setDisable(true);
        productDescription.setDisable(false);
    }

    @FXML
    private void setAllFieldClearOnClick() {
        productId.clear();
        productName.clear();
        productStatus.clear();
        productDescription.clear();

    }

    @FXML
    private void cancelButtonOnClick(Event event) {
        //setAllFieldDisableOnClick();
        setAllFieldClearOnClick();
        productLabel.setText("");

    }

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

    public boolean checkFieldsEmpty() {
        if (productName.getText().equals("") || productDescription.getText().equals("")) {
            //save.setDisable(true);
            productLabel.setTextFill(Color.web("red"));
            productLabel.setText("Please enter all values!");

            return true;
        } else {
            return false;
        }
    }

    @FXML
    private void saveChangesButtonOnClick(Event event) {
        if (!checkFieldsEmpty()) {
            try {
                connection = conn.connect();
                statement = connection.createStatement();
                if (productQuantity.getValue() > 0) {
                    productStatus.setText("Available");
                } else {
                    productStatus.setText("Out of Stock");
                }
                statement.executeUpdate("update product set productQuantity ='" + productQuantity.getValue() + "',productName='" + productName.getText() + "',productStatus ='" + productStatus.getText() + "',productDescription ='" + productDescription.getText() + "' where productId=" + productId.getText() + ";");
                //System.out.println("Your Details have been updated successfully!");
                productLabel.setTextFill(Color.web("green"));
                productLabel.setText("Details updated successfully!");
                setAllFieldDisableOnClick();
                setAllFieldClearOnClick();
                saveProductButton.setDisable(false);
                refreshEmployeeButtonOnClick(event);
                saveChangesButton.setDisable(true);
                connection.close();
                statement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }
    private static int count;

    @FXML
    private void saveProductButtonOnClick(Event event) {
        setAllFieldEnableOnClick();
        try {
//            System.out.println("dept:"+employeeDeptBox.getValue().toString().trim());
            connection = conn.connect();
            statement = connection.createStatement();

            if (productQuantity.getValue() > 0) {
                productStatus.setText("Available");
            } else {
                productStatus.setText("Out of Stock");
            }

            ResultSet resultSet = statement.executeQuery("select count(*) from product");
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }

            productId.setText(String.valueOf(count + 1));
            if (checkFieldsEmpty()) {

                return;
            } else {
                //save.setDisable(false);
                String sqlQuery = "insert into product values (" + productId.getText() + " , '" + productQuantity.getValue() + "','" + productName.getText() + "','" + productStatus.getText() + "','" + productDescription.getText() + "')";
                //System.out.println(sqlQuery);
                statement.executeUpdate(sqlQuery);
                productLabel.setTextFill(Color.web("green"));
                productLabel.setText("Product Saved to Database");
                setAllFieldClearOnClick();
                setAllFieldDisableOnClick();
                refreshEmployeeButtonOnClick(event);
            }
            connection.close();
            statement.close();
            resultSet.close();

        } catch (SQLException e) {
            productLabel.setText("Product Not Saved to Database");
            System.out.println("Values are not inserted in product table after save button");
            e.printStackTrace();
        }
    }

    @FXML
    private void refreshEmployeeButtonOnClick(Event event) {
        try {
            buildData();
        } catch (Exception e) {
            e.printStackTrace();
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

    public boolean ifRowSelected() {
        if (productDetailsTable.getSelectionModel().getSelectedItems().size() == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "You need to select atleast one row!", ButtonType.OK);
            alert.showAndWait();

            return false;
        } else {
            return true;
        }
    }

    public void getRowDetails() {
        try {
            TablePosition pos = productDetailsTable.getSelectionModel().getSelectedCells().get(0);
            int row = pos.getRow();

            Product_Model item = productDetailsTable.getItems().get(row);
            int productID = item.getProductID();
            //System.out.println("admin id: " + adminID);
            connection = conn.connect();
            statement = connection.createStatement();

            String sql = "Select * from product where productId=" + productID + ";";
            //String sql = "Select * from admin where adminId=1;";

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {

                productId.setText(String.valueOf(rs.getInt("productId")));
                //addAdmintfLastName.setText(rs.getString("adminLastName"));

                productName.setText(rs.getString("productName"));
                productStatus.setText(rs.getString("productStatus"));
                productDescription.setText(rs.getString("productDescription"));

            }
            connection.close();
            statement.close();

            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(AddAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void editProductsButtonOnClick(Event event) {

        try {

            if (ifRowSelected()) {
                getRowDetails();
                setAllFieldEnableOnClick();
                saveProductButton.setDisable(true);
                saveChangesButton.setDisable(false);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            //Logger.getLogger(AddAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void deleteProductsButtonOnClick(Event event) {
        if (ifRowSelected()) {
            try {
                TablePosition pos = productDetailsTable.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();
                Product_Model item = productDetailsTable.getItems().get(row);
                int productId = item.getProductID();
                //System.out.println("admin id: " + adminID);
                connection = conn.connect();
                statement = connection.createStatement();

                String sql = "delete from product where productId=" + productId + ";";
                statement.executeUpdate(sql);
                refreshEmployeeButtonOnClick(event);
                Alert alert = new Alert(Alert.AlertType.WARNING, "Product Removed from Warehouse!", ButtonType.OK);
                alert.showAndWait();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
    }

    @FXML
    private void viewProductsButtonOnClick(Event event) {
        if (ifRowSelected()) {
            getRowDetails();
            setAllFieldDisableOnClick();
            saveProductButton.setDisable(true);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productIdDetail.setCellValueFactory(new PropertyValueFactory<Product_Model, Integer>("productID"));
        productQuantityDetail.setCellValueFactory(new PropertyValueFactory<Product_Model, Integer>("productQuantity"));
        productNameDetail.setCellValueFactory(new PropertyValueFactory<Product_Model, String>("productName"));
        productStatusDetail.setCellValueFactory(new PropertyValueFactory<Product_Model, String>("productStatus"));

        try {
            buildData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ObservableList<Product_Model> data;

    public void buildData() {
        try {
            data = FXCollections.observableArrayList();
            connection = conn.connect();
            statement = connection.createStatement();

            String SQL = "Select * from product;";

            ResultSet rs = statement.executeQuery(SQL);

            while (rs.next()) {
                Product_Model cm = new Product_Model();
                //System.out.println("rs id"+rs.getInt("adminId"));
                cm.productID.set(rs.getInt("productId"));
                cm.productQuantity.set(rs.getInt("productQuantity"));
                cm.productName.set(rs.getString("productName"));
                cm.productStatus.set(rs.getString("productStatus"));

                data.add(cm);
            }

            productDetailsTable.setItems(data);
            connection.close();
            statement.close();

            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }
}
