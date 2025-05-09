package com.example.ch_project_fx;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

public class Scene_Login {
    BookDAO BD = new BookDAO();
    public  void Login() {

        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);
        String imagePath = "/img/bookMarket/logo.png";
        Image logo = new Image(getClass().getResource(imagePath).toExternalForm());
        ImageView img = new ImageView(logo);
        img.setFitWidth(400);
        img.setPreserveRatio(true);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        // 요소
        Label idLabel = new Label("ID:");
        TextField idField = new TextField();
        Label pwLabel = new Label("Password:");
        PasswordField pwField = new PasswordField();
        Button loginButton = new Button("Login");

        // 배치
        grid.add(idLabel, 0, 0);
        grid.add(idField, 1, 0);
        grid.add(pwLabel, 0, 1);
        grid.add(pwField, 1, 1);
        grid.add(loginButton, 0,2,2, 1);

        // 로그인 버튼 이벤트
        loginButton.setOnAction(e -> {
            String inputId = idField.getText();
            String inputPw = pwField.getText();

            UserDAO dao = new UserDAO();
            User user = dao.login(inputId, inputPw);

            if (user != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setGraphic(null);
                CH_Application.getInstance().setCurrentUser(user);
                alert.setContentText(CH_Application.getInstance().currentUser.getName()+"님 로그인을 환영합니다!");
                alert.showAndWait();
                Scene_userSelect scene1 = new Scene_userSelect();
                CH_Application.getInstance().stage.setScene(scene1.getUserSelectScene());
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setGraphic(null);
                alert.setContentText("아이디나 비밀번호가 일치하지 않습니다");
                alert.showAndWait();

            }
        });
        pwField.setOnAction(e -> {
            String inputId = idField.getText();
            String inputPw = pwField.getText();

            UserDAO dao = new UserDAO();
            User user = dao.login(inputId, inputPw);

            if (user != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setGraphic(null);
                CH_Application.getInstance().setCurrentUser(user);
                alert.setContentText(CH_Application.getInstance().currentUser.getName()+"님 로그인을 환영합니다!");
                alert.showAndWait();
                showOverdueAlert(CH_Application.getInstance().currentUser.getId());
                Scene_userSelect scene1 = new Scene_userSelect();
                CH_Application.getInstance().stage.setScene(scene1.getUserSelectScene());

            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setGraphic(null);
                alert.setContentText("아이디나 비밀번호가 일치하지 않습니다");
                alert.showAndWait();

            }
        });



        vBox.getChildren().addAll(img,grid);

        Scene loginScene = new Scene(vBox);
        loginScene.getStylesheets().add(getClass().getResource("/css/styleLogin.css").toExternalForm());

        CH_Application.getInstance().setCurrentScene(loginScene);
    }
    public void showOverdueAlert(String userId) {
        try {
            int overdueDays = BD.calculateOverdueDays(userId);

            if (overdueDays > 0) {
                // 연체된 날이 있으면 알림 창을 띄운다
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("연체 알림");
                alert.setHeaderText("책 반납 기한이 지났습니다.");
                alert.setContentText("현재 " + overdueDays + "일 연체되었습니다.");
                alert.showAndWait();
            } else {
                // 연체된 날이 없으면 정상적으로 진행
                System.out.println("연체된 책이 없습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // 예외 처리 로직 추가
        }
    }
}