package fr.ubordeaux.miage.s7.poo.projet.view;

import fr.ubordeaux.miage.s7.poo.projet.controller.MainController;
import fr.ubordeaux.miage.s7.poo.projet.controller.TransactionController;
import fr.ubordeaux.miage.s7.poo.projet.model.BienImmobilier;
import fr.ubordeaux.miage.s7.poo.projet.model.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;

public class TransactionView {
    private final Stage stage;
    private final ObservableList<BienImmobilier> biens;
    private final TransactionController transactionController;
    private final MainController mainController;

    private TableView<Transaction> table;

    public TransactionView(Stage stage, MainController mainController, ObservableList<BienImmobilier> biens) {
        this.stage = stage;
        this.biens = biens;
        this.transactionController = new TransactionController();
        this.mainController = mainController;
    }

    public void show() {
        BorderPane root = new BorderPane();

        // Table pour afficher les transactions
        table = new TableView<>();
        ObservableList<Transaction> transactions = FXCollections.observableArrayList();

        // Ajouter les transactions des biens
        for (BienImmobilier bien : biens) {
            transactions.addAll(bien.getTransactions());
        }

        table.setItems(transactions);

        // Colonne Titre
        TableColumn<Transaction, String> titleColumn = new TableColumn<>("Titre");
        titleColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getTitre())
        );

        // Colonne Date
        TableColumn<Transaction, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getDate().toString())
        );

        // Colonne Revenus
        TableColumn<Transaction, String> revenueColumn = new TableColumn<>("Revenus");
        revenueColumn.setCellValueFactory(cellData -> {
            Transaction transaction = cellData.getValue();
            return transaction.getType() == Transaction.TransactionType.REVENUE 
                ? new SimpleStringProperty(String.valueOf(transaction.getMontant())) 
                : new SimpleStringProperty("");
        });

        // Colonne Dépenses
        TableColumn<Transaction, String> expenseColumn = new TableColumn<>("Dépenses");
        expenseColumn.setCellValueFactory(cellData -> {
            Transaction transaction = cellData.getValue();
            return transaction.getType() == Transaction.TransactionType.EXPENSE 
                ? new SimpleStringProperty(String.valueOf(transaction.getMontant())) 
                : new SimpleStringProperty("");
        });

        // Colonne Loyer
        TableColumn<Transaction, String> rentColumn = new TableColumn<>("Loyer");
        rentColumn.setCellValueFactory(cellData -> {
            Transaction transaction = cellData.getValue();
            return transaction.getType() == Transaction.TransactionType.LOYER 
                ? new SimpleStringProperty(String.valueOf(transaction.getMontant())) 
                : new SimpleStringProperty("");
        });

        table.getColumns().addAll(titleColumn, dateColumn, revenueColumn, expenseColumn, rentColumn);
        
        root.setCenter(table);

        Button addTransactionButton = new Button("Enregistrer une transaction");
        addTransactionButton.setOnAction(e -> showAddTransactionForm());

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().add(addTransactionButton);

        Button backButton = new Button("Retour");
        backButton.setOnAction(e -> backToBienView());
        buttonBox.getChildren().add(backButton);

        root.setBottom(buttonBox);

        Scene scene = new Scene(root, 800, 400);
        stage.setTitle("Transactions des biens immobiliers");
        stage.setScene(scene);
        stage.show();
    }

    private void showAddTransactionForm() {
        Dialog<Transaction> dialog = new Dialog<>();
        dialog.setTitle("Ajout de transaction");
        dialog.setHeaderText("Veuillez entrer les informations de la transaction");

        TextField titleField = new TextField();
        titleField.setPromptText("Titre de la transaction");

        ComboBox<Transaction.TransactionType> typeComboBox = new ComboBox<>();
        typeComboBox.setItems(FXCollections.observableArrayList(
            Transaction.TransactionType.REVENUE,
            Transaction.TransactionType.EXPENSE,
            Transaction.TransactionType.LOYER
        ));

        TextField montantField = new TextField();
        montantField.setPromptText("Montant");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Titre :"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("Nature :"), 0, 1);
        grid.add(typeComboBox, 1, 1);
        grid.add(new Label("Montant :"), 0, 2);
        grid.add(montantField, 1, 2);

        dialog.getDialogPane().setContent(grid);

        ButtonType confirmButtonType = new ButtonType("Ajouter", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType) {
                String titre = titleField.getText();
                double montant = Double.parseDouble(montantField.getText());
                Transaction.TransactionType type = typeComboBox.getValue();

                BienImmobilier selectedBien = biens.get(0);

                if (type == Transaction.TransactionType.REVENUE) {
                    transactionController.enregistrerRevenu(montant, selectedBien, titre);
                } else if (type == Transaction.TransactionType.EXPENSE) {
                    transactionController.enregistrerDepense(montant, selectedBien, titre);
                } else if (type == Transaction.TransactionType.LOYER) {
                    transactionController.enregistrerLoyer(montant, selectedBien, titre);
                }

                refreshTransactions();
            }
            return null;
        });

        dialog.showAndWait();
    }

    //permet de rafraichir la page sans avoir besoin de quitter puis revenir pour voir les changements
    private void refreshTransactions() {
        ObservableList<Transaction> transactions = FXCollections.observableArrayList();
        for (BienImmobilier bien : biens) {
            transactions.addAll(bien.getTransactions());
        }
        table.setItems(transactions);
    }

    private void backToBienView() {
        mainController.openManagementView();
    }
}
