package View_Controller;

import Model.Part;
import Model.Product;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Model.Inventory.*;
import static View_Controller.Utility.*;

public class MainScreenController implements Initializable {

    public Stage stage;
    public Parent scene;


    @FXML
    private AnchorPane pane;

    @FXML
    private Button partAddButton;

    @FXML
    private Button partModifyButton;

    @FXML
    private Button partDeleteButton;

    @FXML
    private Button partSearchButton;

    @FXML
    private TextField partSearchField;

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
    private Button prodAddButton;

    @FXML
    private Button prodModifyButton;

    @FXML
    private Button prodDeleteButton;

    @FXML
    private Button prodSearchButton;

    @FXML
    private Button prodExitButton;

    @FXML
    private TextField prodSearchField;

    @FXML
    private TableView<Product> prodTable;

    @FXML
    private TableColumn<Product, Integer> prodIdCol;

    @FXML
    private TableColumn<Product, String> prodNameCol;

    @FXML
    private TableColumn<Product, Integer> prodInvCol;

    @FXML
    private TableColumn<Product, Integer> prodPriceCol;

    // Sends user to Add Part Screen when clicking on the Add button in the Part section
    @FXML
    void partAddHandler(MouseEvent event) throws IOException {
        viewScreen(event, addPartScreenFxmlUrl);
    }

    // Deletes the selected part if it is not the only associated part for a specific product when
    // clicking the Delete Button in the Part section
    @FXML
    void partDeleteHandler(MouseEvent event) {
        deleteSelectedPart(partTable);
    }


    @FXML
    void prodExitHandler(MouseEvent event) {
        exitProgram(event);
    }

    // Sends user to the Modify Part screen with the information for the selected part pre-populated when
    // clicking the Modify Button in the Part section
    @FXML
    void partModifyHandler(MouseEvent event) throws IOException {
        modifySelectedPart(this, partTable, event);
    }

    // Searches for a part by name or ID when clicking on the Search Button in the Part section
    @FXML
    void partSearchHandler(MouseEvent event) {
        searchPart(partSearchField, partTable);
    }

    // Sends user to the add product screen when clicking on the Add button in the Product section
    @FXML
    void prodAddHandler(MouseEvent event) throws IOException {
        viewScreen(event, Utility.addProductScreenFxmlUrl);
    }

    // Deletes the selected product when clicking on the delete button in the Products section
    @FXML
    void prodDeleteHandler(MouseEvent event) {
        deleteSelectedProduct(prodTable);
    }

    // Sends user to the Modify Product screen with the information for the selected part pre-populated when
    // clicking the Modify Button in the Product section
    @FXML
    void prodModifyHandler(MouseEvent event) throws IOException {
        modifySelectedProduct(this, prodTable, event);
    }

    // Searches for a product by name or ID when clicking on the Search Button in the Product section
    @FXML
    void prodSearchHandler(MouseEvent event) {
        searchProduct(prodSearchField, prodTable);
    }

    // Initializes the Main Screen and sets the Parts and Products tables
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setAllPartsTable(partTable, partIdCol, partNameCol, partInvCol, partPriceCol);
        setAllProductsTable(prodTable, prodIdCol, prodNameCol, prodInvCol, prodPriceCol);
    }

    // Function that deletes a selected part from the Main Screen (All Parts Table)
    // Function will not complete and will alert user if the Part is the only associated part to a product
    private static void deleteSelectedPart(TableView<Part> partTable) {
        if (partTable.getSelectionModel().getSelectedItem() == null) {
            alertBox(alertType.error, selectPartDelete, invalidSelection);
        } else {
            if (searchAssociatedParts(partTable.getSelectionModel().getSelectedItem())) {
                if (alertBox(alertType.confirmation, deletePartMain.concat(getSearchPartBuilder().toString()), confirmation)) {
                    deletePart(partTable.getSelectionModel().getSelectedItem(), getSearchProductQueue());
                    partTable.setItems(partTable.getItems());
                }
            } else
                alertBox(alertType.error, onlyAssociatedPartError + getOnlyAssociatedPartBuilder(), invalidSelection);
        }
    }

}