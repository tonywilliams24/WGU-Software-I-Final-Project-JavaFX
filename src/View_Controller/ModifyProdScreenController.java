// PLEASE SEE MODIFY PART SCREEN CONTROLLER FOR MORE COMPLETE IMPLEMENTATION

package View_Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ModifyProdScreenController {

    Stage stage;
    Parent scene;

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
    private Button prodSearchButton;

    @FXML
    private TextField prodSearchField;

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
    void prodAddHandler(MouseEvent event) {

    }

    @FXML
    void prodCancelHandler(MouseEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View_Controller/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void prodSaveHandler(MouseEvent event) {

    }

    @FXML
    void prodSearchHandler(MouseEvent event) {

    }

}
