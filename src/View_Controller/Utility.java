// Utility class to store methods, variables, etc that can be used by more than one controller (Use may be theoretical based on potential future expansion of project)

package View_Controller;

import Model.Part;
import Model.Product;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import static Model.Inventory.*;

public class Utility {

    // Strings for the error messages
    public static final String cancel = "Cancel and return to the Main Screen without making changes?";
    public static final String confirmation = "Confirmation Needed";
    public static final String deletePartMain = "Permanently delete the selected part? This will also remove it as an associated part from the following products: \n";
    public static final String deletePartCancel = "Permanently delete the selected part? The cancel button will not undo this action.";
    public static final String deleteProduct = "Permanently delete the product?";
    public static final String empty = "Empty Search";
    public static final String exit = "Are you sure you want to exit the program?";
    public static final String errorFields = "Error(s) found in the following field(s): \n";
    public static final String fieldInput = "Text Field Input Error";
    public static final String invalidMaxMin = "Maximum / Minimum amounts are invalid.";
    public static final String invalidSelection = "Selection Error";
    public static final String maxEmpty = "Maximum Inventory Level Empty";
    public static final String maxLessMin = "Maximum amount is less than minimum amount";
    public static final String maxSetHundred = "Maximum Inventory level will be set to 100,000. Do you accept this change?";
    public static final String minEmpty = "Minimum Inventory Level Empty";
    public static final String minGreaterMax = "Minimum amount is greater than maximum amount";
    public static final String minSetZero = "Minimum Inventory level will be set to 0. Do you accept this change?";
    public static final String notFound = "Not Found";
    public static final String noAssociatedParts = "No Associated Parts Found";
    public static final String onlyAssociatedPartError = "Cannot delete part as it is the only associated part for the following products:\n";
    public static final String partsGreaterProduct = "Cost of the product is less than the cost of the parts";
    public static final String partNotFound = "Part was not found";
    public static final String productPrice = "Product Price Error";
    public static final String productMustHavePart = "Product must have at least one associated part";
    public static final String productNotFound = "Product was not found";
    public static final String searchFieldEmpty = "Please enter a name or an ID into the search field";
    public static final String selectPartDelete = "Must select a part to delete";
    public static final String selectPartModify = "Must select a part to modify";
    public static final String selectProductDelete = "Must select a product to delete";
    public static final String selectProductModify = "Must select a product to modify";
    public static final String stockEmpty = "Inventory Level Empty";
    public static final String stockSetZero = "Inventory level will be set to 0. Do you accept this change?";
    public static final String stockMax = "Inventory level is greater than maximum";
    public static final String stockMin = "Inventory level is less than minimum";
    public static final String switchMaxMin = "Maximum / Minimum amounts are invalid. The values may need to be switched.";

    // Strings for the screen Urls
    public static final String mainScreenFxmlUrl = "/View_Controller/MainScreen.fxml";
    public static final String addPartScreenFxmlUrl = "/View_Controller/AddPartScreen.fxml";
    public static final String addProductScreenFxmlUrl = "/View_Controller/AddProductScreen.fxml";
    public static final String ModifyPartScreenFxmlUrl = "/View_Controller/ModifyPartScreen.fxml";
    public static final String ModifyProductScreenFxmlUrl = "/View_Controller/ModifyProductScreen.fxml";

    public static void validateInput(TextField[] partFields) {
        checkString(partFields[0]);
        checkIntEmpty(partFields[1], inventoryLevel.stock);
        checkDbl(partFields[2]);
        checkIntEmpty(partFields[3], inventoryLevel.max);
        checkIntEmpty(partFields[4], inventoryLevel.min);
    }

