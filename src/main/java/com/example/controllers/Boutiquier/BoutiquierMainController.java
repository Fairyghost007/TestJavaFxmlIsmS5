package com.example.controllers.Boutiquier;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXToggleButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class BoutiquierMainController implements Initializable {
    @FXML
    private AnchorPane contentPane;


    @FXML
    private JFXToggleButton toggleBtn;

    @FXML
    private BorderPane mainPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            FXMLLoader menuLoader = new FXMLLoader(
                    getClass().getResource("/com/example/Fxml/Boutiquier/BoutiquierMenu.fxml"));
            VBox menuPane = menuLoader.load();
            BoutiquierMenuController menuController = menuLoader.getController();
            menuController.setMainController(this);
            mainPane.setLeft(menuPane);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error loading menu", "Could not load the menu. Please try again.");
        }
    }



    

    public void initializeAddDette() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/com/example/Fxml/Boutiquier/BoutiquierAddDette.fxml"));
        AnchorPane addDettePane = fxmlLoader.load();
        BoutiquierAddDetteController addDetteController = fxmlLoader.getController();
        addDetteController.setMainController(this);
        contentPane.getChildren().setAll(addDettePane);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
