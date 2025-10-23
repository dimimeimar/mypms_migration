package com.mypms.antikatavoles.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * Διαχείριση Αντικαταβολών - JavaFX View
 * Placeholder - θα μετατραπεί σε πλήρες UI
 */
public class AntikatavoliManagementView {

    private BorderPane view;

    public AntikatavoliManagementView() {
        createView();
    }

    private void createView() {
        view = new BorderPane();
        view.setPadding(new Insets(20));

        // Header
        VBox header = new VBox(10);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20));
        header.getStyleClass().add("panel-header");

        Label title = new Label("Διαχείριση Αντικαταβολών");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        header.getChildren().add(title);

        // Content
        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(40));
        content.getStyleClass().add("panel");

        Label message = new Label("🚧 Υπό κατασκευή...\n\nΗ διαχείριση αντικαταβολών θα μετατραπεί σύντομα σε JavaFX!");
        message.setStyle("-fx-font-size: 18px; -fx-text-alignment: center;");
        message.setWrapText(true);

        content.getChildren().add(message);

        view.setTop(header);
        view.setCenter(content);
    }

    public BorderPane getView() {
        return view;
    }
}
