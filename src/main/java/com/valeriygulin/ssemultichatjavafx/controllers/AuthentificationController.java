package com.valeriygulin.ssemultichatjavafx.controllers;

import com.valeriygulin.ssemultichatjavafx.App;
import com.valeriygulin.ssemultichatjavafx.model.User;
import com.valeriygulin.ssemultichatjavafx.repository.UserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.prefs.Preferences;

public class AuthentificationController {
    @FXML
    public TextField textFieldLogin;
    @FXML
    public TextField textFieldPassword;
    @FXML
    public CheckBox checkBox;
    @FXML
    public PasswordField passwordField;
    @FXML
    public void togglevisiblePassword(ActionEvent event) {
        if (checkBox.isSelected()) {
            textFieldPassword.setText(passwordField.getText());
            textFieldPassword.setVisible(true);
            passwordField.setVisible(false);
            return;
        }
        passwordField.setText(textFieldPassword.getText());
        passwordField.setVisible(true);
        textFieldPassword.setVisible(false);
    }

    @FXML
    public void buttonLogIn(ActionEvent actionEvent) throws IOException {
        Preferences prefs = Preferences.userRoot().node(App.class.getName());
        String login = textFieldLogin.getText();
        if (login.isEmpty()) {
            App.showAlert("Error!", "Enter login!", Alert.AlertType.ERROR);
            return;
        }
        prefs.put("login", login);
        String password = textFieldPassword.getText();
        String password2 = passwordField.getText();
        if (password.isEmpty()) {
            if (password2.isEmpty()) {
                App.showAlert("Error!", "Enter password!", Alert.AlertType.ERROR);
                return;
            } else {
                prefs.put("password", password2);
            }
        } else {
            prefs.put("password", password);
        }
        String login1 = prefs.get("login", "");
        String password1 = prefs.get("password", "");
        User user = new UserRepository().get(login1, password1);
        if (user == null) {
            App.showAlert("Error!", "You are not registered", Alert.AlertType.ERROR);
            return;
        } else {
            prefs.put("id", String.valueOf(user.getId()));
            App.openWindow("main.fxml", "MultiChat 1.0", null);
            App.closeWindow(actionEvent);
        }
    }

    @FXML
    public void buttonRegistration(ActionEvent actionEvent) throws IOException {
        App.openWindowAndWait("registration.fxml", "Registration Form", null);
    }
}
