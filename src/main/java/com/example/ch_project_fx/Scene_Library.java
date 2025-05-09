package com.example.ch_project_fx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;

public class Scene_Library {
    BookDAO BD = new BookDAO();
    List<Book> books;
    User currentUser = CH_Application.getInstance().getCurrentUser();
    VBox Menu = new VBox(20);
    VBox BorrowList = new VBox(20);
    List<Book> userBorrowList = new ArrayList<>();

    void openLibrary() {
        books = BD.getAllBooksFromDB();


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

        Button bookStoreButton = createStyledButton("뒤로가기");
        bookStoreButton.setOnMousePressed(e -> {
            Scene_userSelect su = new Scene_userSelect();
            CH_Application.getInstance().stage.setScene(su.getUserSelectScene());
        });

        buttons.getChildren().addAll(logoutButton, bookStoreButton);
        VBox userInfoVBox = new VBox(10, userInfoBox, buttons);
        userInfoVBox.setAlignment(Pos.CENTER_RIGHT);

        Image bookMarketLogo = new Image(getClass().getResource("/img/bookMarket/librarylogo.png").toExternalForm());
        ImageView imageView = new ImageView(bookMarketLogo);
        imageView.setPreserveRatio(false);
        imageView.setFitHeight(100);
        imageView.setFitWidth(200);

        BorderPane header = new BorderPane();
        header.setPadding(new Insets(10));
        header.setLeft(imageViewLogo);
        header.setCenter(imageView);
        header.setRight(userInfoVBox);

        main.getChildren().add(header);
//+++++++++++++++++++++++++++++++++++++++++++++++ 위 까지 상단 ++++++++++++++++++++++++++++++++++++++++++++
        HBox categoryButtons = new HBox(20);
        categoryButtons.setAlignment(Pos.CENTER);
        Button Category1 = createStyledButtonCategory("생활지식");
        Category1.setOnMouseClicked(e -> {
            clearMenu();
            for (Book b : this.books) {
                if (b.getCategory().contains("생활") || b.getCategory().contains("교양") || b.getCategory().contains("과학")) {
                    HBox element = new HBox(20);
                    ImageView img = new ImageView(b.getImage());
                    img.setFitHeight(80);
                    img.setPreserveRatio(true);
                    VBox infoTags = new VBox(10);
                    Button btn = createMiniButton("담기");
                    btn.setOnMouseClicked(i -> {
                        addToCart(b);
                    });
                    infoTags.getChildren().add(new Label(b.getTitle()));
                    infoTags.getChildren().add(new Label(b.getPublisher()));
                    infoTags.getChildren().add(new Label(b.getAuthor()));
                    infoTags.getChildren().add(new Label(b.getCategory()));
                    infoTags.getChildren().add(btn);
                    element.getChildren().addAll(img, infoTags);
                    this.Menu.getChildren().add(element);
                }
            }
        });
        Button Category2 = createStyledButtonCategory("요리");
        Category2.setOnMouseClicked(e -> {
            clearMenu();
            for (Book b : this.books) {
                if (b.getCategory().contains("요리")) {
                    HBox element = new HBox(20);
                    ImageView img = new ImageView(b.getImage());
                    img.setFitHeight(80);
                    img.setPreserveRatio(true);
                    VBox infoTags = new VBox(10);
                    Button btn = createMiniButton("담기");
                    btn.setOnMouseClicked(i -> {
                        addToCart(b);
                    });
                    infoTags.getChildren().add(new Label(b.getTitle()));
                    infoTags.getChildren().add(new Label(b.getPublisher()));
                    infoTags.getChildren().add(new Label(b.getAuthor()));
                    infoTags.getChildren().add(new Label(b.getCategory()));
                    infoTags.getChildren().add(btn);
                    element.getChildren().addAll(img, infoTags);
                    this.Menu.getChildren().add(element);
                }
            }
        });
        Button Category3 = createStyledButtonCategory("만화");
        Category3.setOnMouseClicked(e -> {
            clearMenu();
            for (Book b : this.books) {
                if (b.getCategory().contains("만화")) {
                    HBox element = new HBox(20);
                    ImageView img = new ImageView(b.getImage());
                    img.setFitHeight(80);
                    img.setPreserveRatio(true);
                    VBox infoTags = new VBox(10);
                    Button btn = createMiniButton("담기");
                    btn.setOnMouseClicked(i -> {
                        addToCart(b);
                    });
                    infoTags.getChildren().add(new Label(b.getTitle()));
                    infoTags.getChildren().add(new Label(b.getPublisher()));
                    infoTags.getChildren().add(new Label(b.getAuthor()));
                    infoTags.getChildren().add(new Label(b.getCategory()));
                    infoTags.getChildren().add(btn);
                    element.getChildren().addAll(img, infoTags);
                    this.Menu.getChildren().add(element);
                }
            }
        });
        Button Category4 = createStyledButtonCategory("아동");
        Category4.setOnMouseClicked(e -> {
            clearMenu();
            for (Book b : this.books) {
                if (b.getCategory().contains("아동")) {
                    HBox element = new HBox(20);
                    ImageView img = new ImageView(b.getImage());
                    img.setFitHeight(80);
                    img.setPreserveRatio(true);
                    VBox infoTags = new VBox(10);
                    Button btn = createMiniButton("담기");
                    btn.setOnMouseClicked(i -> {
                        addToCart(b);
                    });
                    infoTags.getChildren().add(new Label(b.getTitle()));
                    infoTags.getChildren().add(new Label(b.getPublisher()));
                    infoTags.getChildren().add(new Label(b.getAuthor()));
                    infoTags.getChildren().add(new Label(b.getCategory()));
                    infoTags.getChildren().add(btn);
                    element.getChildren().addAll(img, infoTags);
                    this.Menu.getChildren().add(element);
                }
            }
        });
        Button Category5 = createStyledButtonCategory("프로그래밍");
        Category5.setOnMouseClicked(e -> {
            clearMenu();
            for (Book b : this.books) {
                if (b.getCategory().contains("프로그래밍") || b.getCategory().contains("웹디자인")) {
                    HBox element = new HBox(20);
                    ImageView img = new ImageView(b.getImage());
                    img.setFitHeight(80);
                    img.setPreserveRatio(true);
                    VBox infoTags = new VBox(10);
                    Button btn = createMiniButton("담기");
                    btn.setOnMouseClicked(i -> {
                        addToCart(b);
                    });
                    infoTags.getChildren().add(new Label(b.getTitle()));
                    infoTags.getChildren().add(new Label(b.getPublisher()));
                    infoTags.getChildren().add(new Label(b.getAuthor()));
                    infoTags.getChildren().add(new Label(b.getCategory()));
                    infoTags.getChildren().add(btn);
                    element.getChildren().addAll(img, infoTags);
                    this.Menu.getChildren().add(element);
                }
            }
        });

        HBox searchBox = new HBox(20);
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("전부", "제목", "저자", "출판사", "카테고리");
        choiceBox.setValue("전부");
        TextField inputSearch = new TextField();
        inputSearch.setOnAction(e -> {
            switch (choiceBox.getValue()) {
                case "전부":
                    clearMenu();
                    for (Book b : this.books) {
                        HBox element = new HBox(20);
                        ImageView img = new ImageView(b.getImage());
                        img.setFitHeight(80);
                        img.setPreserveRatio(true);
                        VBox infoTags = new VBox(10);
                        Button btn = createMiniButton("담기");
                        btn.setOnMouseClicked(i -> {
                            addToCart(b);
                        });
                        infoTags.getChildren().add(new Label(b.getTitle()));
                        infoTags.getChildren().add(new Label(b.getPublisher()));
                        infoTags.getChildren().add(new Label(b.getAuthor()));
                        infoTags.getChildren().add(new Label(b.getCategory()));
                        infoTags.getChildren().add(btn);
                        element.getChildren().addAll(img, infoTags);
                        this.Menu.getChildren().add(element);
                    }
                    break;
                case "제목":
                    clearMenu();
                    for (Book b : this.books) {
                        if (b.getTitle().contains(inputSearch.getText())) {
                            HBox element = new HBox(20);
                            ImageView img = new ImageView(b.getImage());
                            img.setFitHeight(80);
                            img.setPreserveRatio(true);
                            VBox infoTags = new VBox(10);
                            Button btn = createMiniButton("담기");
                            btn.setOnMouseClicked(i -> {
                                addToCart(b);
                            });
                            infoTags.getChildren().add(new Label(b.getTitle()));
                            infoTags.getChildren().add(new Label(b.getPublisher()));
                            infoTags.getChildren().add(new Label(b.getAuthor()));
                            infoTags.getChildren().add(new Label(b.getCategory()));
                            infoTags.getChildren().add(btn);
                            element.getChildren().addAll(img, infoTags);
                            this.Menu.getChildren().add(element);
                        }
                    }
                    break;
                case "저자":
                    clearMenu();
                    for (Book b : this.books) {
                        if (b.getAuthor().contains(inputSearch.getText())) {
                            HBox element = new HBox(20);
                            ImageView img = new ImageView(b.getImage());
                            img.setFitHeight(80);
                            img.setPreserveRatio(true);
                            VBox infoTags = new VBox(10);
                            Button btn = createMiniButton("담기");
                            btn.setOnMouseClicked(i -> {
                                addToCart(b);
                            });
                            infoTags.getChildren().add(new Label(b.getTitle()));
                            infoTags.getChildren().add(new Label(b.getPublisher()));
                            infoTags.getChildren().add(new Label(b.getAuthor()));
                            infoTags.getChildren().add(new Label(b.getCategory()));
                            infoTags.getChildren().add(btn);
                            element.getChildren().addAll(img, infoTags);
                            this.Menu.getChildren().add(element);
                        }
                    }
                    break;
                case "출판사":
                    clearMenu();
                    for (Book b : this.books) {
                        if (b.getPublisher().contains(inputSearch.getText())) {
                            HBox element = new HBox(20);
                            ImageView img = new ImageView(b.getImage());
                            img.setFitHeight(80);
                            img.setPreserveRatio(true);
                            VBox infoTags = new VBox(10);
                            Button btn = createMiniButton("담기");
                            btn.setOnMouseClicked(i -> {
                                addToCart(b);
                            });
                            infoTags.getChildren().add(new Label(b.getTitle()));
                            infoTags.getChildren().add(new Label(b.getPublisher()));
                            infoTags.getChildren().add(new Label(b.getAuthor()));
                            infoTags.getChildren().add(new Label(b.getCategory()));
                            infoTags.getChildren().add(btn);
                            element.getChildren().addAll(img, infoTags);
                            this.Menu.getChildren().add(element);
                        }
                    }
                    break;
                case "카테고리":
                    clearMenu();
                    for (Book b : this.books) {
                        if (b.getCategory().contains(inputSearch.getText())) {
                            HBox element = new HBox(20);
                            ImageView img = new ImageView(b.getImage());
                            img.setFitHeight(80);
                            img.setPreserveRatio(true);
                            VBox infoTags = new VBox(10);
                            Button btn = createMiniButton("담기");
                            btn.setOnMouseClicked(i -> {
                                addToCart(b);
                            });
                            infoTags.getChildren().add(new Label(b.getTitle()));
                            infoTags.getChildren().add(new Label(b.getPublisher()));
                            infoTags.getChildren().add(new Label(b.getAuthor()));
                            infoTags.getChildren().add(new Label(b.getCategory()));
                            infoTags.getChildren().add(btn);
                            element.getChildren().addAll(img, infoTags);
                            this.Menu.getChildren().add(element);
                        }
                    }
                    break;

            }
        });
        categoryButtons.getChildren().addAll(Category1, Category2, Category3, Category4, Category5);
        searchBox.getChildren().addAll(choiceBox, inputSearch);
        //++++++++++++++++++++++++++++++++  위는 검색기능 박스 +++++++++++++++++++++++++++++++++++++++++++++
        HBox middle = new HBox(20);
        middle.setAlignment(Pos.CENTER);


        for (Book b : this.books) {
            HBox element = new HBox(20);
            ImageView img = new ImageView(b.getImage());
            img.setFitHeight(80);
            img.setPreserveRatio(true);
            VBox infoTags = new VBox(10);
            Button btn = createMiniButton("담기");
            btn.setOnMouseClicked(e -> {
                addToCart(b);
            });
            infoTags.getChildren().add(new Label(b.getTitle()));
            infoTags.getChildren().add(new Label(b.getPublisher()));
            infoTags.getChildren().add(new Label(b.getAuthor()));
            infoTags.getChildren().add(new Label(b.getCategory()));
            infoTags.getChildren().add(btn);
            element.getChildren().addAll(img, infoTags);
            this.Menu.getChildren().add(element);
        }
        this.Menu.setStyle("-fx-boarder-color: black; -fx-border-radius: 5;");
        ScrollPane menuListScroll = new ScrollPane(this.Menu);
        Node content = menuListScroll.getContent();
        content.setOnMousePressed(e -> {
            e.consume();
        });
        menuListScroll.setPrefViewportHeight(600);
        menuListScroll.setPrefViewportWidth(450);
        menuListScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        VBox cartAndButtons = new VBox(10);
        Label info = new Label("BorrowList");

        VBox ButtonBox = new VBox(20);
        Button submit = createStyledButton("대여신청");
        submit.setOnMouseClicked(e->{

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setGraphic(null);
            alert.setContentText("대여신청이 완료되었습니다!");
            alert.showAndWait();
            for (Book book : userBorrowList) {
                BD.insertBorrow(currentUser.getId(), book.getIsbn());
            }
            UserDAO ud = new UserDAO();
            CH_Application.getInstance().setCurrentUser(ud.login(currentUser.getId(), currentUser.getPw()));
            Scene_userSelect SU = new Scene_userSelect();
            CH_Application.getInstance().stage.setScene(SU.getUserSelectScene());
        });
        ButtonBox.getChildren().add(submit);
        this.BorrowList.setStyle("-fx-boarder-color: black; -fx-border-radius: 5;");
        this.BorrowList.setMinHeight(300);
        this.BorrowList.setMinWidth(300);
        cartAndButtons.getChildren().addAll(info, this.BorrowList, ButtonBox);


        middle.getChildren().addAll(menuListScroll, cartAndButtons);

        //++++++++++++++++++++++++++위까지 메뉴리스트 장바구니 리스트 그리고 버튼 ++++++++++++++++++++++++++++++++

        main.getChildren().addAll(categoryButtons, searchBox, middle);
        Scene SceneLibrary = new Scene(main);
        CH_Application.getInstance().stage.setScene(SceneLibrary);

    }

