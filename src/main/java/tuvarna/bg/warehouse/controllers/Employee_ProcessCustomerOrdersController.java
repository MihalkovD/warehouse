/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuvarna.bg.warehouse.controllers;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
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

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Employee_ProcessCustomerOrdersController implements Initializable {

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
    private BaseFont bfBold;
    private BaseFont bf;
    private int pageNumber = 0;
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

            String SQL = "Select * from orders where orderStatus = 'New';";

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
            Alert alert = new Alert(Alert.AlertType.WARNING, "You need to select at least one row!", ButtonType.OK);
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
                String pdfFilename = "test.pdf";
                createPDF(pdfFilename,rs);

            }

            connection.close();
            statement.close();

            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(AddAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void confirmButtonOnClick(Event event) {
        try {
            connection = conn.connect();
            statement = connection.createStatement();

            processOrderLabel.setTextFill(Color.web("green"));
            processOrderLabel.setText("The Order has been Processed!!");

            String updateStatus = "update orders set orderStatus = 'Processed' where orderId=" + Integer.parseInt(OrdertfId.getText()) + ";";
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
    private void createPDF (String pdfFilename,ResultSet rs){

        Document doc = new Document();
        PdfWriter docWriter = null;
        initializeFonts();

        try {
            String path = pdfFilename;
            docWriter = PdfWriter.getInstance(doc , new FileOutputStream(path));
            doc.addAuthor("betterThanZero");
            doc.addCreationDate();
            doc.addProducer();
            doc.addCreator("MySampleCode.com");
            doc.addTitle("Invoice");
            doc.setPageSize(PageSize.LETTER);

            doc.open();
            PdfContentByte cb = docWriter.getDirectContent();

            boolean beginPage = true;
            int y = 0;
            generateLayout(doc, cb);
            generateHeader(doc, cb);
            y = 615;
            generateDetail(doc, cb, 0, y, rs);
            printPageNumber(cb);

        }
        catch (DocumentException dex)
        {
            dex.printStackTrace();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if (doc != null)
            {
                doc.close();
            }
            if (docWriter != null)
            {
                docWriter.close();
            }
        }
    }

    private void generateLayout(Document doc, PdfContentByte cb) throws DocumentException {

        try {

            cb.setLineWidth(1f);

            // Invoice Header box layout
            cb.rectangle(420,700,150,60);
            cb.moveTo(420,720);
            cb.lineTo(570,720);
            cb.moveTo(420,740);
            cb.lineTo(570,740);
            cb.moveTo(480,700);
            cb.lineTo(480,760);
            cb.stroke();

            // Invoice Header box Text Headings
            createHeadings(cb,422,743,"Account No.");
            createHeadings(cb,422,723,"Invoice No.");
            createHeadings(cb,422,703,"Invoice Date");

            // Invoice Detail box layout
            cb.rectangle(20,50,550,600);
            cb.moveTo(20,630);
            cb.lineTo(570,630);
            cb.moveTo(50,50);
            cb.lineTo(50,650);
            cb.moveTo(150,50);
            cb.lineTo(150,650);
            cb.moveTo(430,50);
            cb.lineTo(430,650);
            cb.moveTo(500,50);
            cb.lineTo(500,650);
            cb.stroke();

            // Invoice Detail box Text Headings
            createHeadings(cb,22,633,"Qty");
            createHeadings(cb,52,633,"Item");
            createHeadings(cb,152,633,"Item Description");
            createHeadings(cb,432,633,"Price");

            //add the images
           // Image companyLogo = Image.getInstance("images/olympics_logo.gif");
            //companyLogo.setAbsolutePosition(25,700);
            //companyLogo.scalePercent(25);
            //doc.add(companyLogo);

        } catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private void generateHeader(Document doc, PdfContentByte cb)  {

        try {

            createHeadings(cb,200,750,"Company Name");
            createHeadings(cb,200,735,"Address Line 1");
            createHeadings(cb,200,720,"Address Line 2");
            createHeadings(cb,200,705,"City, State - ZipCode");
            createHeadings(cb,200,690,"Country");

            createHeadings(cb,482,743,"ABC0001");
            createHeadings(cb,482,723,"123456");
            createHeadings(cb,482,703,"09/26/2012");

        }

        catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private void generateDetail(Document doc, PdfContentByte cb, int index, int y, ResultSet rs)  {
        DecimalFormat df = new DecimalFormat("0.00");

        try {

            createContent(cb,48,y,String.valueOf(index+1),PdfContentByte.ALIGN_RIGHT);
            createContent(cb,52,y, rs.getString("productName" ) ,PdfContentByte.ALIGN_LEFT); //String.valueOf(index+1)
            createContent(cb,152,y, rs.getString("productDescription") ,PdfContentByte.ALIGN_LEFT);
            createContent(cb,498,y, df.format(rs.getInt("productPrice")),PdfContentByte.ALIGN_RIGHT);
        }

        catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private void createHeadings(PdfContentByte cb, float x, float y, String text){


        cb.beginText();
        cb.setFontAndSize(bfBold, 8);
        cb.setTextMatrix(x,y);
        cb.showText(text.trim());
        cb.endText();

    }

    private void printPageNumber(PdfContentByte cb){


        cb.beginText();
        cb.setFontAndSize(bfBold, 8);
        cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "Page No. " + (pageNumber+1), 570 , 25, 0);
        cb.endText();

        pageNumber++;

    }

    private void createContent(PdfContentByte cb, float x, float y, String text, int align){


        cb.beginText();
        cb.setFontAndSize(bf, 8);
        cb.showTextAligned(align, text.trim(), x , y, 0);
        cb.endText();

    }

    private void initializeFonts(){


        try {
            bfBold = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}


