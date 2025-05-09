package com.example.ch_project_fx;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class Scene_userSelect {

    public Scene getUserSelectScene() {
        // 상단 로고
        HBox topBox = new HBox();
        topBox.setPadding(new Insets(30, 0, 20, 0));
        topBox.setAlignment(Pos.CENTER);
        ImageView logoView = new ImageView(new Image(getClass().getResource("/img/bookMarket/logo.png").toExternalForm()));
        logoView.setFitHeight(120);
        logoView.setPreserveRatio(true);
        logoView.setOnMousePressed(e -> {
            Scene_Login login = new Scene_Login();
            login.Login();
            CH_Application.getInstance().stage.setScene(CH_Application.getInstance().currentScene);

        });
        topBox.getChildren().add(logoView);

        // 유저 인사말
        VBox welcomeBox = new VBox(10);
        welcomeBox.setAlignment(Pos.CENTER);
        welcomeBox.setStyle("""
                    -fx-background-color: linear-gradient(to bottom, #ffffff, #e0f0ff);
                    -fx-background-radius: 20;
                    -fx-border-radius: 20;
                    -fx-border-color: #a0c4ff;
                    -fx-border-width: 1.5;
                    -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 4);
                    -fx-padding: 20;
                """);
        Label welcomeLabel = new Label(CH_Application.getInstance().currentUser.getName() + "님 반가워요");

        welcomeLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 22));
        welcomeLabel.setTextFill(Color.web("#1a73e8"));

        Label greetingLabel = new Label("오늘도 독서하기 좋은 날이네요!");
        greetingLabel.setFont(Font.font("Helvetica", FontWeight.NORMAL, 16));
        greetingLabel.setTextFill(Color.web("#555555"));
        welcomeBox.getChildren().addAll(welcomeLabel, greetingLabel);


        // 메뉴 카드 (이미지 + 라벨)
        GridPane gridPane = new GridPane();
        gridPane.setHgap(40);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(30));
        gridPane.setAlignment(Pos.CENTER);

        VBox miniGameBox = createMenuCard("/img/bookMarket/minigame.png", "미니게임");
        miniGameBox.setOnMousePressed(e -> {
            Scene_Minigame SM = new Scene_Minigame();
            SM.SelectGame();
        });
        VBox libraryBox = createMenuCard("/img/bookMarket/library.png", "책 대여");
        libraryBox.setOnMousePressed(e -> {
            if (isUserCurrentlyBorrowing(CH_Application.getInstance().currentUser.getId())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setGraphic(null);
                alert.setContentText("현재 대여중인 책이 있어 도서관에 입장할 수 없습니다.");
                alert.showAndWait();
            }else {
                Scene_Library SL = new Scene_Library();
                SL.openLibrary();
            }
        });

        VBox marketBox = createMenuCard("/img/bookMarket/bookmarket.png", "온라인 서점");
        marketBox.setOnMousePressed(e -> {
            Scene_bookMarket BM = new Scene_bookMarket();
            BM.openMarket();
        });
        gridPane.add(miniGameBox, 0, 0);
        gridPane.add(libraryBox, 1, 0);
        gridPane.add(marketBox, 2, 0);

        GridPane Bottom = new GridPane();
        Bottom.setHgap(40);
        Bottom.setVgap(20);
        Bottom.setPadding(new Insets(20));
        Bottom.setAlignment(Pos.CENTER);

        Image image = new Image(getClass().getResource("/img/bookMarket/BookBot.png").toExternalForm());
        ImageView bookBot = new ImageView(image);
        bookBot.setPreserveRatio(true);
        bookBot.setFitHeight(200);
        bookBot.setOnMousePressed(e -> {
            Scene_ChatBot SC = new Scene_ChatBot();
            SC.runChatBot();
        });
        Image image1 = new Image(getClass().getResource("/img/advertisement/a1.jpg").toExternalForm());
        Image image2 = new Image(getClass().getResource("/img/advertisement/a2.jpg").toExternalForm());
        Image image3 = new Image(getClass().getResource("/img/advertisement/a3.jpg").toExternalForm());
        Image image4 = new Image(getClass().getResource("/img/advertisement/a4.jpg").toExternalForm());
        Image image5 = new Image(getClass().getResource("/img/advertisement/a5.jpg").toExternalForm());
        Image image6 = new Image(getClass().getResource("/img/advertisement/a6.jpg").toExternalForm());


        ImageView imageView = new ImageView(image1);
        imageView.setFitHeight(240);
        imageView.setPreserveRatio(true);
        StackPane imageContainer = new StackPane(imageView);
        imageContainer.setStyle("-fx-border-color: black; -fx-border-width: 2;");
        List<Image> images = Arrays.asList(image1, image2, image3, image4, image5, image6);
        IntegerProperty currentIndex = new SimpleIntegerProperty(0);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
            int nextIndex = (currentIndex.get() + 1) % images.size();
            imageView.setImage(images.get(nextIndex));
            currentIndex.set(nextIndex);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();


        Bottom.add(bookBot, 1, 0);
        Bottom.add(imageContainer, 0, 0);
        Bottom.add(createUserInfoBox(), 2, 0);
        Bottom.setPadding(new Insets(0, 0, 30, 0)); // Bottom 내부 요소들과의 간격 확보


        // 전체 레이아웃
        VBox root = new VBox(20);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(30));
        root.getChildren().addAll(topBox, welcomeBox, gridPane, Bottom);

        root.setStyle("-fx-background-color: #ffffff;");
        return new Scene(root, 800, 600); // 적절한 크기 지정
    }

    private VBox createMenuCard(String imagePath, String titleText) {
        ImageView imageView = new ImageView(new Image(getClass().getResource(imagePath).toExternalForm()));
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);

        Label title = new Label(titleText);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        VBox box = new VBox(10, imageView, title);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-background-color: #f5f5f5; -fx-border-color: #cccccc; -fx-border-radius: 10; -fx-background-radius: 10;");
        box.setPadding(new Insets(15));
        box.setOnMouseEntered(e -> box.setStyle("-fx-background-color: #e0f7fa; -fx-border-color: #00796b; -fx-border-radius: 10; -fx-background-radius: 10;"));
        box.setOnMouseExited(e -> box.setStyle("-fx-background-color: #f5f5f5; -fx-border-color: #cccccc; -fx-border-radius: 10; -fx-background-radius: 10;"));


        return box;
    }

    private VBox createUserInfoBox() {
        Label nameLabel = new Label("유저이름: " + CH_Application.getInstance().currentUser.getName());
        Label pointLabel = new Label("잔여 포인트: " + CH_Application.getInstance().currentUser.getPoint());
        Label idLabel = new Label("아이디: " + CH_Application.getInstance().currentUser.getId());
        Label gradeLabel = new Label("회원 등급: " + CH_Application.getInstance().currentUser.getGrade());

        nameLabel.setFont(Font.font(15));
        pointLabel.setFont(Font.font(15));
        idLabel.setFont(Font.font(15));
        gradeLabel.setFont(Font.font(15));

        Button logoutBtn = new Button("로그아웃");
        Button infoBtn = new Button("회원정보 보기");
        logoutBtn.setStyle("-fx-background-color: #e0f0ff; -fx-text-fill: #003366;");
        infoBtn.setStyle("-fx-background-color: #e0f0ff; -fx-text-fill: #003366;");

        logoutBtn.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setGraphic(null);
            alert.setContentText(CH_Application.getInstance().currentUser.getName() + "님 로그아웃 되었습니다. 다음에 봐요~!");
            alert.showAndWait();
            Scene_Login login = new Scene_Login();
            login.Login();
            CH_Application.getInstance().stage.setScene(CH_Application.getInstance().currentScene);
        });

        infoBtn.setOnAction(e -> {
            Scene_userInfo info = new Scene_userInfo();
            CH_Application.getInstance().stage.setScene(info.getUserInfoScene(CH_Application.getInstance().getCurrentUser()));
        });

        HBox buttonBox = new HBox(10, infoBtn, logoutBtn);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        VBox userInfoBox = new VBox(10);
        userInfoBox.getChildren().addAll(nameLabel, pointLabel, idLabel, gradeLabel, buttonBox);
        userInfoBox.setPadding(new Insets(20));
        userInfoBox.setStyle("-fx-background-color: #f8fbff; -fx-border-color: #b0cfff; -fx-border-radius: 10; -fx-background-radius: 10;");

        return userInfoBox;
    }
    public boolean isUserCurrentlyBorrowing(String userId) {
        String sql = """
    SELECT COUNT(*) FROM borrows 
    WHERE user_id = ? AND CURDATE() <= due_date
    """;

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // 대여중인 책이 있으면 true
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // 대여중인 책이 없으면 false
    }

}