package View_Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

import static View_Controller.Utility.*;

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
        cancelButton(event);
    }

    @FXML
    void partSaveHandler(MouseEvent event) throws IOException {

        // Adds all text fields to an array
        TextField[] partFields = new TextField[] {
                partNameField,    // 0
                partInvField,     // 1
                partPriceField,   // 2
                partMaxField,     // 3
                partMinField,     // 4
                partUniqueField   // 5
        };

        // Clear previous validation errors
        clearValidationErrors(partFields);

        // Validation that styles text fields but does not throw error
        // Returns text field to queue for future reference
        checkString(partFields[0]);
        checkInt(partFields[1]);
        checkDbl(partFields[2]);
        checkInt(partFields[3]);
        checkInt(partFields[4]);
        if (partInHouseRadio.isSelected()) checkInt(partFields[5]);
        else checkString(partFields[5]);

        // Input validation that will throw error if unsuccessful
        // If no errors, will create new Part based on the Part type selected
        // Will send back to main screen after part is created
        int id = Integer.parseInt(partIDField.getText());
        try {
            String name = partNameField.getText().trim();
            checkEmpty(name);
            int stock = Integer.parseInt(partFields[1].getText().trim());
            checkPos(stock);
            double price = Double.parseDouble(partFields[2].getText().trim());
            checkPos(price);
            int min = Integer.parseInt(partFields[4].getText().trim());
            checkPos(min);
            int max = Integer.parseInt(partFields[3].getText().trim());
            checkPos(max);
            checkMaxMin(partFields[3], partFields[4], Integer.parseInt(partFields[1].getText().trim()));
            checkStock(partFields[1], min, max);

            if (partInHouseRadio.isSelected()) {
                int machineID = Integer.parseInt(partFields[5].getText().trim());
                checkPos(machineID);
                updatePart(id, new InHouse(id, name, price, stock, min, max, machineID));
            } else {
                String companyName = partFields[5].getText().trim();
                checkEmpty(companyName);
                updatePart(id, new Outsourced(id, name, price, stock, min, max, companyName));
            }
            viewScreen(event, mainScreenFxmlUrl);
        }

        // Catches first error encountered and displays alert box with all items from error queue
        catch (NumberFormatException e) {
            if (partInHouseRadio.isSelected()) buildErrorMap("InHouse");
            else buildErrorMap("Outsourced");
            while(!errorQ.isEmpty()) {

                errorBuilder.append(errorMap.get(errorQ.poll().getId()) + "\n");
            }
            alertBox(errorFields, errorBuilder, fieldInput);
        }

        // Catches errors that have already generated an alert box
        catch (IllegalArgumentException e) {
        }
    }

    // Function to bring Part information from Main screen to Modify Part Screen
    public void sendPart(Part part) {

        // Sets text fields based on part selected from prior screen
        partIDField.setText(String.valueOf(part.getId()));
        partNameField.setText((part.getName()));
        partInvField.setText(String.valueOf(part.getStock()));
        partPriceField.setText(String.valueOf(part.getPrice()));
        partMaxField.setText((String.valueOf(part.getMax())));
        partMinField.setText((String.valueOf(part.getMin())));

        // Checks to see if the part is InHouse or not, and sets the Label and Field accordingly
        // Also creates a variable to remember the field in case it needs to be recalled

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

    // Radio Button Handlers - Changes text in Unique Label and Field based on the selection
    @FXML
    void partInHouseRadioHandler(ActionEvent event) {
        partUniqueLabel.setText("Machine ID");
        partUniqueField.clear();
        if(unique[0].equals("InHouse")) partUniqueField.setText(unique[1]);
        else partUniqueField.setPromptText("Machine ID");
    }

    @FXML
    void partOutsourcedRadioHandler(ActionEvent event) {
        partUniqueLabel.setText("Company Name");
        partUniqueField.clear();
        if(unique[0].equals("Outsourced")) partUniqueField.setText(unique[1]);
        else partUniqueField.setPromptText("Company Name");
    }

    // Function to update Part in inventory based on user input
    public void updatePart(int partId, Part part){
        int index = -1;
        for (Part p : Inventory.getAllParts()) {
            index++;
            if(p.getId() == partId) {
                Inventory.getAllParts().set(index, part);
                return;
            }
        }
    }
}