    public static void validateInput(TextField[] partFields, RadioButton partInHouseRadio) {
        checkString(partFields[0]);
        checkIntEmpty(partFields[1], inventoryLevel.stock);
        checkDbl(partFields[2]);
        checkIntEmpty(partFields[3], inventoryLevel.max);
        checkIntEmpty(partFields[4], inventoryLevel.min);
        if (partInHouseRadio.isSelected()) checkInt(partFields[5]);
        else checkString(partFields[5]);
    }

    // enums to be used for switch cases, used to differentiate error messages
    public enum alertType {confirmation, error, warning}
    public enum inventoryLevel {stock, min, max}

    // Objects to hold various error information
    static StringBuilder errorBuilder = new StringBuilder();
    static HashMap<String, String> errorMap = new HashMap<>();
    static Queue<TextField> errorQ = new LinkedList<>();

    // Hashmap that maps the internal names of input fields to external names that will be displayed to the
    // user in the event of an error with one of the fields
    public static void buildErrorMap(String s) {
        if (s.equals("InHouse")) {
            errorMap.put("partNameField", "Name");
            errorMap.put("partInvField", "Inventory");
            errorMap.put("partPriceField", "Price");
            errorMap.put("partMinField", "Min");
            errorMap.put("partMaxField", "Max");
            errorMap.put("partUniqueField", "Machine ID");
            errorMap.put("partSearchField", "Part Search");
        } else if (s.equals("Outsourced")) {
            errorMap.put("partNameField", "Name");
            errorMap.put("partInvField", "Inventory");
            errorMap.put("partPriceField", "Price");
            errorMap.put("partMinField", "Min");
            errorMap.put("partMaxField", "Max");
            errorMap.put("partUniqueField", "Company Name");
            errorMap.put("partSearchField", "Part Search");
        } else {
            errorMap.put("prodNameField", "Name");
            errorMap.put("prodInvField", "Inventory");
            errorMap.put("prodPriceField", "Price");
            errorMap.put("prodMaxField", "Max");
            errorMap.put("prodMinField", "Min");
            errorMap.put("prodSearchField", "Product Search");
        }
    }

    // Sets the styles of text fields back to default, and clears the objects holding error information
    public static void clearValidationErrors(TextField[] fields) {
        for (TextField f : fields) {
            f.setStyle("-fx-border-color: #999999");
        }
        errorBuilder.setLength(0);
        errorMap.clear();
        errorQ.clear();
    }

    // Methods to validate various input types, makes sure no strings are empty and all numbers are positive
    public static void checkEmpty(String s) {
        if (s.isEmpty()) throw new NumberFormatException("Cannot be empty");
    }

    public static void checkPos(double d) {
        if (d < 0) throw new NumberFormatException("Number must be positive");
    }

    public static void checkPos(int i) {
        if (i < 0) throw new NumberFormatException("Number must be positive");
    }

    // Methods to catch validation errors, format the text fields, and add the affected fields to the "error" queue
    public static void checkString(TextField f) {
        try {
            checkEmpty(f.getText().trim());
        } catch (Exception e) {
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
        } catch (Exception e) {
            f.setStyle("-fx-border-color: red");
            errorQ.add(f);
        }
    }

    public static void checkDbl(TextField f) {
        double d;
        try {
            d = Double.parseDouble(f.getText().trim());
            checkPos(d);
        } catch (Exception e) {
            f.setStyle("-fx-border-color: red");
            errorQ.add(f);
        }
    }

    // Validates the Stock, Min, and Max fields. If empty, will prompt to set the field to the default value instead
    public static void checkIntEmpty(TextField f, inventoryLevel IL) {
        if (f.getText().trim().isEmpty()) {
            switch (IL) {
                case stock:
                    if (alertBox(alertType.confirmation, stockSetZero, stockEmpty)) f.setText("0");
                    break;
                case min:
                    if (alertBox(alertType.confirmation, minSetZero, minEmpty)) f.setText("0");
                    break;
                case max:
                    if (alertBox(alertType.confirmation, maxSetHundred, maxEmpty)) f.setText("100000");
            }
        }
        try {
            int i = Integer.parseInt(f.getText().trim());
            checkPos(i);
        } catch (Exception e) {
            f.setStyle("-fx-border-color: red");
            errorQ.add(f);
        }
    }

