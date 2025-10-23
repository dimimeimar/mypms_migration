package com.mypms;

import com.mypms.config.DatabaseConfig;
import com.mypms.customers.ui.CustomerManagementView;
import com.mypms.shipments.ui.ShipmentManagementView;
import com.mypms.antikatavoles.ui.AntikatavoliManagementView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Κεντρικό Dashboard της εφαρμογής MyPMS - JavaFX Edition
 * Migration από Swing σε JavaFX με modern Material Design
 */
public class MainApplication extends Application {

    private Stage primaryStage;
    private Stage customerStage;
    private Stage shipmentStage;
    private Stage antikatavoliStage;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;

        // Test database connection
        try {
            DatabaseConfig.getInstance().getConnection();
        } catch (Exception e) {
            showErrorAlert("Σφάλμα σύνδεσης με τη βάση δεδομένων:\n" +
                          e.getMessage() + "\n\nΠαρακαλώ ελέγξτε τις ρυθμίσεις σύνδεσης.");
            Platform.exit();
            return;
        }

        // Create main scene
        Scene scene = createDashboardScene();

        // Apply CSS
        scene.getStylesheets().add(
            getClass().getResource("/css/main.css").toExternalForm()
        );

        // Setup stage
        primaryStage.setTitle("MyPMS - Κεντρικό Σύστημα Διαχείρισης");
        primaryStage.setScene(scene);
        primaryStage.setWidth(900);
        primaryStage.setHeight(650);
        primaryStage.centerOnScreen();

