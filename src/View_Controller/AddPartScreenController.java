package View_Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import View_Controller.MainScreenController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

import static View_Controller.MainScreenController.checkPos;
import static View_Controller.MainScreenController.errorStack;

public class AddPartScreenController {

    Stage stage;
    Parent scene;

    @FXML
    private AnchorPane pane;

    @FXML
    private RadioButton partInHouseRadio;

    @FXML
    private ToggleGroup partRadioGroup;

    @FXML
    private RadioButton partOutsourcedRadio;

    @FXML
    private Label partIDLabel;

    @FXML
    private Label partNameLabel;

    @FXML
    private Label PartInvLabel;

    @FXML
    private Label partPriceLabel;

    @FXML
    private TextField partIDField;

    @FXML
    private TextField partNameField;

    @FXML
    private TextField partInvField;

    @FXML
    private TextField partPriceField;

    @FXML
    private Button partSaveButton;

    @FXML
    private Button partCancelButton;

    @FXML
    private Label partMaxLabel;

    @FXML
    private TextField partMaxField;

    @FXML
    private Label partMinLabel;

    @FXML
    private TextField partMinField;

    @FXML
    private Label partUniqueLabel;

    @FXML
    private TextField partUniqueField;

    @FXML
    void partCancelHandler(MouseEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void partSaveHandler(MouseEvent event) throws IOException {

// Clear previous validation errors
        while(!errorStack.empty()) {
            TextField t = errorStack.pop();
            t.setStyle("-fx-border-color: #999999");
        }

        // Validate Input
        MainScreenController.checkInt(partInvField);
        MainScreenController.checkDbl(partPriceField);
        MainScreenController.checkInt(partMinField);
        MainScreenController.checkInt(partMaxField);

        try {
            int id = Inventory.incPartID();
            String name = partNameField.getText();
            int stock = Integer.parseInt(partInvField.getText());
            checkPos(stock);
            double price = Double.parseDouble(partPriceField.getText());
            checkPos(price);
            int min = Integer.parseInt(partMinField.getText());
            checkPos(min);
            int max = Integer.parseInt(partMaxField.getText());
            checkPos(max);

            if (partInHouseRadio.isSelected()) {
                int machineID = Integer.parseInt(partUniqueField.getText());
                checkPos(machineID);
                Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineID));
            } else {
                String companyName = partUniqueField.getText();
                Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
            }
            MainScreenController.checkMaxMin(partMaxField, partMinField, Integer.parseInt(partInvField.getText()));
            MainScreenController.checkStock(partInvField, min, max);
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View_Controller/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input Type");
            alert.setContentText("Please check input for errors");
            alert.showAndWait();
        }
        catch (IllegalArgumentException e) {

        }


    }

    @FXML
    void partInHouseRadioHandler(ActionEvent event) {
            partUniqueLabel.setText("Machine ID");
            partUniqueField.setPromptText("Mach ID");
    }

    @FXML
    void partOutsourcedRadioHandler(ActionEvent event) {
            partUniqueLabel.setText("Company Name");
            partUniqueField.setPromptText("Comp Nm");
    }

}
