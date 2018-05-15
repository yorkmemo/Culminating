package io;

import javafx.scene.control.Alert;

public class Output {
    public static void show(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Output Message");
        alert.setHeaderText("Output Message");
        alert.setContentText(message);

        if (Win.stage() != null)
            alert.initOwner(Win.stage());

        alert.showAndWait();
    }

    public static void show(int value) {
        show(""+value);
    }

    public static void show(double value) {
        show(""+value);
    }

    static void error(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Run-Time Error");
        alert.setHeaderText("Run-Time Error");
        alert.setContentText(message);

        if (Win.stage() != null)
             alert.initOwner(Win.stage());

        alert.showAndWait();
    }

}
