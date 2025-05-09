package com.example.ch_project_fx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Scene_Cart {
    Label totalPrice = new Label();
    VBox cartListBox = new VBox(20);
    Coupon usedCoupon = null;
    int totalPriceInt = 0;

    void userCart() {
        VBox main = new VBox(20);
        main.setAlignment(Pos.TOP_CENTER);
        main.setPadding(new Insets(20));
        main.setStyle("-fx-background-color: #f4f9f9;");

        Image logo = new Image(getClass().getResource("/img/bookMarket/logo.png").toExternalForm());
        ImageView imageViewLogo = new ImageView(logo);
        imageViewLogo.setFitHeight(120);
        imageViewLogo.setPreserveRatio(true);
        imageViewLogo.setOnMousePressed(e -> {
            Scene_userSelect su = new Scene_userSelect();
            CH_Application.getInstance().stage.setScene(su.getUserSelectScene());
        });

        GridPane userInfoBox = new GridPane();
        userInfoBox.setMaxWidth(300);
        userInfoBox.setHgap(10);
        userInfoBox.setVgap(10);
        userInfoBox.setPadding(new Insets(15));
        userInfoBox.setStyle("-fx-background-color: #e0f7fa; -fx-border-color: #00796b; -fx-border-radius: 10; -fx-background-radius: 10;");

        Font infoFont = Font.font("Arial", FontWeight.NORMAL, 14);

        Label userNameLabel = new Label("이름: " + CH_Application.getInstance().currentUser.getName());
        userNameLabel.setFont(infoFont);
        Label userPointsLabel = new Label("포인트: " + CH_Application.getInstance().currentUser.getPoint());
        userPointsLabel.setFont(infoFont);
        Label userGradeLabel = new Label("등급: " + CH_Application.getInstance().currentUser.getGrade());
        userGradeLabel.setFont(infoFont);
        Label userIdLabel = new Label("ID: " + CH_Application.getInstance().currentUser.getId());
        userIdLabel.setFont(infoFont);
        userInfoBox.add(userNameLabel, 0, 0);
        userInfoBox.add(userPointsLabel, 0, 1);
        userInfoBox.add(userGradeLabel, 1, 0);
        userInfoBox.add(userIdLabel, 1, 1);

        HBox buttons = new HBox(20);

        Button logoutButton = createStyledButton("로그아웃");
        logoutButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setGraphic(null);
            alert.setContentText(CH_Application.getInstance().currentUser.getName() + "님 로그아웃 되었습니다. 다음에 봐요~!");
            alert.showAndWait();
            Scene_Login login = new Scene_Login();
            login.Login();
            CH_Application.getInstance().stage.setScene(CH_Application.getInstance().currentScene);
        });

        Button bookStoreButton = createStyledButton("상점으로");
        bookStoreButton.setOnMousePressed(e -> {
            Scene_bookMarket bm = new Scene_bookMarket();
            bm.openMarket();
        });

        buttons.getChildren().addAll(logoutButton, bookStoreButton);
        VBox userInfoVBox = new VBox(10, userInfoBox, buttons);
        userInfoVBox.setAlignment(Pos.CENTER_RIGHT);

        Image bookMarketLogo = new Image(getClass().getResource("/img/bookMarket/bookmarketlogo.png").toExternalForm());
        ImageView imageView = new ImageView(bookMarketLogo);
        imageView.setPreserveRatio(false);
        imageView.setFitHeight(100);
        imageView.setFitWidth(200);

        BorderPane header = new BorderPane();
        header.setPadding(new Insets(10));
        header.setLeft(imageViewLogo);
        header.setCenter(imageView);
        header.setRight(userInfoVBox);

        updateCart();
        ScrollPane cartListPane = new ScrollPane(cartListBox);
        cartListPane.setFitToWidth(true);
        cartListPane.setPrefViewportHeight(500);
        cartListPane.setPrefViewportWidth(600);
        cartListPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        cartListPane.setStyle("-fx-background: #ffffff; -fx-border-color: #cccccc; -fx-border-radius: 8; -fx-background-radius: 8;");

        BorderPane bottom = new BorderPane();

        totalPrice.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        totalPrice.setTextFill(Color.DARKSLATEGRAY);
        totalPrice.setText("총 금액: " + setTotalPrice());

        VBox coupons = new VBox(10);
        coupons.setPadding(new Insets(10));
        coupons.getChildren().add(new Label("보유 쿠폰"));
        if (CH_Application.getInstance().currentUser.getCoupons().isEmpty()) {
            Label empty = new Label("보유 중인 쿠폰이 없습니다");
            empty.setTextFill(Color.GRAY);
            coupons.getChildren().add(empty);
        } else {
            for (Coupon c : CH_Application.getInstance().currentUser.getCoupons()) {
                ImageView coupon = new ImageView(c.getImage());
                coupon.setPreserveRatio(true);
                coupon.setFitHeight(40);
                coupons.getChildren().add(coupon);
            }
        }

        VBox payButtons = new VBox(10);
        Button getPay = createStyledButton("결제하기");
        getPay.setOnMousePressed(e -> {
            if (this.usedCoupon != null) {
                CouponDAO cd = new CouponDAO();
                cd.useCoupon(CH_Application.getInstance().currentUser.getId(), this.usedCoupon.getId());
                PurchaseDAO PD = new PurchaseDAO();
                for (Book b : CH_Application.getInstance().currentUser.getBuyList()) {
                    BookDAO BD = new BookDAO();
                    BD.increaseBookAmount(b.getIsbn(), b.amount);
                    PD.recordPurchase(CH_Application.getInstance().currentUser.getId(), b.isbn, b.getAmount());
                }
                UserDAO UD = new UserDAO();
                UD.updateUserAfterPurchase(CH_Application.getInstance().currentUser.getId(), this.totalPriceInt);
                this.usedCoupon = null;
            } else {
                PurchaseDAO PD = new PurchaseDAO();
                for (Book b : CH_Application.getInstance().currentUser.getBuyList()) {
                    BookDAO BD = new BookDAO();
                    BD.increaseBookAmount(b.getIsbn(), b.amount);
                    PD.recordPurchase(CH_Application.getInstance().currentUser.getId(), b.isbn, b.getAmount());
                }
                UserDAO UD = new UserDAO();
                UD.updateUserAfterPurchase(CH_Application.getInstance().currentUser.getId(), getTotalPrice());
            }
            CH_Application.getInstance().currentUser.getBuyList().clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setGraphic(null);
            alert.setContentText("구매가 완료되었습니다");
            alert.showAndWait();
            UserDAO UD = new UserDAO();
            User updatedUser = UD.login(
                    CH_Application.getInstance().getCurrentUser().getId(),
                    CH_Application.getInstance().getCurrentUser().getPw()
            );
            CH_Application.getInstance().setCurrentUser(updatedUser);
            Scene_userSelect userSelect = new Scene_userSelect();
            CH_Application.getInstance().stage.setScene(userSelect.getUserSelectScene());
        });
        Button useCoupon = createStyledButton("쿠폰사용");
        useCoupon.setOnMousePressed(e -> {
            if (CH_Application.getInstance().currentUser.getCoupons().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("쿠폰 없음");
                alert.setHeaderText(null);
                alert.setContentText("보유 중인 쿠폰이 없습니다.");
                alert.showAndWait();
                return;
            }
            ChoiceBox<String> couponChoiceBox = new ChoiceBox<>();
            List<Coupon> coupons1 = CH_Application.getInstance().currentUser.getCoupons();
            for (Coupon c : coupons1) {
                couponChoiceBox.getItems().add(c.getName());
            }
            Button applyButton = new Button("쿠폰 적용");
            applyButton.setOnAction(ev -> {
                String select = couponChoiceBox.getValue();
                for (Coupon c : coupons1) {
                    if (c.getName().equals(select)) {
                        this.usedCoupon = c;
                        int discount = getTotalPrice() * c.getValue() / 100;
                        int finalPrice = getTotalPrice() - discount;
                        this.totalPrice.setText("(쿠폰사용됨)할인적용금액:" + finalPrice);
                        this.totalPriceInt = finalPrice;
                        ((Stage) applyButton.getScene().getWindow()).close(); // 창 닫기
                    }
                }
            });

            VBox layout = new VBox(10, new Label("쿠폰을 선택하세요:"), couponChoiceBox, applyButton);
            layout.setPadding(new Insets(10));

            Scene miniScene = new Scene(layout);
            Stage popupStage = new Stage();
            popupStage.setTitle("쿠폰 선택");
            popupStage.setScene(miniScene);
            popupStage.initModality(Modality.APPLICATION_MODAL); // 모달 창
            popupStage.showAndWait();

        });
        payButtons.getChildren().addAll(useCoupon, getPay);

        bottom.setLeft(totalPrice);
        bottom.setRight(payButtons);
        bottom.setPadding(new Insets(20));

        main.getChildren().addAll(header, cartListPane, bottom);
        Scene cartScene = new Scene(main);
        CH_Application.getInstance().stage.setScene(cartScene);
    }

    void deleteCart(Book b) {
        CH_Application.getInstance().currentUser.getBuyList().remove(b);
        updateCart();
    }

    void updateCart() {
        cartListBox.getChildren().clear();
        for (Book book : CH_Application.getInstance().currentUser.getBuyList()) {
            ImageView img = new ImageView(book.image);
            img.setFitHeight(100);
            img.setPreserveRatio(true);

            VBox infotags = new VBox(5);
            infotags.getChildren().addAll(
                    createBookLabel(book.getTitle(), 16, true),
                    createBookLabel(book.getAuthor(), 14, false),
                    createBookLabel(book.getPublisher(), 14, false),
                    createBookLabel(book.getPrice() + "원", 14, false)
            );

            HBox Buttons = new HBox(10);
            Button btnDelete = createMiniButton("삭제");
            btnDelete.setOnMousePressed(e -> deleteCart(book));

            Button plus = createMiniButton("+");
            plus.setOnMousePressed(e -> {
                book.setAmount(book.getAmount() + 1);
                updateCart();
            });

            Label amount = new Label(String.valueOf(book.getAmount()));
            amount.setFont(Font.font("Arial", FontWeight.BOLD, 14));

            Button minus = createMiniButton("-");
            minus.setOnMousePressed(e -> {
                if (book.getAmount() == 1) {
                    deleteCart(book);
                } else {
                    book.setAmount(book.getAmount() - 1);
                    updateCart();
                }
            });

            Buttons.setAlignment(Pos.CENTER_RIGHT);
            Buttons.getChildren().addAll(btnDelete, plus, amount, minus);

            HBox element = new HBox(20, img, infotags);
            element.setAlignment(Pos.CENTER_LEFT);

            BorderPane pane = new BorderPane();
            pane.setPadding(new Insets(10));
            pane.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-radius: 10; -fx-background-radius: 10;");
            pane.setLeft(element);
            pane.setRight(Buttons);
            cartListBox.getChildren().add(pane);
        }
        totalPrice.setText("총 금액: " + setTotalPrice());
    }

    String setTotalPrice() {
        List<Book> cart = CH_Application.getInstance().currentUser.getBuyList();
        User user = CH_Application.getInstance().getCurrentUser();
        int total = 0;
        String s = "";
        for (Book b : cart) {
            total += b.getAmount() * b.getPrice();
        }
        if (user.getGrade().equals("silver")) {
            total *= 0.95;
            s = "silver 등급 할인 적용됨";
        } else if (user.getGrade().equals("gold")) {
            total *= 0.9;
            s = "gold 등급 할인 적용됨";
        } else if (user.getGrade().equals("vip")) {
            total *= 0.85;
            s = "vip 등급 할인 적용됨";

        }
        return total + "원" + "(" + s + ")";
    }

    // 공통 버튼 스타일
    private Button createStyledButton(String text) {
        Button btn = new Button(text);
        btn.setStyle("-fx-background-color: #00796b; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 10 20; -fx-background-radius: 8;");
        return btn;
    }

    // 미니 버튼 스타일
    private Button createMiniButton(String text) {
        Button btn = new Button(text);
        btn.setStyle("-fx-background-color: #009688; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px; -fx-background-radius: 6;");
        return btn;
    }

    private Label createBookLabel(String text, int size, boolean bold) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", bold ? FontWeight.BOLD : FontWeight.NORMAL, size));
        return label;
    }

    int getTotalPrice() {
        int total = 0;
        for (Book b : CH_Application.getInstance().currentUser.getBuyList()) {
            total += b.getPrice() * b.getAmount();
        }
        return total;
    }
}