    // Validation to make sure the Maximum value is always greater than the Minimum value and vise versa
    // Throws error that Min is too large if Min is larger than both Max and Stock fields
    // If Min is larger than stock and Max is smaller than stock, will throw an error and ask if perhaps the values were accidentally switched
    // Otherwise, left remaining else if to catch any other instances where the Max is greater than Min or vise versa
    public static void checkMaxMin(TextField maxF, TextField minF, int stockInt) {
        if (Integer.parseInt(maxF.getText().trim()) < Integer.parseInt(minF.getText().trim()) && Integer.parseInt(maxF.getText().trim()) < stockInt && stockInt < Integer.parseInt(minF.getText().trim())) {
            minF.setStyle("-fx-border-color: red");
            maxF.setStyle("-fx-border-color: red");
            errorQ.add(minF);
            errorQ.add(maxF);
            alertBox(alertType.error, switchMaxMin, fieldInput);
            throw new IllegalArgumentException();
        } else if (Integer.parseInt(maxF.getText().trim()) < Integer.parseInt(minF.getText().trim()) && Integer.parseInt(maxF.getText().trim()) < stockInt) {
            maxF.setStyle("-fx-border-color: red");
            errorQ.add(maxF);
            alertBox(alertType.error, maxLessMin, fieldInput);
            throw new IllegalArgumentException();
        } else if (Integer.parseInt(minF.getText().trim()) > Integer.parseInt(maxF.getText().trim()) && Integer.parseInt(minF.getText().trim()) > stockInt) {
            minF.setStyle("-fx-border-color: red");
            errorQ.add(minF);
            alertBox(alertType.error, minGreaterMax, fieldInput);
            throw new IllegalArgumentException();
        } else if (Integer.parseInt(minF.getText().trim()) > Integer.parseInt(maxF.getText().trim())) { // Catch any remaining logic problems if applicable
            minF.setStyle("-fx-border-color: red");
            maxF.setStyle("-fx-border-color: red");
            errorQ.add(minF);
            errorQ.add(maxF);
            alertBox(alertType.error, invalidMaxMin, fieldInput);
            throw new IllegalArgumentException();
        }
    }

    // Validation to make sure the stock value is between the min and max, should not be run before checking the Max and Min values
    public static void checkStock(TextField stock, int min, int max) {
        if (Integer.parseInt(stock.getText().trim()) < min) {
            stock.setStyle("-fx-border-color: red");
            errorQ.add(stock);
            alertBox(alertType.error, stockMin, fieldInput);
            throw new IllegalArgumentException();
        }
        if (Integer.parseInt(stock.getText().trim()) > max) {
            stock.setStyle("-fx-border-color: red");
            errorQ.add(stock);
            alertBox(alertType.error, stockMax, fieldInput);
            throw new IllegalArgumentException();
        }
    }

    // Validation to make sure the product cost is not less than the sum cost of associated parts
    static void checkProductPrice(double price, TableView<Part> associatedPartTable, TextField prodPriceField) {
        double partSum = 0;
        for (Part associatedPart : associatedPartTable.getItems()) partSum += associatedPart.getPrice();
        if (partSum > price) {
            prodPriceField.setStyle("-fx-border-color: red");
            associatedPartTable.setStyle("-fx-border-color: red");
            alertBox(alertType.error, partsGreaterProduct, productPrice);
            throw new IllegalArgumentException();
        }
    }

    // Validation to make sure a product has an associated part. To be run when creating or modifying a product.
    static void checkAssociatedParts(TableView<Part> associatedPartTable) {
        if (associatedPartTable.getItems().isEmpty()) {
            associatedPartTable.setStyle("-fx-border-color: red");
            alertBox(alertType.error, productMustHavePart, noAssociatedParts);
            throw new IllegalArgumentException("Product has no part");
        }
    }

