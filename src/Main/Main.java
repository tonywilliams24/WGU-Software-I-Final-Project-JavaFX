package Main;

import Model.InHouse;
import Model.Outsourced;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static Model.Inventory.*;

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

        Product prodTest1 = new Product(incProductID(), "Product 1", 5.00, 2, 1, 3);
        Product prodTest2 = new Product(incProductID(), "Product 2", 5.00, 2, 1, 3);
        Product prodTest3 = new Product(incProductID(), "Product 3", 5.00, 2, 1, 3);
        Product prodTest4 = new Product(incProductID(), "Product 4", 5.00, 2, 1, 3);
        Product prodTest5 = new Product(incProductID(), "Product 5", 5.00, 2, 1, 3);
        Product prodTest6 = new Product(incProductID(), "Product 5", 5.00, 2, 1, 3);
        InHouse inTest1 = new InHouse(incPartID(),"InHouse1", 6.00, 3, 2, 4, 100);
        InHouse inTest2 = new InHouse(incPartID(),"InHouse2", 6.00, 3, 2, 4, 100);
        InHouse inTest3 = new InHouse(incPartID(),"InHouse3", 6.00, 3, 2, 4, 100);
        Outsourced outTest1 = new Outsourced(incPartID(), "Outsourced1", 8.00, 5, 3, 20, "ABC Company");
        Outsourced outTest2 = new Outsourced(incPartID(), "Outsourced2", 8.00, 5, 3, 20, "ABC Company");
        Outsourced outTest3 = new Outsourced(incPartID(), "Outsourced3", 8.00, 5, 3, 20, "ABC Company");
        prodTest1.addAssociatedPart(inTest1);
        prodTest2.addAssociatedPart(inTest1);
        prodTest3.addAssociatedPart(inTest1);
        prodTest4.addAssociatedPart(inTest1);
        prodTest5.addAssociatedPart(inTest1);
        prodTest6.addAssociatedPart(inTest1);
        prodTest1.addAssociatedPart(outTest1);
        prodTest2.addAssociatedPart(outTest1);
        prodTest3.addAssociatedPart(outTest1);
        prodTest4.addAssociatedPart(outTest1);
        prodTest5.addAssociatedPart(outTest1);
        prodTest6.addAssociatedPart(outTest1);
        prodTest1.addAssociatedPart(inTest1);
        prodTest2.addAssociatedPart(inTest2);
        prodTest3.addAssociatedPart(inTest3);
        prodTest4.addAssociatedPart(outTest1);
        prodTest5.addAssociatedPart(outTest2);
        prodTest6.addAssociatedPart(outTest3);
        addPart(inTest1);
        addPart(inTest2);
        addPart(inTest3);
        addPart(outTest1);
        addPart(outTest2);
        addPart(outTest3);
        addProduct(prodTest1);
        addProduct(prodTest2);
        addProduct(prodTest3);
        addProduct(prodTest4);
        addProduct(prodTest5);
        addProduct(prodTest6);

        launch(args);


    }
}
