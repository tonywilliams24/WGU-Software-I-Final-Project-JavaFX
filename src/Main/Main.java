package Main;

import Model.InHouse;
import Model.Outsourced;
import Model.Product;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import static Model.Inventory.*;
import static View_Controller.Utility.*;

public class Main extends Application {

    // Launches the Main Screen and handles close requests
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/MainScreen.fxml"));
        Scene scene = new Scene(root, 1200, 500);
        scene.getStylesheets().add("/View_Controller/CSS.css");
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if (!alertBox(alertType.confirmation, exit, confirmation)) {
                    try{ exitProgram(event); }
                    catch(ClassCastException e) {}
                }
            }
        });
    }


    public static void main(String[] args) {

        Product prodTest1 = new Product(incProductID(), "Plane", 100000.0, 2, 1, 3);
        Product prodTest2 = new Product(incProductID(), "Train", 50000.0, 4, 1, 20);
        Product prodTest3 = new Product(incProductID(), "Autonomous Vehicle", 10000.0, 50, 40, 100);
        Product prodTest4 = new Product(incProductID(), "House", 20000.0, 1, 0, 2);
        Product prodTest5 = new Product(incProductID(), "Television", 2000.0, 10, 1, 50);
        Product prodTest6 = new Product(incProductID(), "Computer", 2500.0, 4, 2, 33);
        InHouse inTest1 = new InHouse(incPartID(),"Processor", 1000.0, 3, 2, 4, 100);
        InHouse inTest2 = new InHouse(incPartID(),"Duct Tape", 5.0, 3, 2, 4, 200);
        InHouse inTest3 = new InHouse(incPartID(),"LCD Screen", 100.0, 3, 2, 4, 300);
        Outsourced outTest1 = new Outsourced(incPartID(), "Aluminum", 10.0, 5, 3, 20, "ABC Company");
        Outsourced outTest2 = new Outsourced(incPartID(), "Mouse", 50.0, 5, 3, 20, "XYZ Company");
        Outsourced outTest3 = new Outsourced(incPartID(), "Stairs", 400.0, 5, 3, 20, "Parts Inc");
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
        prodTest4.addAssociatedPart(outTest3);
        prodTest5.addAssociatedPart(outTest2);
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
