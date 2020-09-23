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
import javafx.stage.Stage;

import java.io.IOException;

public class ModifyPartScreenController {

    Stage stage;
    Parent scene;
    String unique[];

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

        // Validate Input
        MainScreenController.checkInt(partIDField);
        MainScreenController.checkInt(partInvField);
        MainScreenController.checkDbl(partPriceField);
        MainScreenController.checkInt(partMinField);
        MainScreenController.checkInt(partMaxField);

        try {
            int id = Integer.parseInt(partIDField.getText());
            String name = partNameField.getText();
            double price = Double.parseDouble(partPriceField.getText());
            int stock = Integer.parseInt(partInvField.getText());
            int min = Integer.parseInt(partMinField.getText());
            int max = Integer.parseInt(partMaxField.getText());

            if (partInHouseRadio.isSelected()) {
                int machineID = Integer.parseInt(partUniqueField.getText());
                try {
                    updatePart(id, new InHouse(id, name, price, stock, min, max, machineID)) ;
                }
                catch (Exception e) {
                    // Give a pop up warning that Part ID does not exist, and ask to create new part instead or cancel
                    // Also warn that new part ID will be auto-incremented and not necessarily the input number
                    System.out.println("Part does not exist in Inventory, new part will be created using the next auto-generated ID");
                    id = Inventory.incPartID();
                    Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineID));
                }
            } else {
                String companyName = partUniqueField.getText();
                try {
                    updatePart(id, new Outsourced(id, name, price, stock, min, max, companyName)) ;
                }
                catch (Exception e) {
                    // Give a pop up warning that Part ID does not exist, and ask to create new part instead or cancel
                    // Also warn that new part ID will be auto-incremented and not necessarily the input number
                    System.out.println("Part does not exist in Inventory, new part will be created using the next auto-generated ID");
                    id = Inventory.incPartID();
                    Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
                }
            }
            MainScreenController.checkStock(partInvField, min, max);
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View_Controller/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input Type");
            alert.setContentText("Please make sure you are entering the correct input type");
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
