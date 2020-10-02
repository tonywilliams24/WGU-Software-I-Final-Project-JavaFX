package Model;

import View_Controller.Utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.LinkedList;
import java.util.Queue;

import static View_Controller.Utility.*;

public class Inventory {

    // Required Observable Lists per UML Diagram:
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    private ObservableList<Part> allSearchedParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allSearchedProducts = FXCollections.observableArrayList();
    private static StringBuilder searchPartBuilder = new StringBuilder();
    private static StringBuilder onlyAssociatedPartBuilder = new StringBuilder();
    private static Queue<Product> searchProductQueue = new LinkedList<>();
    private static Queue<Product> onlyAssociatedPartQueue = new LinkedList<>();
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
        if (!allParts.isEmpty()) {
            for (int i = 0; i < allParts.size(); i++) {
                if (allParts.get(i).getId() == partID) return allParts.get(i);
            }
        }
        return null;
    }

    public static Product lookupProduct(int productID) {
        if (!allProducts.isEmpty()) {
            for (int i = 0; i < allProducts.size(); i++) {
                if (allProducts.get(i).getId() == productID) return allProducts.get(i);
            }
        }
        return null;
    }

    public static Part lookupPart(String partName) {
        if (!allParts.isEmpty()) {
            for (int i = 0; i < allParts.size(); i++) {
                if (allParts.get(i).getName() == partName) return allParts.get(i);
            }
        }
        return null;
    }

    public static Product lookupProduct(String productName) {
        if (!allProducts.isEmpty()) {
            for (int i = 0; i < allProducts.size(); i++) {
                if (allParts.get(i).getName() == productName) return allProducts.get(i);
            }
        }
        return null;
    }

    public static void updatePart(int partId, Part part){
        int index = -1;
        for (Part p : getAllParts()) {
            index++;
            if(p.getId() == partId) {
                getAllParts().set(index, part);
                return;
            }
        }
    }

    public static void updateProduct(int productId, Product product){
        int index = -1;
        for (Product p : getAllProducts()) {
            index++;
            if(p.getId() == productId) {
                getAllProducts().set(index, product);
                return;
            }
        }
    }

    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    public static boolean deletePart(Part selectedPart, Queue<Product> productQueue) {
        for (Product product : productQueue) product.deleteAssociatedPart(selectedPart);
        return allParts.remove(selectedPart);
    }

    public static boolean deletePart(Part selectedPart, ObservableList<Part> partObservableList) {
        return partObservableList.remove(selectedPart);
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

    public ObservableList<Part> getAllSearchedParts() {
        return allSearchedParts;
    }

    public static ObservableList<Product> getAllSearchedProducts() {
        return allSearchedProducts;
    }

    public static Queue<Product> getOnlyAssociatedPartQueue() {
        return onlyAssociatedPartQueue;
    }

    public static Queue<Product> getSearchProductQueue() {
        return searchProductQueue;
    }

    public static StringBuilder getSearchPartBuilder() {
        return searchPartBuilder;
    }

    public static StringBuilder getOnlyAssociatedPartBuilder() {
        return onlyAssociatedPartBuilder;
    }

    public static ObservableList<Part> searchPartResult(int search, ObservableList<Part> partObservableList) {
        ObservableList<Part> tempPartsList = FXCollections.observableArrayList();
        for (Part part : partObservableList) {
            if (part.getId() == search) {
                tempPartsList.add(part);
            }
        }
        return tempPartsList;
    }

    public static ObservableList<Part> searchPartResult(String search) {
        ObservableList<Part> tempPartsList = FXCollections.observableArrayList();
        search = search.trim();
        for (Part part : getAllParts()) {
            if (part.getName().contains(search) || Integer.toString(part.getId()).contains(search)) {
                tempPartsList.add(part);
            }
        }
        if (tempPartsList.isEmpty()) {
            alertBox(Utility.alertType.error, partNotFound, notFound);
            return getAllParts();
        } else {
            return tempPartsList;
        }
    }

    public static ObservableList<Product> searchProductResult(String search) {
        ObservableList<Product> tempProductsList = FXCollections.observableArrayList();
        search = search.trim();
        for (Product product : getAllProducts()) {
            if (product.getName().contains(search) || Integer.toString(product.getId()).contains(search)) {
                tempProductsList.add(product);
            }
        }
        if (tempProductsList.isEmpty()) {
            alertBox(alertType.error, productNotFound, notFound);
            return getAllProducts();
        } else {
            return tempProductsList;
        }
    }

    public static boolean searchAssociatedParts(Part selectedPart) {
        searchPartBuilder.setLength(0);
        onlyAssociatedPartBuilder.setLength(0);
        onlyAssociatedPartQueue.clear();
        searchProductQueue.clear();
        for (Product product : getAllProducts()) {
            boolean partFound = false;
            boolean onlyAssociatedPart = true;
            for (Part part : searchPartResult(selectedPart.getId(), product.getAllAssociatedParts())) {
                partFound = true;
                searchProductQueue.add(product);
            }
            for (Part associatedPart : product.getAllAssociatedParts()) {
                if (!(associatedPart.getId() == selectedPart.getId())) {
                    onlyAssociatedPart = false;
                }
            }
            if (onlyAssociatedPart) {
                onlyAssociatedPartQueue.add(product);
                onlyAssociatedPartBuilder.append("Product ID: " + product.getId() + "  (" + product.getName() + ")\n");
            }
            if (partFound) {
                searchPartBuilder.append("Product ID: ");
                searchPartBuilder.append(product.getId());
                searchPartBuilder.append("  (");
                searchPartBuilder.append(product.getName());
                searchPartBuilder.append(")");
                searchPartBuilder.append("\n");
            }
        }
        if(!onlyAssociatedPartQueue.isEmpty()) {
            while (!onlyAssociatedPartQueue.isEmpty()) {
                System.out.println("Product ID: " + onlyAssociatedPartQueue.poll().getId());
            }
            return false;
        }
        return true;
    }


}