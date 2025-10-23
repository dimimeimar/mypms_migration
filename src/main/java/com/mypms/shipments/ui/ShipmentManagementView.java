package com.mypms.shipments.ui;

import com.mypms.shipments.dao.ApostoliDAO;
import com.mypms.shipments.model.Apostoli;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Διαχείριση Αποστολών - JavaFX View με πλήρες functionality
 */
public class ShipmentManagementView {

    private BorderPane view;
    private ApostoliDAO apostoliDAO;

    // Table components
    private TableView<Apostoli> table;
    private ObservableList<Apostoli> apostolesList;

    // Search components
    private TextField txtSearch;
    private ComboBox<String> cmbCourierFilter;
    private ComboBox<String> cmbStatusFilter;

    // Action buttons
    private Button btnNew;
    private Button btnEdit;
    private Button btnDelete;
    private Button btnRefresh;
    private Button btnViewDetails;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ShipmentManagementView() {
        this.apostoliDAO = new ApostoliDAO();
        this.apostolesList = FXCollections.observableArrayList();
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

        Label title = new Label("📦 Διαχείριση Αποστολών");
        title.setStyle(
            "-fx-font-size: 28px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: white;"
        );

        Label subtitle = new Label("Αποστολές, παρακολούθηση και tracking");
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
        txtSearch.setPromptText("Αριθμός αποστολής, παραλήπτης, πόλη...");
        txtSearch.setPrefWidth(300);
        txtSearch.getStyleClass().add("text-field");

        // Courier filter label
        Label lblCourier = new Label("Courier:");
        lblCourier.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        // Courier combo box
        cmbCourierFilter = new ComboBox<>();
        cmbCourierFilter.getItems().addAll("όλοι", "ACS", "ΕΛΤΑ", "Speedex", "Γενική Ταχυδρομική", "DHL");
        cmbCourierFilter.setValue("όλοι");
        cmbCourierFilter.setPrefWidth(150);
        cmbCourierFilter.getStyleClass().add("combo-box");

        // Status filter label
        Label lblStatus = new Label("Κατάσταση:");
        lblStatus.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        // Status combo box
        cmbStatusFilter = new ComboBox<>();
        cmbStatusFilter.getItems().addAll("όλα", "PENDING", "IN_TRANSIT", "DELIVERED", "RETURNED", "PROBLEM");
        cmbStatusFilter.setValue("όλα");
        cmbStatusFilter.setPrefWidth(130);
        cmbStatusFilter.getStyleClass().add("combo-box");

        // Search button
        Button btnSearch = new Button("Αναζήτηση");
        btnSearch.getStyleClass().add("button-primary");
        btnSearch.setOnAction(e -> searchShipments());

        // Clear button
        Button btnClear = new Button("Καθαρισμός");
        btnClear.getStyleClass().add("button-secondary");
        btnClear.setOnAction(e -> {
            txtSearch.clear();
            cmbCourierFilter.setValue("όλοι");
            cmbStatusFilter.setValue("όλα");
            loadData();
        });

        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Count label
        Label lblCount = new Label("Σύνολο: 0");
        lblCount.setStyle("-fx-font-weight: bold; -fx-text-fill: -fx-primary;");

        // Update count when list changes
        apostolesList.addListener((javafx.collections.ListChangeListener<Apostoli>) c -> {
            lblCount.setText("Σύνολο: " + apostolesList.size());
        });

        searchPanel.getChildren().addAll(
            lblSearch, txtSearch,
            lblCourier, cmbCourierFilter,
            lblStatus, cmbStatusFilter,
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
        table.setItems(apostolesList);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getStyleClass().add("table-view");

        // Add a placeholder
        Label placeholder = new Label("Δεν βρέθηκαν αποστολές");
        placeholder.setStyle("-fx-font-size: 16px; -fx-text-fill: -fx-text-muted;");
        table.setPlaceholder(placeholder);

        // Create columns
        TableColumn<Apostoli, Integer> colId = new TableColumn<>("#");
        colId.setCellValueFactory(new PropertyValueFactory<>("idApostolis"));
        colId.setPrefWidth(60);

        TableColumn<Apostoli, String> colArithmos = new TableColumn<>("Αρ. Αποστολής");
        colArithmos.setCellValueFactory(new PropertyValueFactory<>("arithmosApostolis"));
        colArithmos.setPrefWidth(130);

        TableColumn<Apostoli, String> colCourier = new TableColumn<>("Courier");
        colCourier.setCellValueFactory(new PropertyValueFactory<>("courier"));
        colCourier.setPrefWidth(100);

        TableColumn<Apostoli, String> colParaliptis = new TableColumn<>("Παραλήπτης");
        colParaliptis.setCellValueFactory(new PropertyValueFactory<>("paraliptis"));
        colParaliptis.setPrefWidth(180);

        TableColumn<Apostoli, String> colPoli = new TableColumn<>("Πόλη");
        colPoli.setCellValueFactory(new PropertyValueFactory<>("poli"));
        colPoli.setPrefWidth(130);

        TableColumn<Apostoli, LocalDate> colImerominia = new TableColumn<>("Ημερομηνία");
        colImerominia.setCellValueFactory(new PropertyValueFactory<>("imerominiaParalabis"));
        colImerominia.setPrefWidth(110);
        colImerominia.setCellFactory(column -> new TableCell<Apostoli, LocalDate>() {
            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (empty || date == null) {
                    setText(null);
                } else {
                    setText(DATE_FORMATTER.format(date));
                }
            }
        });

        TableColumn<Apostoli, BigDecimal> colAntikatavoli = new TableColumn<>("Αντικ/λή");
        colAntikatavoli.setCellValueFactory(new PropertyValueFactory<>("antikatavoli"));
        colAntikatavoli.setPrefWidth(90);
        colAntikatavoli.setCellFactory(column -> new TableCell<Apostoli, BigDecimal>() {
            @Override
            protected void updateItem(BigDecimal amount, boolean empty) {
                super.updateItem(amount, empty);
                if (empty || amount == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f €", amount));
                }
            }
        });

        TableColumn<Apostoli, String> colStatus = new TableColumn<>("Κατάσταση");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("statusMypms"));
        colStatus.setPrefWidth(120);
        colStatus.setCellFactory(column -> new TableCell<Apostoli, String>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(status);
                    // Color-code status
                    switch (status.toUpperCase()) {
                        case "DELIVERED" -> setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                        case "IN_TRANSIT" -> setStyle("-fx-text-fill: blue; -fx-font-weight: bold;");
                        case "PENDING" -> setStyle("-fx-text-fill: orange; -fx-font-weight: bold;");
                        case "RETURNED", "PROBLEM" -> setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                        default -> setStyle("");
                    }
                }
            }
        });

        table.getColumns().addAll(
            colId, colArithmos, colCourier, colParaliptis,
            colPoli, colImerominia, colAntikatavoli, colStatus
        );

        // Double-click to view details
        table.setRowFactory(tv -> {
            TableRow<Apostoli> row = new TableRow<>();
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

        btnNew = new Button("➕ Νέα Αποστολή");
        btnNew.getStyleClass().add("button-success");
        btnNew.getStyleClass().add("button-lg");
        btnNew.setOnAction(e -> createNewShipment());

        btnEdit = new Button("✏️ Επεξεργασία");
        btnEdit.getStyleClass().add("button-primary");
        btnEdit.getStyleClass().add("button-lg");
        btnEdit.setOnAction(e -> editShipment());
        btnEdit.setDisable(true);

        btnDelete = new Button("🗑️ Διαγραφή");
        btnDelete.getStyleClass().add("button-danger");
        btnDelete.getStyleClass().add("button-lg");
        btnDelete.setOnAction(e -> deleteShipment());
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
            apostolesList.clear();
            apostolesList.addAll(apostoliDAO.findAll());
        } catch (Exception e) {
            showError("Σφάλμα φόρτωσης δεδομένων", e.getMessage());
            e.printStackTrace();
        }
    }

    private void searchShipments() {
        try {
            String searchTerm = txtSearch.getText();
            String courier = cmbCourierFilter.getValue();
            String status = cmbStatusFilter.getValue();

            apostolesList.clear();
            apostolesList.addAll(apostoliDAO.search(searchTerm, courier, status));
        } catch (Exception e) {
            showError("Σφάλμα αναζήτησης", e.getMessage());
        }
    }

    private void createNewShipment() {
        showInfo("Νέα Αποστολή", "Η φόρμα δημιουργίας αποστολής θα προστεθεί σύντομα!");
    }

    private void editShipment() {
        Apostoli selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showInfo("Επεξεργασία", "Επεξεργασία αποστολής: " + selected.getArithmosApostolis());
        }
    }

    private void deleteShipment() {
        Apostoli selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Επιβεβαίωση Διαγραφής");
            alert.setHeaderText("Διαγραφή αποστολής: " + selected.getArithmosApostolis());
            alert.setContentText("Είστε σίγουροι ότι θέλετε να διαγράψετε αυτήν την αποστολή;");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    if (apostoliDAO.delete(selected.getIdApostolis())) {
                        showSuccess("Επιτυχία", "Η αποστολή διαγράφηκε επιτυχώς");
                        loadData();
                    } else {
                        showError("Σφάλμα", "Αποτυχία διαγραφής αποστολής");
                    }
                }
            });
        }
    }

    private void viewDetails() {
        Apostoli selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showInfo("Λεπτομέρειες Αποστολής",
                "ID: " + selected.getIdApostolis() + "\n" +
                "Αριθμός: " + selected.getArithmosApostolis() + "\n" +
                "Courier: " + selected.getCourier() + "\n" +
                "Παραλήπτης: " + selected.getParaliptis() + "\n" +
                "Διεύθυνση: " + selected.getDiefthinsi() + ", " + selected.getPoli() + "\n" +
                "Τηλέφωνο: " + selected.getTilefonoKinito() + "\n" +
                "Αντικαταβολή: " + (selected.getAntikatavoli() != null ? selected.getAntikatavoli() + " €" : "Χωρίς") + "\n" +
                "Κατάσταση: " + selected.getStatusMypms()
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