    private Button createMiniButton(String text) {
        Button btn = new Button(text);
        btn.setStyle("-fx-background-color: #009688; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12px; -fx-background-radius: 6;");
        return btn;
    }

    private Button createStyledButton(String text) {
        Button btn = new Button(text);
        btn.setStyle("-fx-background-color: #00796b; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 10 20; -fx-background-radius: 8;");
        return btn;
    }

    void clearMenu() {
        this.Menu.getChildren().clear();
    }

    void resetBorrowList() {
        this.BorrowList.getChildren().clear();
        for (Book b : this.userBorrowList) {
            Label label = new Label(b.getTitle());
            BorrowList.getChildren().add(label);
            Button btn = createMiniButton("삭제");
            btn.setOnMouseClicked(e -> {
                userBorrowList.remove(b);
                resetBorrowList();
            });
            BorderPane element = new BorderPane();
            element.setLeft(label);
            element.setRight(btn);
            BorrowList.getChildren().add(element);
        }
    }

    void addToCart(Book b) {
        if (userBorrowList.size() >= 3) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setGraphic(null);
            alert.setContentText("대출 가능 도서는 최대 3권까지입니다.");
            alert.showAndWait();
            return;
        }

        boolean alreadyExists = false;
        for (Book c : userBorrowList) {
            if (b.getIsbn().equals(c.getIsbn())) {
                alreadyExists = true;
                break;
            }
        }

        if (alreadyExists) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setGraphic(null);
            alert.setContentText("이미 위시리스트에 담겨있습니다.");
            alert.showAndWait();
        } else {
            userBorrowList.add(b.CopyBookForCart(b));
            resetBorrowList();
        }
    }


    private Button createStyledButtonCategory(String text) {
        Button btn = new Button(text);
        btn.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 10px; -fx-padding: 10 20; -fx-background-radius: 8;");
        return btn;
    }
}
