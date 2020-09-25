package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> allSearchedParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allSearchedProducts = FXCollections.observableArrayList();
    private static int partID = 0;
    private static int productID = 0;

    public static int getPartID() {
        return partID;
    }
    public static int incPartID() {
        return ++partID;
    }
    public static int getProductID() {
        return productID;
    }
    public static int incProductID() {
        return ++productID;
    }
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }
    public static Part lookupPart(int partID) {
        if(!allParts.isEmpty()) {
            for (int i = 0; i < allParts.size(); i++) {
                if (allParts.get(i).getId() == partID) return allParts.get(i);
            }
        }
        return null;
    }
    public static Product lookupProduct(int productID) {
        if(!allProducts.isEmpty()) {
            for (int i = 0; i < allProducts.size(); i++) {
                if (allProducts.get(i).getId() == productID) return allProducts.get(i);
            }
        }
        return null;
    }
    public static Part lookupPart(String partName) {
        if(!allParts.isEmpty()) {
            for (int i = 0; i < allParts.size(); i++) {
                if (allParts.get(i).getName() == partName) return allParts.get(i);
            }
        }
        return null;
    }
    public static Product lookupProduct(String productName) {
        if(!allProducts.isEmpty()) {
            for (int i = 0; i < allProducts.size(); i++) {
                if (allParts.get(i).getName() == productName) return allProducts.get(i);
            }
        }
        return null;
    }
    public static void updatePart(int index, Part selectedPart) {
        allParts.add(index, selectedPart);
        allParts.remove(++index);
    }
    public static void updateProduction(int index, Product selectedProduct) {
        allProducts.add(index, selectedProduct);
        allProducts.remove(++index);
    }
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    public static ObservableList<Part> getAllSearchedParts() {
        return allSearchedParts;
    }

    public static ObservableList<Product> getAllSearchedProducts() {
        return allSearchedProducts;
    }
}