    // Validates the search field, runs the search function, sets the table to match the search results or returns the
    // original parts / products table if no results are found or if the search field is empty
    public static void searchPart(TextField partSearchField, TableView<Part> partTable) {
        try {
            checkEmpty(partSearchField.getText().trim());
            partTable.setItems(searchPartResult(partSearchField.getText()));
        } catch (Exception e) {
            if (partTable.getItems().equals(getAllParts())) alertBox(alertType.error, searchFieldEmpty, empty);
            else partTable.setItems(getAllParts());
        }
    }

    public static void searchProduct(TextField prodSearchField, TableView<Product> prodTable) {
        try {
            checkEmpty(prodSearchField.getText().trim());
            prodTable.setItems(searchProductResult(prodSearchField.getText()));
        } catch (Exception e) {
            if (prodTable.getItems().equals((getAllProducts()))) alertBox(alertType.error, searchFieldEmpty, empty);
            else prodTable.setItems(getAllProducts());
        }
    }

    // Deletes selected part / product from a table and gives error message if empty
    public static void deleteSelectedPart(TableView<Part> partTable) {
        if (partTable.getSelectionModel().getSelectedItem() == null) {
            alertBox(alertType.error, selectPartDelete, invalidSelection);
        } else if (alertBox(alertType.confirmation, deletePartCancel, confirmation)) {
            deletePart(partTable.getSelectionModel().getSelectedItem(), partTable.getItems());
            partTable.setItems(partTable.getItems());
        }
    }

    public static void deleteSelectedProduct(TableView<Product> prodTable) {
        if (prodTable.getSelectionModel().getSelectedItem() == null) {
            alertBox(alertType.error, selectProductDelete, invalidSelection);
        } else {
            if (alertBox(alertType.confirmation, deleteProduct, confirmation)) {
                deleteProduct(prodTable.getSelectionModel().getSelectedItem());
                prodTable.setItems(getAllProducts());
            }
        }
    }

