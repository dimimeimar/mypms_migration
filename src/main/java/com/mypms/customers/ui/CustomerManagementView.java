package com.mypms.customers.ui;

import com.mypms.customers.dao.PelatisDAO;
import com.mypms.customers.model.Pelatis;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * Διαχείριση Πελατών - JavaFX View με πλήρες functionality
 */
public class CustomerManagementView {

    private BorderPane view;
    private PelatisDAO pelatisDAO;

    // Table components
    private TableView<Pelatis> table;
    private ObservableList<Pelatis> pelatesList;

    // Search components
    private TextField txtSearch;
    private ComboBox<String> cmbKategoriaFilter;

    // Action buttons
    private Button btnNew;
    private Button btnEdit;
    private Button btnDelete;
    private Button btnRefresh;
    private Button btnViewDetails;

    public CustomerManagementView() {
        this.pelatisDAO = new PelatisDAO();
        this.pelatesList = FXCollections.observableArrayList();
        createView();
        loadData();
    }

    private void createView() {
        view = new BorderPane();
        view.setPadding(new Insets(0));

        // Header
        VBox header = createHeader();
        view.setTop(header);

        // Main content
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(20));
        mainContent.setStyle("-fx-background-color: -fx-light-gray;");

        // Search panel
        HBox searchPanel = createSearchPanel();

        // Table
        VBox tableContainer = createTable();
        VBox.setVgrow(tableContainer, Priority.ALWAYS);

        // Action buttons
        HBox buttonPanel = createButtonPanel();

