package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

import static Model.Inventory.*;

public class Utility {

    public static void addAssociatedPartButton(TableView<Part> partTable, TableView<Part> associatedPartTable, TableColumn<Part, Integer> partIdCol) {
        ObservableList<Part> associatedParts = FXCollections.observableArrayList();
        associatedParts = associatedPartTable.getItems();
        associatedParts.add(partTable.getSelectionModel().getSelectedItem());
        associatedPartTable.setItems(associatedParts);
        associatedPartTable.getSortOrder().add(partIdCol);
    }

    public enum alertType {confirmation, error, warning};

    // Strings for the error messages
    public static final String cancel = "Cancel and return to the Main Screen without making changes?";
    public static final String confirmation = "Confirmation Needed";
    public static final String deletePartMain = "Permanently delete the selected part? This will also remove it as an associated part from the following products: \n" ;
    public static final String deletePartCancel = "Permanently delete the selected part? The cancel button will not undo this action.";
    public static final String deleteProduct = "Permanently delete the product?";
    public static final String empty = "Empty Search";
    public static final String errorFields = "Error(s) found in the following field(s): \n";
    public static final String fieldInput = "Text Field Input Error";
    public static final String invalidMaxMin = "Maximum / Minimum amounts are invalid.";
    public static final String invalidSelection = "Selection Error";
    public static final String maxGreaterMin = "Maximum amount is less than minimum amount";
    public static final String minGreaterMax = "Minimum amount is greater than maximum amount";
    public static final String notFound = "Not Found";
    public static final String partNotFound = "Part was not found";
    public static final String productNotFound = "Product was not found";
    public static final String searchFieldEmpty = "Please enter a name or an ID into the search field";
    public static final String selectPartDelete = "Must select a part to delete";
    public static final String selectPartModify = "Must select a part to modify";
    public static final String selectProductDelete = "Must select a product to delete";
    public static final String selectProductModify = "Must select a product to modify";
    public static final String stockMax = "Input amount is greater than maximum stock amount";
    public static final String stockMin = "Input amount is less than minimum stock amount";
    public static final String switchMaxMin = "Maximum / Minimum amounts are invalid. The values may need to be switched.";

    // Strings for the screen Urls
    public static final String mainScreenFxmlUrl = "/View_Controller/MainScreen.fxml";
    public static final String addPartScreenFxmlUrl = "/View_Controller/AddPartScreen.fxml";
    public static final String addProductScreenFxmlUrl = "/View_Controller/AddProductScreen.fxml";
    public static final String ModifyPartScreenFxmlUrl = "/View_Controller/ModifyPartScreen.fxml";
    public static final String ModifyProductScreenFxmlUrl = "/View_Controller/ModifyProductScreen.fxml";

    static StringBuilder errorBuilder = new StringBuilder();
    static HashMap<String, String> errorMap = new HashMap<>();
    static Queue<TextField> errorQ = new LinkedList<>();

    public static void buildErrorMap(String s) {
        if(s.equals("InHouse")) {
            errorMap.put("partNameField", "Name");
            errorMap.put("partInvField", "Inventory");
            errorMap.put("partPriceField", "Price");
            errorMap.put("partMinField", "Min");
            errorMap.put("partMaxField", "Max");
            errorMap.put("partUniqueField", "Machine ID");
            errorMap.put("partSearchField", "Part Search");
        }
        else if(s.equals("Outsourced")) {
            errorMap.put("partNameField", "Name");
            errorMap.put("partInvField", "Inventory");
            errorMap.put("partPriceField", "Price");
            errorMap.put("partMinField", "Min");
            errorMap.put("partMaxField", "Max");
            errorMap.put("partUniqueField", "Company Name");
            errorMap.put("partSearchField", "Part Search");
        }
        else {
                errorMap.put("prodNameField", "Name");
                errorMap.put("prodInvField", "Inventory");
                errorMap.put("prodPriceField", "Price");
                errorMap.put("prodMaxField", "Max");
                errorMap.put("prodMinField", "Min");
                errorMap.put("prodSearchField", "Product Search");
        }
    }
    public static void clearValidationErrors(TextField[] fields) {
        for(TextField f : fields) {
            f.setStyle("-fx-border-color: #999999");
        }
        errorBuilder.setLength(0);
        errorMap.clear();
        errorQ.clear();
    }