    // Method to display an alert box. The alert box will vary based on the alertType enum parameter that is input.
    public static boolean alertBox(alertType type, String contentText, String title) {
        if (type == alertType.confirmation) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, contentText, ButtonType.CANCEL, ButtonType.YES);
            alert.setTitle(title);
            alert.setHeaderText(title);
            Optional<ButtonType> confirm = alert.showAndWait();
            return (confirm.isPresent() && confirm.get() == ButtonType.YES);
        } else if (type == alertType.error) {
            Alert alert = new Alert(Alert.AlertType.ERROR, contentText);
            alert.setTitle(title);
            alert.setHeaderText(title);
            alert.showAndWait();
            return false;
        } else if (type == alertType.warning) {
            Alert alert = new Alert(Alert.AlertType.WARNING, contentText);
            alert.setTitle(title);
            alert.setHeaderText(title);
            alert.showAndWait();
            return false;
        } else return false;
    }

    // Sends the user to a different view screen based on the url parameter
    public static void viewScreen(MouseEvent event, String url) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Utility.class.getResource(url));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    // Method to close the program when the exit button (Window) is selected from the main window
    public static void exitProgram(WindowEvent event) throws ClassCastException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    // Overloaded method to close the program when the "Exit Button" on the main screen is clicked
    public static void exitProgram(MouseEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    // Method for cancel button, will prompt a confirmation message before sending user to main screen
    public static void cancelButton(MouseEvent event) throws IOException {
        if (alertBox(alertType.confirmation, cancel, confirmation)) {
            viewScreen(event, mainScreenFxmlUrl);
        }
    }

    // Method for the add associated part button.
    public static void addAssociatedPartButton(TableView<Part> partTable, TableView<Part> associatedPartTable, TableColumn<Part, Integer> partIdCol) {
        ObservableList<Part> associatedParts;
        associatedParts = associatedPartTable.getItems();
        associatedParts.addAll(partTable.getSelectionModel().getSelectedItems());
        associatedPartTable.setItems(associatedParts);
        associatedPartTable.getSortOrder().add(partIdCol);
    }

    // Sends user to the Modify Part / Product screen and pre-populates the fields with the data from the selected Part / Product
    public static void modifySelectedPart(MainScreenController mainScreenController, TableView<Part> partTable, MouseEvent event) throws IOException {
        if (partTable.getSelectionModel().getSelectedItem() == null) {
            alertBox(alertType.error, selectPartModify, invalidSelection);
        } else {
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
        } else {
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

    // Method to populate the All Products table
    public static void setAllProductsTable(TableView<Product> prodTable, TableColumn<Product, Integer> prodIdCol, TableColumn<Product, String> prodNameCol, TableColumn<Product, Integer> prodInvCol, TableColumn<Product, Integer> prodPriceCol) {
        prodTable.setItems(getAllProducts());
        prodTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        prodIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    // Method to populate the All Parts table
    public static void setAllPartsTable(TableView<Part> partTable, TableColumn<Part, Integer> partIdCol, TableColumn<Part, String> partNameCol, TableColumn<Part, Integer> partInvCol, TableColumn<Part, Double> partPriceCol) {
        partTable.setItems(getAllParts());
        partTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    // Overloaded to add parameter that changes the selection mode
    public static void setAllPartsTable(TableView<Part> partTable, TableColumn<Part, Integer> partIdCol, TableColumn<Part, String> partNameCol, TableColumn<Part, Integer> partInvCol, TableColumn<Part, Double> partPriceCol, SelectionMode mode) {
        partTable.setItems(getAllParts());
        partTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        partTable.getSelectionModel().setSelectionMode(mode);
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    // Method that sets an empty Associated Parts table
    public static void setAssociatedPartTable(TableColumn<Part, Integer> associatedPartIdCol, TableColumn<Part, Integer> associatedPartInvCol, TableColumn<Part, String> associatedPartNameCol, TableColumn<Part, Double> associatedPartPriceCol, TableView<Part> associatedPartTable) {
        associatedPartTable.getSortOrder().add(associatedPartIdCol);
        associatedPartTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    // Overloaded method that set an Associated Parts table, populated with the Associated Parts of a specific Product
    public static void setAssociatedPartTable(TableColumn<Part, Integer> associatedPartIdCol, TableColumn<Part, Integer> associatedPartInvCol, TableColumn<Part, String> associatedPartNameCol, TableColumn<Part, Double> associatedPartPriceCol, TableView<Part> associatedPartTable, Product product) {
        associatedPartTable.setItems(product.getAllAssociatedParts());
        associatedPartTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        associatedPartTable.getSortOrder().add(associatedPartIdCol);
        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    // Method to set text fields based on a specified product
    public static void setFields(Product p, TextField idField, TextField nameField, TextField invField, TextField priceField, TextField maxField, TextField minField) {
        idField.setText(String.valueOf(p.getId()));
        nameField.setText((p.getName()));
        invField.setText(String.valueOf(p.getStock()));
        priceField.setText(String.valueOf(p.getPrice()));
        maxField.setText((String.valueOf(p.getMax())));
        minField.setText((String.valueOf(p.getMin())));
    }

    // Overloaded method to set text fields based on a specified part
    public static void setFields(Part p, TextField idField, TextField nameField, TextField invField, TextField priceField, TextField maxField, TextField minField) {
        idField.setText(String.valueOf(p.getId()));
        nameField.setText((p.getName()));
        invField.setText(String.valueOf(p.getStock()));
        priceField.setText(String.valueOf(p.getPrice()));
        maxField.setText((String.valueOf(p.getMax())));
        minField.setText((String.valueOf(p.getMin())));
    }
}
