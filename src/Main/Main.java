package Main;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/MainScreen.fxml"));
        Scene scene = new Scene(root, 1200, 500);
        scene.getStylesheets().add("/View_Controller/CSS.css");
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {

        Product prodTest = new Product(1, "test1", 5.00, 2, 1, 3);
        Inventory.incProductID();
        Product prodTest2 = new Product(2, "test2", 5.00, 2, 1, 3);
        Inventory.incProductID();
        InHouse inTest = new InHouse(1,"test1", 6.00, 3, 2, 4, 100);
        Inventory.incPartID();
        Outsourced outTest = new Outsourced(2, "test2", 8.00, 5, 3, 20, "ABC Company");
        Inventory.incPartID();

        Inventory.addPart(inTest);
        Inventory.addPart(outTest);
        Inventory.addProduct(prodTest);
        Inventory.addProduct(prodTest2);

        launch(args);


    }
}
