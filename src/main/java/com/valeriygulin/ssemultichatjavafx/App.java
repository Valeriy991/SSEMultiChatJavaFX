package com.valeriygulin.ssemultichatjavafx;

import com.valeriygulin.ssemultichatjavafx.controllers.ControllerData;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.prefs.Preferences;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Preferences prefs = Preferences.userRoot().node(App.class.getName());
        if (prefs.get("login", "").isEmpty() && prefs.get("password", "").isEmpty()) {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("authentification.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 280, 320);
            stage.setTitle("Authorization");
            stage.setScene(scene);
            stage.show();
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 520);
            stage.setTitle("MultiChat 1.0");
            stage.setScene(scene);
            stage.show();
        }
    }

    public static void main(String[] args) {
        launch();
    }

    public static void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static <T> Stage getStage(String name, String title, T data) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(name));

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(
                new Scene(loader.load())
        );

        stage.setTitle(title);

        if (data != null) {
            ControllerData<T> controller = loader.getController();
            controller.initData(data);
        }
        return stage;
    }

    public static <T> Stage openWindow(String name, String title, T data) throws IOException {
        Stage stage = getStage(name, title, data);
        stage.show();
        return stage;
    }

    public static <T> Stage openWindowAndWait(String name, String title, T data) throws IOException {
        Stage stage = getStage(name, title, data);
        stage.showAndWait();
        return stage;
    }

    public static void closeWindow(Event event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}