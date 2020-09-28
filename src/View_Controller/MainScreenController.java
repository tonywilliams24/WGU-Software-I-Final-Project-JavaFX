package View_Controller;

import Model.Part;
import Model.Product;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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

    @FXML
    void partAddHandler(MouseEvent event) throws IOException {
        viewScreen(event, addPartScreenFxmlUrl);
    }

    @FXML
    void partDeleteHandler(MouseEvent event) {
        deleteSelectedPart(partTable);
    }

    @FXML
    void partModifyHandler(MouseEvent event) throws IOException {
        modifySelectedPart(this, partTable, event);
    }

    @FXML
    void partSearchHandler(MouseEvent event) {
        searchPart(partSearchField, partTable);
    }

    @FXML
    void prodAddHandler(MouseEvent event) throws IOException {
        viewScreen(event, Utility.addProductScreenFxmlUrl);
    }

    @FXML
    void prodDeleteHandler(MouseEvent event) {
        deleteSelectedProduct(prodTable);
    }

    @FXML
    void prodModifyHandler(MouseEvent event) throws IOException {
        modifySelectedProduct(this, prodTable, event);
    }

    @FXML
    void prodSearchHandler(MouseEvent event) {
        searchProduct(prodSearchField, prodTable);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        partTable.setItems(getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        prodTable.setItems(getAllProducts());
        prodIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}
