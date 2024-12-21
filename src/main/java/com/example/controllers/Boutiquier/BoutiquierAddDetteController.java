package com.example.controllers.Boutiquier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.entities.Article;
import com.example.entities.Client;
import com.example.entities.Detail;
import com.example.entities.Commande;
import com.example.enums.Statut;
import com.example.repositories.jpa.ArticleRepositoryJpa;
import com.example.repositories.jpa.ClientRepositoryJpa;
import com.example.repositories.jpa.DetailRepositoryJpa;
import com.example.repositories.jpa.CommandeRepositoryJpa;
import com.example.services.ArticleServiceImpl;
import com.example.services.ClientServiceImpl;
import com.example.services.DetailServiceImpl;
import com.example.services.CommandeServiceImpl;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.scene.control.TableView;

public class BoutiquierAddDetteController {
    @FXML
    private ComboBox<Article> cbArticle;

    @FXML
    private TextField quantityField;
    @FXML
    private TextField searchTelephoneField;
    @FXML
    private TextField nomClientField;
    @FXML
    private TextField addresseClientField;

    @FXML
    private Text errormessage;

    @FXML
    public Button searchButton;

    @FXML
    private TableView<Detail> articleTable;

    @FXML
    private TableView<Commande> detteTable;

    @FXML
    private TableColumn<Detail, String> articleNameColumn;

    @FXML
    private TableColumn<Detail, Integer> quantityColumn;

    @FXML
    private TableColumn<Detail, Double> totalColumn;

    @FXML
    private TableColumn<Detail, Void> actionsColumn;

    @FXML
    private Text totalMontantDette;

    private DoubleProperty totalMontant = new SimpleDoubleProperty(0.0);

    private BoutiquierMainController mainController;
    private ArticleServiceImpl articleService = new ArticleServiceImpl(new ArticleRepositoryJpa());
    private final CommandeServiceImpl DetteService = new CommandeServiceImpl(new CommandeRepositoryJpa());
    private final DetailServiceImpl detailServiceImpl = new DetailServiceImpl(new DetailRepositoryJpa());
    private ClientServiceImpl ClientService = new ClientServiceImpl(new ClientRepositoryJpa());
    private ObservableList<Detail> detailsList = FXCollections.observableArrayList();
    private Integer clientId;
    private Client client;

    // @FXML
    // public void initialize() {
    // totalMontantDette.textProperty().bind(totalMontant.asString("%.2f"));
    // cbArticle.setItems(FXCollections.observableArrayList());
    // cbArticle.setButtonCell(new ListCell<Article>() {
    // @Override
    // protected void updateItem(Article item, boolean empty) {
    // super.updateItem(item, empty);
    // setText((item != null) ? item.getLibelle() : null);
    // }
    // });
    // cbArticle.setCellFactory(param -> new ListCell<Article>() {
    // @Override
    // protected void updateItem(Article item, boolean empty) {
    // super.updateItem(item, empty);
    // setText((item != null) ? item.getLibelle() : null);
    // }
    // });
    // loadArticleDisponible();
    // articleTable.setItems(detailsList);
    // setupTableColumns();
    // }
    @FXML
    public void initialize() {
        totalMontantDette.textProperty().bind(totalMontant.asString("%.2f"));
        cbArticle.setItems(FXCollections.observableArrayList());
        cbArticle.setButtonCell(new ListCell<Article>() {
            @Override
            protected void updateItem(Article item, boolean empty) {
                super.updateItem(item, empty);
                setText((item != null) ? item.getLibelle() : null);
            }
        });
        cbArticle.setCellFactory(param -> new ListCell<Article>() {
            @Override
            protected void updateItem(Article item, boolean empty) {
                super.updateItem(item, empty);
                setText((item != null) ? item.getLibelle() : null);
            }
        });
        loadArticleDisponible();
        articleTable.setItems(detailsList);
        setupTableColumns();
    }

    @FXML
    public void handleSearchClient() {
        String phoneNumber = searchTelephoneField.getText().trim();

        if (phoneNumber.isEmpty()) {
            errormessage.setText("Veuillez entrer un numéro de téléphone.");
            clearClientFields();
            return;
        }

        try {
            client = ClientService.search(phoneNumber);

            if (client == null) {
                errormessage.setText("Client non trouvé.");
                clearClientFields();
                return;
            }

            // Populate client details
            nomClientField.setText(client.getSurname());
            addresseClientField.setText(client.getAddress());
            errormessage.setText(""); // Clear error message
        } catch (Exception e) {
            e.printStackTrace();
            errormessage.setText("Une erreur s'est produite lors de la recherche.");
        }
    }

    private void clearClientFields() {
        nomClientField.clear();
        addresseClientField.clear();
        client = null;
    }

    // private void setupTableColumns() {
    // articleNameColumn.setCellValueFactory(cellData -> {
    // Article article =
    // articleService.getById(cellData.getValue().getArticle().getId());
    // return new ReadOnlyStringWrapper(article.getLibelle());
    // });
    // quantityColumn.setCellValueFactory(new PropertyValueFactory<>("qteDette"));
    // totalColumn.setCellValueFactory(new PropertyValueFactory<>("montant"));
    // }

