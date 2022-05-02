package tuvarna.bg.warehouse;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tuvarna.bg.warehouse.models.*;


public class LoginMain extends Application{
 @Override
	public void start(Stage primaryStage) {
		try {
			 Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
			 primaryStage.setTitle("Login Page - Warehouse Management System");
			Scene scene = new Scene(root,650,400);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		launch(args);
		          DAOModel dao = new DAOModel();
                         // dao.createAdminTable();
		          DAOModel_Customer doaCust = new DAOModel_Customer();
		        // doaCust.createCustomerTable();
		          DAOModel_Products doapro = new DAOModel_Products();
		         // doapro.createProductsTable();
                          DAOModel_CustomerOrders daoOrder = new DAOModel_CustomerOrders();
                         // daoOrder.createOrdersTable();
                          DAOModel_Order daoOrd = new DAOModel_Order();
		        // daoOrd.createOrderTable();
		          
		          DAOModel_Transporter daotrns = new DAOModel_Transporter();
		          //daotrns.createTransporterTable();
	}
}
