package com.example.ch_project_fx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CH_Application extends Application {
    private static CH_Application instance;
    Scene currentScene;
    User currentUser;
    Stage stage;
    public static CH_Application getInstance() {
        return instance;
    }
    @Override
    public void start(Stage stage) throws IOException {
        instance = this;
        this.stage = stage;

        Scene_Login sceneLogin = new Scene_Login();
        sceneLogin.Login();
        stage.setWidth(800);
        stage.setHeight(1000);
        stage.setResizable(false);
        stage.setScene(this.currentScene);
        stage.setTitle("창현씨...");
        stage.show();

    }
    public void setCurrentScene(Scene scene) {
        this.currentScene = scene;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return this.currentUser;
    }
    public static void main(String[] args) {
        launch();
    }
}