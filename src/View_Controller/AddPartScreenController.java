package View_Controller;

import Model.InHouse;
import Model.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

import static Model.Inventory.*;
import static View_Controller.Utility.*;

public class AddPartScreenController {

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

    // Cancel Button Handler
    @FXML
    void partCancelHandler(MouseEvent event) throws IOException {
        cancelButton(event);
    }

    // Save Button Handler
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
        checkIntEmpty(partFields[1], inventoryLevel.stock);
        checkDbl(partFields[2]);
        checkIntEmpty(partFields[3], inventoryLevel.max);
        checkIntEmpty(partFields[4], inventoryLevel.min);
        if (partInHouseRadio.isSelected()) checkInt(partFields[5]);
        else checkString(partFields[5]);

        // Input validation that will throw error if unsuccessful
        // If no errors, will create new Part based on the Part type selected
        // Will send back to main screen after part is created
        try {
            int id = incPartID();
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
                addPart(new InHouse(id, name, price, stock, min, max, machineID));
            } else {
                String companyName = partFields[5].getText().trim();
                checkEmpty(companyName);
                addPart(new Outsourced(id, name, price, stock, min, max, companyName));
            }
            viewScreen(event, mainScreenFxmlUrl);
        }

        // Catches first error encountered and displays alert box with all items from queue
        // Builds the appropriate error map based on the type of part
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

    // Radio Button Handlers
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
