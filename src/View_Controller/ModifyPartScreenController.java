package View_Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;
import java.util.Stack;

import static View_Controller.MainScreenController.checkPos;
import static View_Controller.MainScreenController.errorStack;

public class ModifyPartScreenController {

    Stage stage;
    Parent scene;
    String[] unique;

    @FXML
    private AnchorPane pane;

    @FXML
    private RadioButton partInHouseRadio;

    @FXML
    private ToggleGroup partRadioGroup;

    @FXML
    private RadioButton partOutsourcedRadio;

    @FXML
    private Label partUniqueLabel;

    @FXML
    private TextField partMaxField;

    @FXML
    private Label partMinLabel;

    @FXML
    private TextField partMinField;

    @FXML
    private Label partIDLabel;

    @FXML
    private Label partNameLabel;

    @FXML
    private Label PartInvLabel;

    @FXML
    private Label partPriceLabel;

    @FXML
    private Label partMaxLabel;

    @FXML
    private Label partCompanyLabel;

    @FXML
    private TextField partIDField;

    @FXML
    private TextField partNameField;

    @FXML
    private TextField partInvField;

    @FXML
    private TextField partPriceField;

    @FXML
    private TextField partUniqueField;

    @FXML
    private Button partSaveButton;

    @FXML
    private Button partCancelButton;

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
        MainScreenController.checkInt(partIDField);
        MainScreenController.checkInt(partInvField);
        MainScreenController.checkDbl(partPriceField);
        MainScreenController.checkInt(partMinField);
        MainScreenController.checkInt(partMaxField);

        try {
            int id = Integer.parseInt(partIDField.getText());
            checkPos(id);
            String name = partNameField.getText();
            double price = Double.parseDouble(partPriceField.getText());
            checkPos(price);
            int stock = Integer.parseInt(partInvField.getText());
            checkPos(stock);
            int min = Integer.parseInt(partMinField.getText());
            checkPos(min);
            int max = Integer.parseInt(partMaxField.getText());
            checkPos(max);

            if (partInHouseRadio.isSelected()) {
                int machineID = Integer.parseInt(partUniqueField.getText());
                checkPos(machineID);
                try {
                    updatePart(id, new InHouse(id, name, price, stock, min, max, machineID)) ;
                }
                catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Part does not exist in Inventory, new part will be created using the next auto-generated ID");
                    Optional<ButtonType> confirm = alert.showAndWait();
                    if(confirm.isPresent() && confirm.get() == ButtonType.OK) {
                        id = Inventory.incPartID();
                        Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineID));
                    }
                    else {
                        partIDField.setStyle("-fx-border-color: red");
                        errorStack.push(partIDField);
                        throw new IllegalArgumentException("Please enter part number from an existing part");
                    }
                }
            } else {
                String companyName = partUniqueField.getText();
                try {
                    updatePart(id, new Outsourced(id, name, price, stock, min, max, companyName)) ;
                }
                catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Part does not exist in Inventory, new part will be created using the next auto-generated ID");
                    Optional<ButtonType> confirm = alert.showAndWait();
                    if(confirm.isPresent() && confirm.get() == ButtonType.OK) {
                        id = Inventory.incPartID();
                        Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
                    }
                    else {
                        partIDField.setStyle("-fx-border-color: red");
                        errorStack.push(partIDField);
                        throw new IllegalArgumentException("Please enter part number from an existing part");
                    }
                }
            }
            MainScreenController.checkMaxMin(partMaxField, partMinField, Integer.parseInt(partInvField.getText()));
            MainScreenController.checkStock(partInvField, min, max);
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
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

    public void sendPart(Part part) {

        partIDField.setText(String.valueOf(part.getId()));
        partNameField.setText((part.getName()));
        partInvField.setText(String.valueOf(part.getStock()));
        partPriceField.setText(String.valueOf(part.getPrice()));
        partMaxField.setText((String.valueOf(part.getMax())));
        partMinField.setText((String.valueOf(part.getMin())));
        if(part instanceof InHouse) {
            partInHouseRadio.setSelected(true);
            partUniqueLabel.setText("Machine ID");
            partUniqueField.setText(String.valueOf(((InHouse) part).getMachineID()));
            unique = new String[]{"InHouse", partUniqueField.getText()};
        }
        else {
            partOutsourcedRadio.setSelected(true);
            partUniqueLabel.setText("Company Name");
            partUniqueField.setText(((Outsourced) part).getCompanyName());
            unique = new String[]{"Outsourced", partUniqueField.getText()};
        }


    }
    @FXML
    void partInHouseRadioHandler(ActionEvent event) {
        partUniqueLabel.setText("Machine ID");
        partUniqueField.clear();
        partUniqueField.setPromptText("Machine ID");
        if(unique[0].equals("InHouse")) partUniqueField.setText(unique[1]);
    }

    @FXML
    void partOutsourcedRadioHandler(ActionEvent event) {
        partUniqueLabel.setText("Company Name");
        partUniqueField.clear();
        partUniqueField.setPromptText("Company Name");
        if(unique[0].equals("Outsourced")) partUniqueField.setText(unique[1]);
    }

    public void updatePart(int partId, Part part) throws NoSuchFieldException {
        int index = -1;
        for (Part p : Inventory.getAllParts()) {
            index++;
            if(p.getId() == partId) {
                Inventory.getAllParts().set(index, part);
                return;
            }
        }
        throw new NoSuchFieldException("Part Does Not Exist");
    }
}
