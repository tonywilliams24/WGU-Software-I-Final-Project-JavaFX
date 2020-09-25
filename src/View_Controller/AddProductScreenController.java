// PLEASE SEE ADD PART SCREEN CONTROLLER FOR MORE COMPLETE IMPLEMENTATION

package View_Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class AddProductScreenController {

    Stage stage;
    Parent scene;

    @FXML
    private Label prodIDLabel;

    @FXML
    private TextField prodIDField;

    @FXML
    private Label prodNameLabel;

    @FXML
    private TextField prodNameField;

    @FXML
    private Label ProdInvLabel;

    @FXML
    private TextField prodInvField;

    @FXML
    private Label prodPriceLabel;

    @FXML
    private TextField prodPriceField;

    @FXML
    private Label prodMaxLabel;

    @FXML
    private TextField prodMaxField;

    @FXML
    private Label prodMinLabel;

    @FXML
    private TextField prodMinField;

    @FXML
    private AnchorPane pane;

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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Cancel and return to the Main Screen without making changes?",ButtonType.CANCEL,ButtonType.YES);
        Optional<ButtonType> confirm = alert.showAndWait();
        if(confirm.isPresent() && confirm.get() == ButtonType.YES) {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View_Controller/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    @FXML
    void prodDeleteHandler(MouseEvent event) {

    }

    @FXML
    void prodSaveHandler(MouseEvent event) {

    }

    @FXML
    void prodSearchHandler(MouseEvent event) {

    }

}