    private void setupTableColumns() {
        articleNameColumn.setCellValueFactory(cellData -> {
            Article article = articleService.getById(cellData.getValue().getArticle().getId());
            return new ReadOnlyStringWrapper(article.getLibelle());
        });
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("qteDette"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("montant"));

        // Setup the actions column to have a delete button for each row
        actionsColumn.setCellFactory(param -> {
            Button deleteButton = new Button("Delete");
            deleteButton.setOnAction(event -> handleDeleteDetail(event)); // Button click calls delete handler

            TableCell<Detail, Void> cell = new TableCell<Detail, Void>() {
                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null); // Empty cells should not have any button
                    } else {
                        setGraphic(deleteButton); // Non-empty cells have the delete button
                    }
                }
            };
            return cell;
        });
    }

    @FXML
    public void handleDeleteDetail(javafx.event.ActionEvent event) {
        // Get the selected item from the table
        Detail selectedDetail = articleTable.getSelectionModel().getSelectedItem();

        if (selectedDetail != null) {
            // Proceed with deletion if an item is selected
            detailsList.remove(selectedDetail); // Remove the selected item from the list

            // Recalculate the total amount after deletion
            recalculateTotalMontant();

            // Explicitly set the items again to refresh the table view
            articleTable.setItems(detailsList);

            // Clear any previous error message
            errormessage.setText("");
        } else {
            // If no item is selected, show the error message
            errormessage.setText("Veuillez sélectionner un article à supprimer.");
        }
    }

    @FXML
    public void handleSaveDette() {
        if (client == null) {
            errormessage.setText("Veuillez rechercher et sélectionner un client avant de créer une dette.");
            return;
        }

        if (detailsList.isEmpty()) {
            errormessage.setText("Veuillez ajouter au moins un article.");
            return;
        }
        Commande dette = new Commande();
        dette.setStatus(Statut.ENCOURS);
        dette.setClient(client);
        dette.setMontant(0.0);
        dette.setMontantVerser(0.0);
        dette.setMontantRestant(0.0);
        DetteService.create(dette);

        double totalMontant = 0.0;
        for (Detail detail : detailsList) {
            detail.setDette(dette);
            detailServiceImpl.create(detail);
            Article article = articleService.getById(detail.getArticle().getId());
            if (article != null) {
                Integer newStock = article.getQteStock() - detail.getQteDette();
                article.setQteStock(newStock);
                articleService.update(article);
            }
            totalMontant += detail.getMontant();
        }
        dette.setMontant(totalMontant);
        dette.setMontantRestant(totalMontant);
        DetteService.update(dette);
        detailsList.clear();
        articleTable.setItems(detailsList);
        // redirectToDette();
    }

    @FXML
    public void handleAddArticle() {
        if (client == null) {
            errormessage.setText("Veuillez rechercher et sélectionner un client avant de créer une dette.");
            return;
        }
        Article selectedArticle = cbArticle.getValue();
        if (selectedArticle == null || quantityField.getText().isEmpty()) {
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityField.getText());
        } catch (NumberFormatException e) {
            return;
        }

        for (Detail detail : detailsList) {
            if (detail.getArticle().getId().equals(selectedArticle.getId())) {
                int newQuantity = detail.getQteDette() + quantity;
                detail.setQteDette(newQuantity);
                detail.setMontant(newQuantity * selectedArticle.getPrix());
                recalculateTotalMontant();
                articleTable.refresh();
                clearFields();
                return;
            }
        }

        Detail detail = new Detail();
        detail.setArticle(selectedArticle);
        detail.setQteDette(quantity);
        detail.setMontant(quantity * selectedArticle.getPrix());
        detailsList.add(detail);
        recalculateTotalMontant();
        articleTable.setItems(detailsList);
        clearFields();
    }

    private void recalculateTotalMontant() {
        double total = detailsList.stream()
                .mapToDouble(Detail::getMontant)
                .sum();
        totalMontant.set(total);
    }

    public void setMainController(BoutiquierMainController mainController) {
        this.mainController = mainController;
    }

    public void handleCancel() {
        clearAllFields();
        detailsList.clear();
        articleTable.setItems(detailsList);
        // redirectToDette();
    }

    public void loadArticleDisponible() {
        ObservableList<Article> listArticle = FXCollections
                .observableArrayList(articleService.findAllArticleDisponible());
        cbArticle.setItems(listArticle);
    }

    private void clearAllFields() {
        cbArticle.setValue(null);
        quantityField.clear();
        nomClientField.clear();
        addresseClientField.clear();
        errormessage.setText("");
        searchTelephoneField.clear();
        totalMontant.set(0.0);
        // detailsList.clear();
        client = null;
    }

    private void clearFields() {
        cbArticle.setValue(null);
        quantityField.clear();
    }

    @FXML
    public void redirectToDette() {
        try {
            mainController.initializeAddDette();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