        // Handle close request
        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            exitApplication();
        });

        primaryStage.show();
    }

    private Scene createDashboardScene() {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("root");

        // Header
        VBox header = createHeader();
        root.setTop(header);

        // Main Dashboard Content
        VBox centerContent = createDashboardContent();
        root.setCenter(centerContent);

        // Footer
        HBox footer = createFooter();
        root.setBottom(footer);

        return new Scene(root);
    }

    private VBox createHeader() {
        VBox header = new VBox();
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(30, 20, 30, 20));
        header.getStyleClass().add("panel-header");

        // Title
        Label titleLabel = new Label("MyPMS - Σύστημα Διαχείρισης");
        titleLabel.getStyleClass().add("label-title");
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: bold;");

        // Subtitle
        Label subtitleLabel = new Label("Κεντρικό Dashboard");
        subtitleLabel.getStyleClass().add("label-header");
        subtitleLabel.setStyle("-fx-text-fill: rgba(255,255,255,0.9); -fx-font-size: 18px;");

        header.getChildren().addAll(titleLabel, subtitleLabel);
        VBox.setMargin(subtitleLabel, new Insets(5, 0, 0, 0));

        return header;
    }

    private VBox createDashboardContent() {
        VBox content = new VBox(40);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(50, 40, 50, 40));
        content.setStyle("-fx-background-color: -fx-light-gray;");

        // Create three dashboard cards in a horizontal layout
        HBox cardsBox = new HBox(30);
        cardsBox.setAlignment(Pos.CENTER);

        // Customer Management Card
        VBox customerCard = createDashboardCard(
            "Διαχείριση Πελατών",
            "Πελάτες, εταιρίες και υπεύθυνοι",
            "\uD83D\uDC65", // 👥 icon
            this::openCustomerManagement
        );

        // Shipment Management Card
        VBox shipmentCard = createDashboardCard(
            "Διαχείριση Αποστολών",
            "Αποστολές και παρακολούθηση",
            "\uD83D\uDCE6", // 📦 icon
            this::openShipmentManagement
        );

        // COD Management Card
        VBox antikatavoliCard = createDashboardCard(
            "Διαχείριση Αντικαταβολών",
            "Αντικαταβολές και αποδόσεις",
            "\uD83D\uDCB0", // 💰 icon
            this::openAntikatavoliManagement
        );

        cardsBox.getChildren().addAll(customerCard, shipmentCard, antikatavoliCard);
        content.getChildren().add(cardsBox);

        return content;
    }

    private VBox createDashboardCard(String title, String description, String icon, Runnable action) {
        VBox card = new VBox(15);
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(250, 200);
        card.setMaxSize(250, 200);
        card.getStyleClass().add("panel");
        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 16px;" +
            "-fx-padding: 30 20 30 20;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 4);" +
            "-fx-cursor: hand;"
        );

        // Icon
        Label iconLabel = new Label(icon);
        iconLabel.setStyle(
            "-fx-font-size: 48px;" +
            "-fx-text-fill: -fx-primary;"
        );

        // Title
        Label titleLabel = new Label(title);
        titleLabel.setWrapText(true);
        titleLabel.setTextAlignment(TextAlignment.CENTER);
        titleLabel.setStyle(
            "-fx-font-size: 18px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: -fx-primary;"
        );

        // Description
        Label descLabel = new Label(description);
        descLabel.setWrapText(true);
        descLabel.setTextAlignment(TextAlignment.CENTER);
        descLabel.setStyle(
            "-fx-font-size: 13px;" +
            "-fx-text-fill: -fx-text-muted;"
        );

        card.getChildren().addAll(iconLabel, titleLabel, descLabel);

        // Hover effects
        card.setOnMouseEntered(e -> {
            card.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 16px;" +
                "-fx-padding: 30 20 30 20;" +
                "-fx-effect: dropshadow(gaussian, rgba(25,42,86,0.3), 20, 0, 0, 8);" +
                "-fx-cursor: hand;" +
                "-fx-translate-y: -5;"
            );
        });

        card.setOnMouseExited(e -> {
            card.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 16px;" +
                "-fx-padding: 30 20 30 20;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 4);" +
                "-fx-cursor: hand;" +
                "-fx-translate-y: 0;"
            );
        });

        card.setOnMouseClicked(e -> action.run());

        return card;
    }

    private HBox createFooter() {
        HBox footer = new HBox();
        footer.setPadding(new Insets(10, 20, 10, 20));
        footer.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: -fx-medium-gray;" +
            "-fx-border-width: 1 0 0 0;"
        );

        // Status label
        Label statusLabel = new Label("● Σύνδεση ενεργή");
        statusLabel.setStyle(
            "-fx-text-fill: -fx-success;" +
            "-fx-font-size: 13px;"
        );

        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Version label
        Label versionLabel = new Label("Έκδοση 2.0 - JavaFX");
        versionLabel.setStyle(
            "-fx-text-fill: -fx-text-muted;" +
            "-fx-font-size: 13px;"
        );

        footer.getChildren().addAll(statusLabel, spacer, versionLabel);

        return footer;
    }

    private void openCustomerManagement() {
        if (customerStage == null || !customerStage.isShowing()) {
            customerStage = new Stage();
            customerStage.setTitle("Διαχείριση Πελατών");
            customerStage.setWidth(1200);
            customerStage.setHeight(700);

            // Create Customer Management View (to be created next)
            try {
                CustomerManagementView view = new CustomerManagementView();
                Scene scene = new Scene(view.getView());
                scene.getStylesheets().add(getClass().getResource("/css/main.css").toExternalForm());
                customerStage.setScene(scene);
                customerStage.show();
            } catch (Exception e) {
                showErrorAlert("Σφάλμα φόρτωσης διαχείρισης πελατών: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            customerStage.toFront();
            customerStage.requestFocus();
        }
    }

    private void openShipmentManagement() {
        if (shipmentStage == null || !shipmentStage.isShowing()) {
            shipmentStage = new Stage();
            shipmentStage.setTitle("Διαχείριση Αποστολών");
            shipmentStage.setWidth(1400);
            shipmentStage.setHeight(800);

            try {
                ShipmentManagementView view = new ShipmentManagementView();
                Scene scene = new Scene(view.getView());
                scene.getStylesheets().add(getClass().getResource("/css/main.css").toExternalForm());
                shipmentStage.setScene(scene);
                shipmentStage.show();
            } catch (Exception e) {
                showErrorAlert("Σφάλμα φόρτωσης διαχείρισης αποστολών: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            shipmentStage.toFront();
            shipmentStage.requestFocus();
        }
    }

    private void openAntikatavoliManagement() {
        if (antikatavoliStage == null || !antikatavoliStage.isShowing()) {
            antikatavoliStage = new Stage();
            antikatavoliStage.setTitle("Διαχείριση Αντικαταβολών");
            antikatavoliStage.setWidth(1400);
            antikatavoliStage.setHeight(800);

            try {
                AntikatavoliManagementView view = new AntikatavoliManagementView();
                Scene scene = new Scene(view.getView());
                scene.getStylesheets().add(getClass().getResource("/css/main.css").toExternalForm());
                antikatavoliStage.setScene(scene);
                antikatavoliStage.show();
            } catch (Exception e) {
                showErrorAlert("Σφάλμα φόρτωσης διαχείρισης αντικαταβολών: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            antikatavoliStage.toFront();
            antikatavoliStage.requestFocus();
        }
    }

    private void exitApplication() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Επιβεβαίωση Εξόδου");
        alert.setHeaderText(null);
        alert.setContentText("Είστε σίγουροι ότι θέλετε να κλείσετε την εφαρμογή;");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    DatabaseConfig.getInstance().closeConnection();
                } catch (Exception e) {
                    System.err.println("Σφάλμα κλεισίματος σύνδεσης: " + e.getMessage());
                }
                Platform.exit();
                System.exit(0);
            }
        });
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Σφάλμα");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
