package com.example.ch_project_fx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Comparator;
import java.util.List;

public class Scene_bookMarket {
    BookDAO dao = new BookDAO();
    List<Book> books;
    List<Book> bestSeller;
    VBox Menu = new VBox(20);
    HBox Bookinfo = new HBox(20);
    Button cartButton = new Button("장바구니("+CH_Application.getInstance().currentUser.getBuyList().size()+")");
    Button toCart = new Button("장바구니에 담기");
    void openMarket() {
        this.books = dao.getAllBooksFromDB();
        List<Book> bestSeller = books.stream().sorted(Comparator.comparingInt(Book::getAmount).reversed()) // amount 기준 내림차순
                .limit(3)
                .toList();
        this.bestSeller = bestSeller;
        Menu.getStyleClass().add("menu-pane");
        Bookinfo.getStyleClass().add("book-info-pane");
        for (Book b : this.books) {
            ImageView img = new ImageView(b.getImage());
            img.setPreserveRatio(true);
            img.setFitHeight(100);
            VBox info = new VBox(10);
            Label title = new Label(b.getTitle());
            Label price = new Label(b.getPrice() + "원");
            info.getChildren().addAll(title, price);
            HBox element = new HBox(10);
            element.getChildren().addAll(img, info);
            element.setOnMousePressed(u -> {
                updateInfo(b);
            });
            this.Menu.getChildren().add(element);
        }


        // 메인 VBox 설정
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

        this.cartButton.setOnMousePressed(e->{
            if(CH_Application.getInstance().currentUser.getBuyList().isEmpty()){
                Alert alert  =new Alert(Alert.AlertType.WARNING);
                alert.setGraphic(null);
                alert.setContentText("장바구니가 비어있습니다");
                alert.showAndWait();
            }else{
                Scene_Cart SC = new Scene_Cart();
                SC.userCart();
            }
        });
        this.cartButton.setStyle("-fx-background-color: #00796b; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 10 20; -fx-background-radius: 8;");
        buttons.getChildren().addAll(logoutButton, this.cartButton);
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
        // 베스트셀러 책들
        HBox bestBooks = new HBox(10);
        bestBooks.setAlignment(Pos.CENTER);
        bestBooks.setStyle("-fx-padding: 10;");

        for (int i = 0; i < bestSeller.size(); i++) {
            Book b = bestSeller.get(i);

            HBox element = new HBox(10);
            element.setAlignment(Pos.CENTER_LEFT);
            element.setMaxWidth(250);
            element.setStyle("-fx-padding: 10; -fx-border-color: #d3d3d3; -fx-border-radius: 5; -fx-background-color: #f9f9f9;");

            ImageView img = new ImageView(b.image);
            img.setFitHeight(90); // 책 이미지 크기 조정
            img.setFitWidth(60);
            img.setPreserveRatio(true);

            VBox infoTags = new VBox(5);
            infoTags.getChildren().addAll(new Label(b.title), new Label(b.author));
            infoTags.setAlignment(Pos.CENTER);

            // 책을 베스트셀러처럼 강조
            infoTags.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");

            // 1위 책에 "베스트셀러 1위" 라벨 추가
            if (i == 0) {
                Label rankLabel = new Label("베스트셀러 1위!");
                rankLabel.getStyleClass().add("rank-1");

                rankLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: gold; -fx-font-size: 14px;");
                infoTags.getChildren().add(0, rankLabel); // 1위 라벨을 맨 앞에 추가
            }else if(i == 1){
                Label rankLabel = new Label("베스트셀러 2위");
                rankLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: silver; -fx-font-size: 14px;");
                infoTags.getChildren().add(0, rankLabel);
            }else{
                Label rankLabel = new Label("베스트셀러 3위");
                rankLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: brown; -fx-font-size: 14px;");
                infoTags.getChildren().add(0, rankLabel);
            }

            element.getChildren().addAll(img, infoTags);
            element.setOnMousePressed(e->{
                updateInfo(b);
            });
            bestBooks.getChildren().add(element);
        }
        HBox middle = new HBox(20);
        middle.setPadding(new Insets(20));
        ChoiceBox<String> category = new ChoiceBox<>();
        category.getItems().addAll("전부", "제목", "저자", "출판사", "카테고리");
        category.setValue("전부");
        TextField inputSearch = new TextField();
        inputSearch.setOnAction(e -> {
            switch (category.getValue()) {
                case "전부":
                    clearMenu();
                    for (Book b : this.books) {
                        ImageView img = new ImageView(b.getImage());
                        img.setPreserveRatio(true);
                        img.setFitHeight(100);
                        VBox info = new VBox(10);
                        Label title = new Label(b.getTitle());
                        Label price = new Label(b.getPrice() + "원");
                        info.getChildren().addAll(title, price);
                        HBox element = new HBox(10);
                        element.getChildren().addAll(img, info);
                        element.setOnMousePressed(u -> {
                            updateInfo(b);
                        });
                        this.Menu.getChildren().add(element);
                    }
                    break;
                case "제목":
                    clearMenu();
                    for (Book b : this.books) {
                        if (b.getTitle().contains(inputSearch.getText())) {
                            ImageView img = new ImageView(b.getImage());
                            img.setPreserveRatio(true);
                            img.setFitHeight(100);
                            VBox info = new VBox(10);
                            Label title = new Label(b.getTitle());
                            Label price = new Label(b.getPrice() + "원");
                            info.getChildren().addAll(title, price);
                            HBox element = new HBox(10);
                            element.getChildren().addAll(img, info);
                            element.setOnMousePressed(u -> {
                                updateInfo(b);
                            });
                            this.Menu.getChildren().add(element);
                        }
                    }
                    break;
                case "저자":
                    clearMenu();
                    for (Book b : this.books) {
                        if (b.getAuthor().contains(inputSearch.getText())) {
                            ImageView img = new ImageView(b.getImage());
                            img.setPreserveRatio(true);
                            img.setFitHeight(100);
                            VBox info = new VBox(10);
                            Label title = new Label(b.getTitle());
                            Label price = new Label(b.getPrice() + "원");
                            info.getChildren().addAll(title, price);
                            HBox element = new HBox(10);
                            element.getChildren().addAll(img, info);
                            element.setOnMousePressed(u -> {
                                updateInfo(b);
                            });
                            this.Menu.getChildren().add(element);
                        }
                    }
                    break;
                case "출판사":
                    clearMenu();
                    for(Book b : this.books){
                        if(b.getPublisher().contains(inputSearch.getText())) {
                            ImageView img = new ImageView(b.getImage());
                            img.setPreserveRatio(true);
                            img.setFitHeight(100);
                            VBox info = new VBox(10);
                            Label title = new Label(b.getTitle());
                            Label price = new Label(b.getPrice() + "원");
                            info.getChildren().addAll(title, price);
                            HBox element = new HBox(10);
                            element.getChildren().addAll(img, info);
                            element.setOnMousePressed(u -> {
                                updateInfo(b);
                            });
                            this.Menu.getChildren().add(element);
                        } }
                    break;
                case "카테고리":
                    clearMenu();
                    for(Book b : this.books){
                        if(b.getCategory().contains(inputSearch.getText())) {
                            ImageView img = new ImageView(b.getImage());
                            img.setPreserveRatio(true);
                            img.setFitHeight(100);
                            VBox info = new VBox(10);
                            Label title = new Label(b.getTitle());
                            Label price = new Label(b.getPrice() + "원");
                            info.getChildren().addAll(title, price);
                            HBox element = new HBox(10);
                            element.getChildren().addAll(img, info);
                            element.setOnMousePressed(u -> {
                                updateInfo(b);
                            });
                            this.Menu.getChildren().add(element);
                        } }
                    break;

            }
        });
        middle.getChildren().addAll(category, inputSearch);


        HBox bottom = new HBox(20);
        bottom.setAlignment(Pos.CENTER);
        this.Menu.setStyle("-fx-boarder-color: black; -fx-border-radius: 5;");
        this.Menu.setMinWidth(300);
        this.Menu.setMinHeight(300);
        this.Bookinfo.setStyle("-fx-boarder-color: black; -fx-border-radius: 5;");
        this.Bookinfo.setMinHeight(300);
        this.Bookinfo.setMinWidth(300);
        ScrollPane menuScroll = new ScrollPane(this.Menu);
        Node content = menuScroll.getContent();
        content.setOnMousePressed(e -> {
            e.consume();  // 이벤트 차단
        });
        menuScroll.setFitToWidth(true);
        menuScroll.setPrefViewportHeight(450);
        menuScroll.setPrefViewportWidth(400);
        menuScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        bottom.getChildren().addAll(menuScroll, this.Bookinfo);


        // 전체 레이아웃에 로고, 유저 정보, 베스트셀러 책 추가
        HBox ButtonBox = new HBox(20);
        ButtonBox.setAlignment(Pos.CENTER);


        userInfoBox.getStyleClass().add("user-info-box");
        logoutButton.getStyleClass().add("button-primary");
        cartButton.getStyleClass().add("button-primary");
        Menu.getStyleClass().add("menu-pane");
        Bookinfo.getStyleClass().add("book-info-pane");



        main.getChildren().addAll(header, bestBooks, middle, bottom, ButtonBox);
        main.setStyle("-fx-background-color: #f4f9f9;");
        // Scene 설정
        Scene bookMarketScene = new Scene(main, 800, 600);
        bookMarketScene.getStylesheets().add(getClass().getResource("/css/bookmarket.css").toExternalForm());

        CH_Application.getInstance().stage.setScene(bookMarketScene);
    }