        mainContent.getChildren().addAll(searchPanel, tableContainer, buttonPanel);
        view.setCenter(mainContent);
    }

    private VBox createHeader() {
        VBox header = new VBox();
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(25, 20, 25, 20));
        header.getStyleClass().add("panel-header");

        Label title = new Label("👥 Διαχείριση Πελατών");
        title.setStyle(
            "-fx-font-size: 28px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: white;"
        );

        Label subtitle = new Label("Διαχείριση πελατών, εταιριών και υπευθύνων");
        subtitle.setStyle(
            "-fx-font-size: 15px;" +
            "-fx-text-fill: rgba(255,255,255,0.9);"
        );

        header.getChildren().addAll(title, subtitle);
        VBox.setMargin(subtitle, new Insets(5, 0, 0, 0));

        return header;
    }

    private HBox createSearchPanel() {
        HBox searchPanel = new HBox(15);
        searchPanel.setAlignment(Pos.CENTER_LEFT);
        searchPanel.setPadding(new Insets(20));
        searchPanel.getStyleClass().add("search-panel");
        searchPanel.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 12px;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 6, 0, 0, 2);"
        );

        // Search label
        Label lblSearch = new Label("🔍 Αναζήτηση:");
        lblSearch.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        // Search text field
        txtSearch = new TextField();
        txtSearch.setPromptText("Επωνυμία, κωδικός, email...");
        txtSearch.setPrefWidth(300);
        txtSearch.getStyleClass().add("text-field");

        // Category filter label
        Label lblKategoria = new Label("Κατηγορία:");
        lblKategoria.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        // Category combo box
        cmbKategoriaFilter = new ComboBox<>();
        cmbKategoriaFilter.getItems().addAll("όλες", "A", "B", "C", "D", "E");
        cmbKategoriaFilter.setValue("όλες");
        cmbKategoriaFilter.setPrefWidth(120);
        cmbKategoriaFilter.getStyleClass().add("combo-box");

        // Search button
        Button btnSearch = new Button("Αναζήτηση");
        btnSearch.getStyleClass().add("button-primary");
        btnSearch.setOnAction(e -> searchCustomers());

        // Clear button
        Button btnClear = new Button("Καθαρισμός");
        btnClear.getStyleClass().add("button-secondary");
        btnClear.setOnAction(e -> {
            txtSearch.clear();
            cmbKategoriaFilter.setValue("όλες");
            loadData();
        });

        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Count label
        Label lblCount = new Label("Σύνολο: 0");
        lblCount.setStyle("-fx-font-weight: bold; -fx-text-fill: -fx-primary;");

        // Update count when list changes
        pelatesList.addListener((javafx.collections.ListChangeListener<Pelatis>) c -> {
            lblCount.setText("Σύνολο: " + pelatesList.size());
        });

        searchPanel.getChildren().addAll(
            lblSearch, txtSearch,
            lblKategoria, cmbKategoriaFilter,
            btnSearch, btnClear,
            spacer, lblCount
        );

        return searchPanel;
    }

    private VBox createTable() {
        VBox container = new VBox();
        container.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 12px;" +
            "-fx-padding: 15;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 6, 0, 0, 2);"
        );

        table = new TableView<>();
        table.setItems(pelatesList);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getStyleClass().add("table-view");

        // Add a placeholder
        Label placeholder = new Label("Δεν βρέθηκαν πελάτες");
        placeholder.setStyle("-fx-font-size: 16px; -fx-text-fill: -fx-text-muted;");
        table.setPlaceholder(placeholder);

        // Create columns
        TableColumn<Pelatis, String> colKodikos = new TableColumn<>("#");
        colKodikos.setCellValueFactory(new PropertyValueFactory<>("kodikosPelati"));
        colKodikos.setPrefWidth(80);

        TableColumn<Pelatis, String> colKategoria = new TableColumn<>("Κατηγορία");
        colKategoria.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
        colKategoria.setPrefWidth(90);

        TableColumn<Pelatis, String> colEponymia = new TableColumn<>("Επωνυμία Εταιρίας");
        colEponymia.setCellValueFactory(new PropertyValueFactory<>("eponymiaEtairias"));
        colEponymia.setPrefWidth(250);

        TableColumn<Pelatis, String> colTilefono = new TableColumn<>("Τηλέφωνο");
        colTilefono.setCellValueFactory(new PropertyValueFactory<>("tilefonoStathero"));
        colTilefono.setPrefWidth(120);

        TableColumn<Pelatis, String> colKinito = new TableColumn<>("Κινητό");
        colKinito.setCellValueFactory(new PropertyValueFactory<>("tilefonoKinito"));
        colKinito.setPrefWidth(120);

        TableColumn<Pelatis, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEmail.setPrefWidth(200);

        table.getColumns().addAll(
            colKodikos, colKategoria, colEponymia,
            colTilefono, colKinito, colEmail
        );

        // Double-click to view details
        table.setRowFactory(tv -> {
            TableRow<Pelatis> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    viewDetails();
                }
            });
            return row;
        });

        container.getChildren().add(table);
        VBox.setVgrow(table, Priority.ALWAYS);

        return container;
    }

    private HBox createButtonPanel() {
        HBox buttonPanel = new HBox(15);
        buttonPanel.setAlignment(Pos.CENTER);
        buttonPanel.setPadding(new Insets(20, 0, 0, 0));

        btnNew = new Button("➕ Νέος Πελάτης");
        btnNew.getStyleClass().add("button-success");
        btnNew.getStyleClass().add("button-lg");
        btnNew.setOnAction(e -> createNewCustomer());

        btnEdit = new Button("✏️ Επεξεργασία");
        btnEdit.getStyleClass().add("button-primary");
        btnEdit.getStyleClass().add("button-lg");
        btnEdit.setOnAction(e -> editCustomer());
        btnEdit.setDisable(true);

        btnDelete = new Button("🗑️ Διαγραφή");
        btnDelete.getStyleClass().add("button-danger");
        btnDelete.getStyleClass().add("button-lg");
        btnDelete.setOnAction(e -> deleteCustomer());
        btnDelete.setDisable(true);

        btnViewDetails = new Button("👁️ Λεπτομέρειες");
        btnViewDetails.getStyleClass().add("button-info");
        btnViewDetails.getStyleClass().add("button-lg");
        btnViewDetails.setOnAction(e -> viewDetails());
        btnViewDetails.setDisable(true);

        btnRefresh = new Button("🔄 Ανανέωση");
        btnRefresh.getStyleClass().add("button-secondary");
        btnRefresh.getStyleClass().add("button-lg");
        btnRefresh.setOnAction(e -> loadData());

        // Enable/disable buttons based on selection
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean hasSelection = newSelection != null;
            btnEdit.setDisable(!hasSelection);
            btnDelete.setDisable(!hasSelection);
            btnViewDetails.setDisable(!hasSelection);
        });

        buttonPanel.getChildren().addAll(
            btnNew, btnEdit, btnDelete, btnViewDetails, btnRefresh
        );

        return buttonPanel;
    }

    private void loadData() {
        try {
            pelatesList.clear();
            pelatesList.addAll(pelatisDAO.findAll());
        } catch (Exception e) {
            showError("Σφάλμα φόρτωσης δεδομένων", e.getMessage());
        }
    }

    private void searchCustomers() {
        try {
            String searchTerm = txtSearch.getText();
            String kategoria = cmbKategoriaFilter.getValue();

            pelatesList.clear();
            pelatesList.addAll(pelatisDAO.search(searchTerm, kategoria));
        } catch (Exception e) {
            showError("Σφάλμα αναζήτησης", e.getMessage());
        }
    }

    private void createNewCustomer() {
        showInfo("Νέος Πελάτης", "Η φόρμα δημιουργίας πελάτη θα προστεθεί σύντομα!");
    }

    private void editCustomer() {
        Pelatis selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showInfo("Επεξεργασία", "Επεξεργασία πελάτη: " + selected.getEponymiaEtairias());
        }
    }

    private void deleteCustomer() {
        Pelatis selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Επιβεβαίωση Διαγραφής");
            alert.setHeaderText("Διαγραφή πελάτη: " + selected.getEponymiaEtairias());
            alert.setContentText("Είστε σίγουροι ότι θέλετε να διαγράψετε αυτόν τον πελάτη;");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    if (pelatisDAO.delete(selected.getKodikosPelati())) {
                        showSuccess("Επιτυχία", "Ο πελάτης διαγράφηκε επιτυχώς");
                        loadData();
                    } else {
                        showError("Σφάλμα", "Αποτυχία διαγραφής πελάτη");
                    }
                }
            });
        }
    }

    private void viewDetails() {
        Pelatis selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showInfo("Λεπτομέρειες",
                "Κωδικός: " + selected.getKodikosPelati() + "\n" +
                "Επωνυμία: " + selected.getEponymiaEtairias() + "\n" +
                "Κατηγορία: " + selected.getKategoria() + "\n" +
                "Email: " + selected.getEmail() + "\n" +
                "Τηλέφωνο: " + selected.getTilefonoStathero()
            );
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public BorderPane getView() {
        return view;
    }
}
