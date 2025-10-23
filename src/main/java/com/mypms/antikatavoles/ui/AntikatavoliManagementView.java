package com.mypms.antikatavoles.ui;

import com.mypms.antikatavoles.dao.AntikatavoliDAO;
import com.mypms.antikatavoles.model.Antikatavoli;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Διαχείριση Αντικαταβολών - JavaFX View με πλήρες functionality
 */
public class AntikatavoliManagementView {

    private BorderPane view;
    private AntikatavoliDAO antikatavoliDAO;

    // Table components
    private TableView<Antikatavoli> table;
    private ObservableList<Antikatavoli> antikatavoliList;

    // Action buttons
    private Button btnNew;
    private Button btnEdit;
    private Button btnDelete;
    private Button btnRefresh;
    private Button btnMarkPaid;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public AntikatavoliManagementView() {
        this.antikatavoliDAO = new AntikatavoliDAO();
        this.antikatavoliList = FXCollections.observableArrayList();
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

        // Stats panel
        HBox statsPanel = createStatsPanel();

        // Table
        VBox tableContainer = createTable();
        VBox.setVgrow(tableContainer, Priority.ALWAYS);

        // Action buttons
        HBox buttonPanel = createButtonPanel();

        mainContent.getChildren().addAll(statsPanel, tableContainer, buttonPanel);
        view.setCenter(mainContent);
    }

    private VBox createHeader() {
        VBox header = new VBox();
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(25, 20, 25, 20));
        header.getStyleClass().add("panel-header");

        Label title = new Label("💰 Διαχείριση Αντικαταβολών");
        title.setStyle(
            "-fx-font-size: 28px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: white;"
        );

        Label subtitle = new Label("Αντικαταβολές και αποδόσεις");
        subtitle.setStyle(
            "-fx-font-size: 15px;" +
            "-fx-text-fill: rgba(255,255,255,0.9);"
        );

        header.getChildren().addAll(title, subtitle);
        VBox.setMargin(subtitle, new Insets(5, 0, 0, 0));