    public static void checkEmpty(String s) {
        if(s.isEmpty()) throw new NumberFormatException("Cannot be empty");
    }

    public static void checkPos(double d) {
        if(d<0) throw new NumberFormatException("Number must be positive");
    }

    public static void checkPos(int i) {
        if(i<0) throw new NumberFormatException("Number must be positive");
    }

    public static void checkString(TextField f) {
        try {
            checkEmpty(f.getText().trim());
        }
        catch (Exception e) {
            f.setStyle("-fx-border-color: red");
            errorQ.add(f);
            System.out.println(f.getId());
        }
    }

    public static void checkInt(TextField f) {
        int i;
        try {
            i = Integer.parseInt(f.getText().trim());
            checkPos(i);
        }
        catch (Exception e) {
            f.setStyle("-fx-border-color: red");
            errorQ.add(f);
        }
    }

    public static void checkDbl(TextField f) {
        double d;
        try {
            d = Double.parseDouble(f.getText().trim());
            checkPos(d);
        }
        catch (Exception e) {
            f.setStyle("-fx-border-color: red");
            errorQ.add(f);
        }
    }

    public static void checkMaxMin(TextField maxF, TextField minF, int stockInt) {
        if (Integer.parseInt(maxF.getText().trim()) < Integer.parseInt(minF.getText().trim()) && Integer.parseInt(maxF.getText().trim()) < stockInt && stockInt < Integer.parseInt(minF.getText().trim())) {
            minF.setStyle("-fx-border-color: red");
            maxF.setStyle("-fx-border-color: red");
            errorQ.add(minF);
            errorQ.add(maxF);
            alertBox(alertType.error, switchMaxMin, fieldInput);
            throw new IllegalArgumentException();
        }

        else if (Integer.parseInt(maxF.getText().trim()) < Integer.parseInt(minF.getText().trim()) && Integer.parseInt(maxF.getText().trim()) < stockInt) {
            maxF.setStyle("-fx-border-color: red");
            errorQ.add(maxF);
            alertBox(alertType.error, maxGreaterMin, fieldInput);
            throw new IllegalArgumentException();
        }
        else if (Integer.parseInt(minF.getText().trim()) > Integer.parseInt(maxF.getText().trim()) && Integer.parseInt(minF.getText().trim()) > stockInt) {
            minF.setStyle("-fx-border-color: red");
            errorQ.add(minF);
            alertBox(alertType.error, minGreaterMax, fieldInput);
            throw new IllegalArgumentException();
        }
        else if (Integer.parseInt(minF.getText().trim()) > Integer.parseInt(maxF.getText().trim())) {
            minF.setStyle("-fx-border-color: red");
            maxF.setStyle("-fx-border-color: red");
            errorQ.add(minF);
            errorQ.add(maxF);
            alertBox(alertType.error, invalidMaxMin, fieldInput);
            throw new IllegalArgumentException();
        }
    }

    public static void checkStock(TextField stock, int min, int max) {
        if(Integer.parseInt(stock.getText().trim()) < min) {
            stock.setStyle("-fx-border-color: red");
            errorQ.add(stock);
            alertBox(alertType.error, stockMin, fieldInput);
            throw new IllegalArgumentException();
        }
        if(Integer.parseInt(stock.getText().trim()) > max) {
            stock.setStyle("-fx-border-color: red");
            errorQ.add(stock);
            alertBox(alertType.error, stockMax, fieldInput);
            throw new IllegalArgumentException();
        }
    }

