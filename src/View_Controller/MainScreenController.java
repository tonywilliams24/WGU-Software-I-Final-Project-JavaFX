package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

    Stage stage;
    Parent scene;

    ObservableList<Part> searchPartResult(String search) {
        if(!(Inventory.getAllSearchedParts().isEmpty())) {
            Inventory.getAllSearchedParts().clear();
        }
        for(Part part : Inventory.getAllParts()) {
            if(Integer.toString(part.getId()).contains(search) || part.getName().contains(search)) {
                Inventory.getAllSearchedParts().add(part);
            }
        }
        if(Inventory.getAllSearchedParts().isEmpty()){
            return Inventory.getAllParts();
        }
        else {
            return Inventory.getAllSearchedParts();
        }
    }

    ObservableList<Product> searchProductResult(String search) {
        if(!(Inventory.getAllSearchedProducts().isEmpty())) {
            Inventory.getAllSearchedProducts().clear();
        }
        for(Product product : Inventory.getAllProducts()) {
            if(Integer.toString(product.getId()).contains(search) || product.getName().contains(search)) {
                Inventory.getAllSearchedProducts().add(product);
            }
        }
        if(Inventory.getAllSearchedProducts().isEmpty()){
            return Inventory.getAllProducts();
        }
        else {
            return Inventory.getAllSearchedProducts();
        }
    }

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
    private TableColumn<Part, Integer> partIDcol;

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
    private TableColumn<Product, Integer> prodIDcol;

    @FXML
    private TableColumn<Product, String> prodNameCol;

    @FXML
    private TableColumn<Product, Integer> prodInvCol;

    @FXML
    private TableColumn<Product, Integer> prodPriceCol;

    @FXML
    void partAddHandler(MouseEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/AddPartScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void partDeleteHandler(MouseEvent event) {

    }

    @FXML
    void partModifyHandler(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View_Controller/ModifyPartScreen.fxml"));
        loader.load();

        ModifyPartScreenController MPSController = loader.getController();
        MPSController.sendPart(partTable.getSelectionModel().getSelectedItem());


        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void partSearchHandler(MouseEvent event) {
        partTable.setItems(searchPartResult(partSearchField.getText()));

    }

    @FXML
    void prodAddHandler(MouseEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/AddProductScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void prodDeleteHandler(MouseEvent event) {

    }

    @FXML
    void prodModifyHandler(MouseEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/ModifyProductScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void prodSearchHandler(MouseEvent event) {
        prodTable.setItems(searchProductResult(prodSearchField.getText()));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        partTable.setItems(Inventory.getAllParts());
        partIDcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        prodTable.setItems(Inventory.getAllProducts());
        prodIDcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}
