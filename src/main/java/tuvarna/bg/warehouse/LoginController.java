package tuvarna.bg.warehouse;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tuvarna.bg.warehouse.controllers.AdminController;
import tuvarna.bg.warehouse.controllers.CustomerController;
import tuvarna.bg.warehouse.controllers.EmployeeController;
import tuvarna.bg.warehouse.controllers.ManagerController;
import tuvarna.bg.warehouse.models.Connector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class LoginController {

    @FXML
    private TextField tfEmailID;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private ChoiceBox cbUser;
    @FXML
    private Button loginButton;

    private static String sessionUser = null;
    private static Node adminNode;


    @FXML
    private void loginButtonClick(Event event) throws SQLException {
        if (isAllFieldFillup()) {
            String userName = tfEmailID.getText().trim();
            String password = pfPassword.getText();
            String userType = cbUser.getValue().toString().trim();
            //System.out.println(userType);
            switch (userType) {
                case "Admin":
                    if (isValidCredentials(userType, userName, password, "Email")) {
                        try {
                            sessionUser = userName;
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("views/Admin.fxml"));
                            Parent adminParent = FXMLLoader.load(getClass().getResource("views/Admin.fxml"));
                            Scene adminScene = new Scene(adminParent);
                            Stage adminStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            //adminNode = (Node) event.getSource();

                            adminStage.hide();
                            adminStage.setScene(adminScene);
                            adminStage.setTitle("Admin Panel");
                            adminStage.show();
                            AdminController controller = loader.<AdminController>getController();
                            //controller.adminNode = this.adminNode;
                            controller.sessionUser = this.sessionUser;

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                break;
                case "Warehouse Employee":
                    userType="employee";
                    if (isValidCredentials(userType, userName, password, "Email")) {
                        try {
                            sessionUser = userName;
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EmployeeLogin.fxml"));
                            Parent adminParent = FXMLLoader.load(getClass().getResource("/views/EmployeeLogin.fxml"));
                            Scene adminScene = new Scene(adminParent);
                            Stage adminStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            //adminNode = (Node) event.getSource();

                            adminStage.hide();
                            adminStage.setScene(adminScene);
                            adminStage.setTitle("Employee Panel");
                            adminStage.show();
                            EmployeeController controller = loader.<EmployeeController>getController();
                            //controller.adminNode = this.adminNode;
                            controller.sessionUser = this.sessionUser;

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                break;
                case "Warehouse Manager":
                    userType="manager";
                    if (isValidCredentials(userType, userName, password, "Email")) {
                        try {
                            sessionUser = userName;
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ManagerLogin.fxml"));
                            Parent adminParent = FXMLLoader.load(getClass().getResource("/views/ManagerLogin.fxml"));
                            Scene adminScene = new Scene(adminParent);
                            Stage adminStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            //adminNode = (Node) event.getSource();

                            adminStage.hide();
                            adminStage.setScene(adminScene);
                            adminStage.setTitle("Manager Panel");
                            adminStage.show();

                            ManagerController controller = loader.<ManagerController>getController();
                            //controller.adminNode = this.adminNode;
                            System.out.println("LoginC"+sessionUser);
                            
                            ManagerController.sessionUser = sessionUser;
                            //System.out.println("manager C:"+controller.sessionUser);
                           
                            
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                break;

                case "Customer":
                    userType="Customer";
                    if (isValidCredentials(userType, userName, password, "Email")) {
                        try {
                            sessionUser = userName;
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Customer.fxml"));
                            Parent adminParent = FXMLLoader.load(getClass().getResource("/views/Customer.fxml"));
                            Scene adminScene = new Scene(adminParent);
                            Stage adminStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            //adminNode = (Node) event.getSource();

                            adminStage.hide();
                            adminStage.setScene(adminScene);
                            adminStage.setTitle("Customer Panel");
                            adminStage.show();
                            
                           //AdminController controller = loader.<AdminController>getController();
                            //controller.adminNode = this.adminNode;
                            //controller.sessionUser = this.sessionUser;
                            
                           CustomerController controller = loader.<CustomerController>getController();
                           // controller.adminNode = this.adminNode;
                            controller.sessionUser = this.sessionUser;
                            //System.out.println("LoginC"+sessionUser);
                            
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    		}
                             
                    break;
                case "Transporter":
                    userType="Transporter";
                    if (isValidCredentials(userType, userName, password, "Email")) {
                        try {
                            sessionUser = userName;
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/TransporterLogin.fxml"));
                            Parent adminParent = FXMLLoader.load(getClass().getResource("/views/TransporterLogin.fxml"));
                            Scene adminScene = new Scene(adminParent);
                            Stage adminStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            //adminNode = (Node) event.getSource();

                            adminStage.hide();
                            adminStage.setScene(adminScene);
                            adminStage.setTitle("Transporter Panel");
                            adminStage.show();
                            
                           //AdminController controller = loader.<AdminController>getController();
                            //controller.adminNode = this.adminNode;
                            //controller.sessionUser = this.sessionUser;
                            
                           CustomerController controller = loader.<CustomerController>getController();
                           // controller.adminNode = this.adminNode;
                            controller.sessionUser = this.sessionUser;
                            //System.out.println("LoginC"+sessionUser);
                            
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    		}
                    
                    
                    
                    
            }
        }
    }
                  
    

    private boolean isValidCredentials(String userType, String userName, String password, String loginType) throws SQLException {
        boolean userPassOk = false;

        Connector conn = new Connector();
        Connection connection = conn.connect();
        Statement statement = connection.createStatement();
        /*ResultSet resultSet = statement.executeQuery("select * from "+userType.toLowerCase()+" where db"+userType
         +loginType+" = '"+userName+"' and db"+userType+"Password = '"+password+"';");*/
        ResultSet resultSet = statement.executeQuery("select * from " + userType.toLowerCase() + " where " + userType + "UserName = '" + userName + "' and " + userType + "Password = '" + password + "';");
        //ResultSet resultSet = statement.executeQuery("select * from admin where adminUserName = '"+userName+"' and adminPassword = '"+password+"';");

        while (resultSet.next()) {

            if (resultSet.getString(userType + "UserName") != null && resultSet.getString(userType + "Password") != null) {
                userPassOk = true;
            }

        }

        if (!userPassOk) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Incorrect email or password", ButtonType.OK);
            alert.showAndWait();

            tfEmailID.clear();
            pfPassword.clear();

            userPassOk = false;

        }

        return userPassOk;
    }

    private boolean isAllFieldFillup() {
        boolean fillup;
        if (tfEmailID.getText().trim().isEmpty() || pfPassword.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Email or Password should not Empty.", ButtonType.OK);
            alert.showAndWait();

            fillup = false;
        } else {
            fillup = true;
        }
        return fillup;
    }
}
