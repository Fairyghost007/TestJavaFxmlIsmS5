package com.example.controllers.Boutiquier;

import java.io.IOException;

import com.example.controllers.Boutiquier.BoutiquierMainController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class BoutiquierMenuController {

    @FXML
    public Button articleBtn;

    @FXML
    public Button clientBtn;

    @FXML
    public Button userBtn;

    @FXML
    public Button detteBtn;

    @FXML
    public Button demandeBtn;

    @FXML
    public Button logoutBtn;

    private BoutiquierMainController mainController; 

    public void setMainController(BoutiquierMainController mainController) {
        this.mainController = mainController;
    }

   

@FXML
    public void logout() throws IOException {
        System.out.println("Logout button clicked!");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/Fxml/Connexion.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) logoutBtn.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    
}