        return header;
    }

    private HBox createStatsPanel() {
        HBox statsPanel = new HBox(20);
        statsPanel.setAlignment(Pos.CENTER);
        statsPanel.setPadding(new Insets(20));
        statsPanel.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 12px;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 6, 0, 0, 2);"
        );

        // Total label
        Label lblTotal = new Label("Σύνολο: 0");
        lblTotal.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: -fx-primary;");

        // Paid label
        Label lblPaid = new Label("Αποδοθέντα: 0");
        lblPaid.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: green;");

        // Pending label
        Label lblPending = new Label("Εκκρεμή: 0");
        lblPending.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: orange;");

        // Update stats when list changes
        antikatavoliList.addListener((javafx.collections.ListChangeListener<Antikatavoli>) c -> {
            int total = antikatavoliList.size();
            long paid = antikatavoliList.stream().filter(Antikatavoli::getApodothike).count();
            long pending = total - paid;

            lblTotal.setText("Σύνολο: " + total);
            lblPaid.setText("Αποδοθέντα: " + paid);
            lblPending.setText("Εκκρεμή: " + pending);
        });

        statsPanel.getChildren().addAll(lblTotal, new Separator(), lblPaid, new Separator(), lblPending);

        return statsPanel;
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
        table.setItems(antikatavoliList);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getStyleClass().add("table-view");

        // Add a placeholder
        Label placeholder = new Label("Δεν βρέθηκαν αντικαταβολές");
        placeholder.setStyle("-fx-font-size: 16px; -fx-text-fill: -fx-text-muted;");
        table.setPlaceholder(placeholder);

        // Create columns
        TableColumn<Antikatavoli, Integer> colId = new TableColumn<>("#");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(60);

        TableColumn<Antikatavoli, Integer> colIdApostolis = new TableColumn<>("ID Αποστολής");
        colIdApostolis.setCellValueFactory(new PropertyValueFactory<>("idApostolis"));
        colIdApostolis.setPrefWidth(120);

        TableColumn<Antikatavoli, Boolean> colApodothike = new TableColumn<>("Κατάσταση");
        colApodothike.setCellValueFactory(new PropertyValueFactory<>("apodothike"));
        colApodothike.setPrefWidth(120);
        colApodothike.setCellFactory(column -> new TableCell<Antikatavoli, Boolean>() {
            @Override
            protected void updateItem(Boolean apodothike, boolean empty) {
                super.updateItem(apodothike, empty);
                if (empty || apodothike == null) {
                    setText(null);
                    setStyle("");
                } else {
                    if (apodothike) {
                        setText("✓ Αποδόθηκε");
                        setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                    } else {
                        setText("⏳ Εκκρεμεί");
                        setStyle("-fx-text-fill: orange; -fx-font-weight: bold;");
                    }
                }
            }
        });

        TableColumn<Antikatavoli, LocalDate> colImerominia = new TableColumn<>("Ημ/νία Απόδοσης");
        colImerominia.setCellValueFactory(new PropertyValueFactory<>("imerominiaApodosis"));
        colImerominia.setPrefWidth(130);
        colImerominia.setCellFactory(column -> new TableCell<Antikatavoli, LocalDate>() {
            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (empty || date == null) {
                    setText("-");
                } else {
                    setText(DATE_FORMATTER.format(date));
                }
            }
        });

        TableColumn<Antikatavoli, String> colParastatikoACS = new TableColumn<>("Παραστατικό ACS");
        colParastatikoACS.setCellValueFactory(new PropertyValueFactory<>("parastatikoACS"));
        colParastatikoACS.setPrefWidth(150);

        TableColumn<Antikatavoli, String> colParastatikoMyPMS = new TableColumn<>("Παραστατικό MyPMS");
        colParastatikoMyPMS.setCellValueFactory(new PropertyValueFactory<>("parastatikoMyPMS"));
        colParastatikoMyPMS.setPrefWidth(150);

        TableColumn<Antikatavoli, String> colSxolia = new TableColumn<>("Σχόλια");
        colSxolia.setCellValueFactory(new PropertyValueFactory<>("sxolia"));
        colSxolia.setPrefWidth(200);

        table.getColumns().addAll(
            colId, colIdApostolis, colApodothike, colImerominia,
            colParastatikoACS, colParastatikoMyPMS, colSxolia
        );

        container.getChildren().add(table);
        VBox.setVgrow(table, Priority.ALWAYS);

        return container;
    }

    private HBox createButtonPanel() {
        HBox buttonPanel = new HBox(15);
        buttonPanel.setAlignment(Pos.CENTER);
        buttonPanel.setPadding(new Insets(20, 0, 0, 0));

        btnNew = new Button("➕ Νέα Αντικαταβολή");
        btnNew.getStyleClass().add("button-success");
        btnNew.getStyleClass().add("button-lg");
        btnNew.setOnAction(e -> createNew());

        btnEdit = new Button("✏️ Επεξεργασία");
        btnEdit.getStyleClass().add("button-primary");
        btnEdit.getStyleClass().add("button-lg");
        btnEdit.setOnAction(e -> edit());
        btnEdit.setDisable(true);

        btnMarkPaid = new Button("✓ Σήμανση ως Αποδοθέν");
        btnMarkPaid.getStyleClass().add("button-warning");
        btnMarkPaid.getStyleClass().add("button-lg");
        btnMarkPaid.setOnAction(e -> markAsPaid());
        btnMarkPaid.setDisable(true);

        btnDelete = new Button("🗑️ Διαγραφή");
        btnDelete.getStyleClass().add("button-danger");
        btnDelete.getStyleClass().add("button-lg");
        btnDelete.setOnAction(e -> delete());
        btnDelete.setDisable(true);

        btnRefresh = new Button("🔄 Ανανέωση");
        btnRefresh.getStyleClass().add("button-secondary");
        btnRefresh.getStyleClass().add("button-lg");
        btnRefresh.setOnAction(e -> loadData());

        // Enable/disable buttons based on selection
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean hasSelection = newSelection != null;
            btnEdit.setDisable(!hasSelection);
            btnDelete.setDisable(!hasSelection);
            btnMarkPaid.setDisable(!hasSelection || (newSelection != null && newSelection.getApodothike()));
        });

        buttonPanel.getChildren().addAll(
            btnNew, btnEdit, btnMarkPaid, btnDelete, btnRefresh
        );

        return buttonPanel;
    }

    private void loadData() {
        try {
            antikatavoliList.clear();
            antikatavoliList.addAll(antikatavoliDAO.findAll());
        } catch (Exception e) {
            showError("Σφάλμα φόρτωσης δεδομένων", e.getMessage());
            e.printStackTrace();
        }
    }

    private void createNew() {
        showInfo("Νέα Αντικαταβολή", "Η φόρμα δημιουργίας αντικαταβολής θα προστεθεί σύντομα!");
    }

    private void edit() {
        Antikatavoli selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showInfo("Επεξεργασία", "Επεξεργασία αντικαταβολής ID: " + selected.getId());
        }
    }

    private void markAsPaid() {
        Antikatavoli selected = table.getSelectionModel().getSelectedItem();
        if (selected != null && !selected.getApodothike()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Σήμανση ως Αποδοθέν");
            alert.setHeaderText("Αντικαταβολή ID: " + selected.getId());
            alert.setContentText("Θέλετε να σημάνετε αυτή την αντικαταβολή ως αποδοθείσα;");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    selected.setApodothike(true);
                    selected.setImerominiaApodosis(LocalDate.now());
                    if (antikatavoliDAO.update(selected)) {
                        showSuccess("Επιτυχία", "Η αντικαταβολή σημάνθηκε ως αποδοθείσα");
                        loadData();
                    } else {
                        showError("Σφάλμα", "Αποτυχία ενημέρωσης");
                    }
                }
            });
        }
    }

    private void delete() {
        Antikatavoli selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Επιβεβαίωση Διαγραφής");
            alert.setHeaderText("Διαγραφή αντικαταβολής ID: " + selected.getId());
            alert.setContentText("Είστε σίγουροι ότι θέλετε να διαγράψετε αυτήν την αντικαταβολή;");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    if (antikatavoliDAO.delete(selected.getId())) {
                        showSuccess("Επιτυχία", "Η αντικαταβολή διαγράφηκε επιτυχώς");
                        loadData();
                    } else {
                        showError("Σφάλμα", "Αποτυχία διαγραφής");
                    }
                }
            });
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