    public static void viewScreen(MouseEvent event, String url) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Utility.class.getResource(url));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public static void cancelButton(MouseEvent event) throws IOException {
        if(alertBox(alertType.confirmation, cancel, confirmation)) {
            viewScreen(event, mainScreenFxmlUrl);
        }
    }

    public static boolean alertBox(alertType type, String contentText, String title) {
        if(type == alertType.confirmation) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, contentText, ButtonType.CANCEL, ButtonType.YES);
            alert.setTitle(title);
            alert.setHeaderText(title);
            Optional<ButtonType> confirm = alert.showAndWait();
            return (confirm.isPresent() && confirm.get() == ButtonType.YES);
        }
        else if(type == alertType.error) {
            Alert alert = new Alert(Alert.AlertType.ERROR, contentText);
            alert.setTitle(title);
            alert.setHeaderText(title);
            alert.showAndWait();
            return false;
        }
        else if(type == alertType.warning) {
            Alert alert = new Alert(Alert.AlertType.WARNING, contentText);
            alert.setTitle(title);
            alert.setHeaderText(title);
            alert.showAndWait();
            return false;
        }
        else return false;
    }
    public static void alertBox(String contentText, StringBuilder errorFields, String title) {
        Alert alert = new Alert(Alert.AlertType.ERROR, contentText + errorFields.toString());
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.showAndWait();
    }
    public static void searchPart(TextField partSearchField, TableView<Part> partTable) {
        try {
            checkEmpty(partSearchField.getText().trim());
            partTable.setItems(searchPartResult(partSearchField.getText()));
        }
        catch(Exception e) {
            if(partTable.getItems().equals(getAllParts())) alertBox(alertType.error, searchFieldEmpty, empty);
            else partTable.setItems(getAllParts());
        }
    }

    public static void searchProduct(TextField prodSearchField, TableView<Product> prodTable) {
        try {
            checkEmpty(prodSearchField.getText().trim());
            prodTable.setItems(searchProductResult(prodSearchField.getText()));
        }

        catch (Exception e) {
            if(prodTable.getItems().equals((getAllProducts()))) alertBox(alertType.error, searchFieldEmpty, empty);
            else prodTable.setItems(getAllProducts());
        }
    }

    public static void deleteSelectedPart(TableView<Part> partTable) {
        if (partTable.getSelectionModel().getSelectedItem() == null) {
            alertBox(alertType.error, selectPartDelete, invalidSelection);
        }
        else if (alertBox(alertType.confirmation, deletePartCancel, confirmation)) {
            deletePart(partTable.getSelectionModel().getSelectedItem(), partTable.getItems());
            partTable.setItems(partTable.getItems());
        }
    }

    public static void deleteSelectedProduct(TableView<Product> prodTable) {
        if (prodTable.getSelectionModel().getSelectedItem() == null) {
            alertBox(alertType.error, selectProductDelete, invalidSelection);
        }
        else {
            if (alertBox(alertType.confirmation, deleteProduct, confirmation)) {
                deleteProduct(prodTable.getSelectionModel().getSelectedItem());
                prodTable.setItems(getAllProducts());
            }
        }
    }

    public static void modifySelectedPart(MainScreenController mainScreenController, TableView<Part> partTable, MouseEvent event) throws IOException {
        if (partTable.getSelectionModel().getSelectedItem() == null) {
            alertBox(alertType.error, selectPartModify, invalidSelection);
        }
        else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(mainScreenController.getClass().getResource(ModifyPartScreenFxmlUrl));
            loader.load();
            ModifyPartScreenController MPSController = loader.getController();
            MPSController.sendPart(partTable.getSelectionModel().getSelectedItem());
            mainScreenController.stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            mainScreenController.stage.setScene(new Scene(scene));
            mainScreenController.stage.show();
        }
    }

    public static void modifySelectedProduct(MainScreenController mainScreenController, TableView<Product> prodTable, MouseEvent event) throws IOException {
        if (prodTable.getSelectionModel().getSelectedItem() == null) {
            alertBox(alertType.error, selectProductModify, invalidSelection);
        }
        else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(mainScreenController.getClass().getResource(ModifyProductScreenFxmlUrl));
            loader.load();
            ModifyProductScreenController MPSController = loader.getController();
            MPSController.sendProduct(prodTable.getSelectionModel().getSelectedItem());
            mainScreenController.stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            mainScreenController.stage.setScene(new Scene(scene));
            mainScreenController.stage.show();
        }
    }

}