    void clearMenu() {
        this.Menu.getChildren().clear();
    }

    void updateInfo(Book book) {
        this.Bookinfo.getChildren().clear();
        VBox bookInfo = new VBox(10);

        if(CH_Application.getInstance().currentUser.getBuyList().stream().anyMatch(e->e.getTitle().equals(book.getTitle()))){
            toCart.setText("장바구니 추가 완료");
            toCart.setDisable(true);
        }else{
            toCart.setText("장바구니에 담기");
            toCart.setDisable(false);
        }
        toCart.setAlignment(Pos.CENTER);
        toCart.getStyleClass().add("button-primary");
        toCart.setOnMouseClicked(e->{
            CH_Application.getInstance().currentUser.getBuyList().add(book.CopyBookForCart(book));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setGraphic(null);
            alert.setContentText("장바구니에 추가되었습니다!");
            this.cartButton.setText("장바구니("+CH_Application.getInstance().currentUser.getBuyList().size()+")");
            alert.showAndWait();
            updateInfo(book);
        });
        ImageView img = new ImageView(book.getImage());
        img.setPreserveRatio(true);
        img.setFitHeight(250);
        VBox infoTag = new VBox();
        Label bookName = new Label(book.getTitle());
        Label bookAuthor = new Label(book.getAuthor());
        Label bookPublisher = new Label(book.getPublisher());
        Label bookPrice = new Label(book.getPrice() + "원");
        Text info = new Text(book.getDescription());
        info.setWrappingWidth(200);
        bookInfo.getChildren().addAll(toCart,img, infoTag, bookName, bookAuthor, bookPublisher, bookPrice, info);
        this.Bookinfo.getChildren().add(bookInfo);

    }

    // 공통 버튼 스타일
    private Button createStyledButton(String text) {
        Button btn = new Button(text);
        btn.setStyle("-fx-background-color: #00796b; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 10 20; -fx-background-radius: 8;");
        return btn;
    }
}