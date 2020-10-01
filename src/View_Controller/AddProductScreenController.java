// PLEASE SEE ADD PART SCREEN CONTROLLER FOR MORE COMPLETE IMPLEMENTATION

package View_Controller;

import Model.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Model.Inventory.*;
import static View_Controller.Utility.*;

public class AddProductScreenController implements Initializable {

    @FXML
    private AnchorPane pane;

    @FXML
    private TextField prodMaxField;

    @FXML
    private Label prodMinLabel;

    @FXML
    private TextField prodMinField;

    @FXML
    private Label prodIDLabel;

    @FXML
    private Label prodNameLabel;

    @FXML
    private Label prodInvLabel;

    @FXML
    private Label prodPriceLabel;

    @FXML
    private Label prodMaxLabel;

    @FXML
    private TextField prodIDField;

    @FXML
    private TextField prodNameField;

    @FXML
    private TextField prodInvField;

    @FXML
    private TextField prodPriceField;

    @FXML
    private Button partSearchButton;

    @FXML
    private TextField partSearchField;

    @FXML
    private TableView<?> prodTable1;

    @FXML
    private Button prodAddButton;

    @FXML
    private TableView<?> prodTable;

    @FXML
    private Button prodDeleteButton;

    @FXML
    private Button prodSaveButton;

    @FXML
    private Button prodCancelButton;

    @FXML
    private TableView<Part> partTable;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> partInvCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TableView<Part> associatedPartTable;

    @FXML
    private TableColumn<Part, Integer> associatedPartIdCol;

    @FXML
    private TableColumn<Part, String> associatedPartNameCol;

    @FXML
    private TableColumn<Part, Integer> associatedPartInvCol;

    @FXML
    private TableColumn<Part, Double> associatedPartPriceCol;

    @FXML
    void partAddHandler(MouseEvent event) {
        addAssociatedPartButton(partTable, associatedPartTable, partIdCol);
    }

    @FXML
    void prodCancelHandler(MouseEvent event) throws IOException {
        cancelButton(event);
    }

    @FXML
    void prodSaveHandler(MouseEvent event) {

        // Adds all text fields to an array
        TextField[] prodFields = new TextField[] {
                prodNameField,    // 0
                prodInvField,     // 1
                prodPriceField,   // 2
                prodMaxField,     // 3
                prodMinField,     // 4
        };

        // Clear previous validation errors
        clearValidationErrors(prodFields);

        // Validation that styles text fields but does not throw error
        // Returns text field to queue for future reference
        checkString(prodFields[0]);
        checkIntEmpty(prodFields[1], inventoryLevel.stock);
        checkDbl(prodFields[2]);
        checkIntEmpty(prodFields[3], inventoryLevel.max);
        checkIntEmpty(prodFields[4], inventoryLevel.min);

        // Input validation that will throw error if unsuccessful
        // If no errors, will create new prod based on the prod type selected
        // Will send back to main screen after prod is created
        try {
            int id = incProductID();
            String name = prodFields[0].getText().trim();
            checkEmpty(name);
            int stock = Integer.parseInt(prodFields[1].getText().trim());
            checkPos(stock);
            double price = Double.parseDouble(prodFields[2].getText().trim());
            checkPos(price);
            int min = Integer.parseInt(prodFields[4].getText().trim());
            checkPos(min);
            int max = Integer.parseInt(prodFields[3].getText().trim());
            checkPos(max);
            checkProductPrice(price, associatedPartTable, prodPriceField);
            checkMaxMin(prodFields[3], prodFields[4], Integer.parseInt(prodFields[1].getText().trim()));
            checkStock(prodFields[1], min, max);
            checkAssociatedParts(associatedPartTable);
            addProduct(new Product(id, name, price, stock, min, max));
            for(Part associatedPart : associatedPartTable.getItems()) lookupProduct(id).addAssociatedPart(associatedPart);
            viewScreen(event, mainScreenFxmlUrl);
        }
        catch (NumberFormatException e) {
            buildErrorMap("Product");
            while(!errorQ.isEmpty()) {

                errorBuilder.append(errorMap.get(errorQ.poll().getId()) + "\n");
            }
            alertBox(errorFields, errorBuilder, fieldInput);
        }

        // Catches errors that have already generated an alert box
        catch (Exception e) {
        }
    }

    @FXML
    void partDeleteHandler(MouseEvent event) {
        deleteSelectedPart(associatedPartTable);
    }

    @FXML
    void partSearchHandler(MouseEvent event) {
        searchPart(partSearchField, partTable);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        partTable.setItems(getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        associatedPartTable.getSortOrder().add(associatedPartIdCol);
    }
}
