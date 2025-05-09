package com.example.ch_project_fx;

import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;

public class Scene_ChatBot {

    VBox chatBox;
    User user = CH_Application.getInstance().getCurrentUser();
    UserDAO ud = new UserDAO();
    CouponDAO cd = new CouponDAO();
    List<Coupon> coupons = cd.getAllCoupons();
    Random random = new Random();
    BookDAO BD = new BookDAO();
    List<Book> books = BD.getAllBooksFromDB();
    List<Book> bestSeller = books.stream().sorted(Comparator.comparingInt(Book::getAmount).reversed()) // amount 기준 내림차순
            .limit(3)
            .toList();

    void runChatBot() {
        Stage chatStage = new Stage();
        chatStage.setTitle("북봇이에용 0.<");
        chatBox = new VBox(10);
        chatBox.setPadding(new Insets(10));
        chatBox.setMinHeight(550);
        ScrollPane scrollPane = new ScrollPane(chatBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setMinHeight(550);
        TextField inputField = new TextField();
        chatBox.heightProperty().addListener((obs, oldVal, newVal) -> {
            scrollPane.setVvalue(1.0);
        });
        inputField.setOnAction(e -> {
            String userInput = inputField.getText();
            String normalizedInput = userInput.replaceAll("\\s+", "");
            setUserText(user, userInput);
            inputField.clear();

            // 일상
            if (normalizedInput.contains("안녕") || normalizedInput.contains("반가워") || normalizedInput.contains("하이") || normalizedInput.contains("ㅎㅇ") || normalizedInput.contains("hello")) {
                String[] hello = {user.getName() + "님! 저도 반가워요", user.getName() + "님 안녕안녕! > . < ", "안녕하세요" + user.getName() + "님!"};
                String[] niceToMeetYou = {"독서하기 좋은날이네요!", "점심 맛있게 드셨나요", "프로젝트 기간동안 화이팅!"};
                setBotText(hello[random.nextInt(hello.length)] + " " + niceToMeetYou[random.nextInt(niceToMeetYou.length)]);
            }
            if (normalizedInput.contains("내정보") || normalizedInput.contains("유저정보")) {
                setBotTextRapid("이름: " + user.getName() + "\n" + "ID: " + user.getId());
                if (this.user.getGrade().equals("gold")) {
                    setBotText("골드등급 이상이네요! 당신은 독서왕입니다 > < ");
                }
            }
            if (normalizedInput.contains("점메추") || normalizedInput.contains("뭐먹지") || normalizedInput.contains("뭐먹을까") || normalizedInput.contains("배고파") || normalizedInput.contains("메뉴추천")) {
                String[] foodMenu = {"무난하게 제육볶음", "내사랑 쌀국수", "김밥", "한식뷔페", "햄버거", "국밥쟁이 가서 국밥 거하게 딱 때리기 ", "편의점메뉴"};
                setBotText("제 추천메뉴는 " + foodMenu[random.nextInt(foodMenu.length)] + " 입니다! ");

            }
            if (normalizedInput.contains("날씨") || normalizedInput.contains("오늘날씨") || normalizedInput.contains("지금날씨") || normalizedInput.contains("현재날씨")) {
                String[] weather = {"오늘의 날씨는 대체로 맑으며 평균 기온은 19'C 입니다."};
                setBotText(weather[random.nextInt(weather.length)]);
            }

            // 쿠폰
            if (normalizedInput.contains("쿠폰") || normalizedInput.contains("보유쿠폰") || normalizedInput.contains("쿠폰번호") || normalizedInput.contains("할인쿠폰")) {
                List<Coupon> userCoupons = user.getCoupons();
                String couponList = "현재 보유중인 쿠폰 목록입니다.\n";
                for (Coupon c : userCoupons) {
                    couponList += "<" + c.getName() + ">" + "\n";
                }
                setBotTextRapid(couponList);
                setBotTextRapid("보유하신 쿠폰이 있다면 쿠폰번호를 입력해주세요.");
            } else if (userInput.contains("AAA6451") || userInput.contains("BBB7678") || userInput.contains("CCC0455") || userInput.contains("DDD8451")) {
                String couponName = "";
                switch (userInput) {
                    case "AAA6451":
                        cd.giveCouponToUser(user.getId(), 1);
                        couponName = cd.getAllCoupons().get(0).getName();
                        showCouponImage(1);
                        break;
                    case "BBB7678":
                        cd.giveCouponToUser(user.getId(), 2);
                        couponName = cd.getAllCoupons().get(1).getName();
                        showCouponImage(2);
                        break;
                    case "CCC0455":
                        cd.giveCouponToUser(user.getId(), 3);
                        couponName = cd.getAllCoupons().get(2).getName();
                        showCouponImage(3);
                        break;
                    case "DDD8451":
                        cd.giveCouponToUser(user.getId(), 1);
                        couponName = cd.getAllCoupons().get(3).getName();
                        showCouponImage(1);
                        break;
                    default:
                        setBotTextRapid("유효하지 않은 쿠폰 번호입니다. 다시 입력해주세요.");
                        return; // 잘못된 번호면 상태 유지
                }
                CH_Application.getInstance().setCurrentUser(ud.login(user.getId(), user.getPw()));
            }

            // 도서
            if (normalizedInput.contains("베스트") || normalizedInput.contains("잘팔린") || normalizedInput.contains("책추천") || normalizedInput.contains("뭐사지")) {
                setBotText("제가 추천드리는 도서는 " + bestSeller.get(0).getTitle() + " 입니다! 최근들어 가장 많이 팔린 상품이네요!");
                buyBookLink(bestSeller.get(0).getIsbn());
            } else if (normalizedInput.contains("그다음") || normalizedInput.contains("두번째")) {
                setBotText("두번째 추천 도서는 " + bestSeller.get(1).getTitle() + " 입니다! 요즘 소비자분들이 많이 찾으세요!");
                buyBookLink(bestSeller.get(1).getIsbn());
            } else if (normalizedInput.contains("뭐읽지") || normalizedInput.contains("아무거나") || normalizedInput.contains("랜덤") || normalizedInput.contains("흠")) {
                Book finded = books.get(random.nextInt(books.size()));
                setBotText("어떤 책을 읽으실지 고민인가요? 제가 하나 추천해드릴게요! " + finded.getTitle() + " 한번 읽어보세요!");
                buyBookLink(finded.getIsbn());
            }

            if (userInput.contains("책검색") || userInput.contains("책정보") || userInput.contains("책검색하기")) {
                String[] arr = userInput.split(" ");
                String search = arr[1];
                for (Book b : this.books) {
                    if (b.getTitle().contains(search) || b.getCategory().contains(search)) {
                        setBotTextRapid(b.getTitle() + b.getCategory());
                    }
                }
            }

            // 등급
            if (normalizedInput.contains("등급")) {
                setBotText("회원등급은 총 구매금액이 5만원이상 시 silver ,10만원 이상 일 시 gold, 30만원 이상 일 시 vip 입니다! 현재 회원님은 " + user.getGrade() + "(이) 네요!");
            }

            // 게임
            if (normalizedInput.contains("라이어게임")) {
                setBotText("A, K, Q 중 랜덤으로 랭크 하나가 제시됩니다\n" +
                        "                \n" +
                        "승리조건은 자신의 모든 패를 제출. 혹은 상대방의 거짓말을 간파\n" +
                        "                \n" +
                        "같거나 다른 랭크의 카드를 3장까지 동시에 낼수 있습니다.\n" +
                        "제시랭크와 다른 랭크의 카드도 제출 가능하나 상대방이 라이어를 외친다면 패배!\n" +
                        "                \n" +
                        "조커의 경우, 제출할 때 제시 랭크와 같은 랭크로 취급됩니다\n" +
                        "                \n" +
                        "순서는 매 판 랜덤으로 결정됩니다\n" +
                        "                \n" +
                        "라이어 지목은 자신의 다음 사람을 대상으로만 지목할 수 있습니다.");
            }
            if (normalizedInput.contains("블랙잭")) {
                setBotText("유저와 컴퓨터와 서로 각자 패에 보유하고있는 카드로\n" +
                        "점수를 비교합니다. 점수가 더 낮은 사람이 패배하게 됩니다.\n" +
                        "Hit은 카드한장을 먹습니다.\n" +
                        "DoubleDown은 카드 한장을 강제로 먹고 판돈을 한번 더 걸고 승리시 4배로 먹습니다.\n" +
                        "Split은 카드 한장을 다음판으로 넘깁니다. 판돈과 배율은 그대로입니다.\n" +
                        "Stand는 행동을 취하지 않고 차례를 넘깁니다.\n" +
                        "21점이 넘으면 버스트로 무조건 패배!");
            }

        });

        VBox layout = new VBox(10, scrollPane, inputField);
        layout.setPadding(new Insets(10));
        layout.setPrefSize(400, 600);
        Scene scene = new Scene(layout);
        chatStage.setScene(scene);
        chatStage.initModality(Modality.NONE);
        chatStage.show();
        setBotTextRapid("안녕하세요 >.< 무엇을 도와드릴까요");


    }


    void setUserText(User user, String input) {
        HBox messageRow = new HBox(10);
        messageRow.setAlignment(Pos.TOP_LEFT);

        ImageView profileImage = new ImageView(user.getImg());
        profileImage.setFitHeight(30);
        profileImage.setFitWidth(30);
        profileImage.setClip(new Circle(15, 15, 15));

        Label nameLabel = new Label(user.getName());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #444444;");

        Label messageLabel = new Label(input);
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(300);
        messageLabel.setStyle("-fx-background-color: #e0e0e0; -fx-padding: 8 12 8 12; -fx-background-radius: 12;");

        VBox userMessageBox = new VBox(nameLabel, messageLabel);

        messageRow.getChildren().addAll(profileImage, userMessageBox);
        chatBox.getChildren().add(messageRow);

        if (chatBox.getChildren().size() > 12) {
            chatBox.getChildren().remove(0);
        }
    }

    void setBotText(String response) {

        Label sayDelay = new Label("북봇이 응답중입니다 > , <");
        sayDelay.setAlignment(Pos.TOP_RIGHT);
        sayDelay.setStyle("-fx-background-color: lightblue; -fx-padding: 10; -fx-background-radius: 10;");
        HBox H = new HBox(sayDelay);
        H.setAlignment(Pos.TOP_RIGHT);
        this.chatBox.getChildren().add(H);
        PauseTransition userInput = new PauseTransition(Duration.seconds(1.9));
        userInput.setOnFinished(e -> {
            chatBox.getChildren().remove(H);
        });
        userInput.play();

        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> {
            Label botLabel = new Label(response);
            botLabel.setStyle("-fx-background-color: lightblue; -fx-padding: 10; -fx-background-radius: 10;");
            botLabel.setMaxWidth(300);
            botLabel.setWrapText(true);
            botLabel.setAlignment(Pos.CENTER_RIGHT);
            HBox botBox = new HBox(botLabel);
            botBox.setAlignment(Pos.CENTER_RIGHT);
            chatBox.getChildren().add(botBox);


            if (chatBox.getChildren().size() > 12) {
                chatBox.getChildren().remove(0);
            }
        });
        delay.play();
    }

    void setBotTextRapid(String s) {
        Label botLabel = new Label(s);
        botLabel.setStyle("-fx-background-color: lightblue; -fx-padding: 10; -fx-background-radius: 10;");
        botLabel.setMaxWidth(300);
        botLabel.setWrapText(true);
        botLabel.setAlignment(Pos.CENTER_RIGHT);
        HBox botBox = new HBox(botLabel);
        botBox.setAlignment(Pos.CENTER_RIGHT);
        chatBox.getChildren().add(botBox);
        if (chatBox.getChildren().size() > 12) {
            chatBox.getChildren().remove(0);
        }
    }

    void buyBookLink(String isbn) {
        PauseTransition p1 = new PauseTransition(Duration.seconds(2));
        p1.setOnFinished(e -> {
            Book userFind = null;
            for (Book b : this.books) {
                if (b.getIsbn().equals(isbn)) {
                    userFind = b;
                    break;
                }
            }
            Book Copy = userFind;
            Label bookName = new Label("제목: " + userFind.getTitle());
            bookName.setWrapText(true);
            Label price = new Label("가격: " + userFind.getPrice());
            Label category = new Label("카테고리: " + userFind.getCategory());
            ImageView imageView = new ImageView(userFind.getImage());
            imageView.setFitHeight(150);
            imageView.setPreserveRatio(true);
            HBox input = new HBox(10);
            VBox element = new VBox(10);
            VBox click = new VBox(0);
            Label clickLabel = new Label("책 클릭시 구매 👉");
            clickLabel.setAlignment(Pos.CENTER);
            click.getChildren().add(clickLabel);
            click.setAlignment(Pos.CENTER);

            element.setMaxWidth(200);
            element.setAlignment(Pos.TOP_LEFT);
            element.setOnMousePressed(i -> {
                this.user.getBuyList().add(Copy.CopyBookForCart(Copy));
                Scene_Cart sc = new Scene_Cart();
                sc.userCart();
            });
            element.getChildren().addAll(bookName, price, category, imageView);
            element.setStyle("-fx-background-color: lightblue; -fx-padding: 10; -fx-background-radius: 10;");

            input.setAlignment(Pos.TOP_RIGHT);
            input.getChildren().addAll(click, element);
            chatBox.getChildren().add(input);
        });
        p1.play();
    }

    void showCouponImage(int couponId) {
        PauseTransition p1 = new PauseTransition(Duration.seconds(2));
        p1.setOnFinished(e -> {
            ImageView imageView = new ImageView(coupons.get(couponId - 1).getImage());
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(70);
            VBox input1 = new VBox(10);

            VBox input2 = new VBox(10);
            input1.setAlignment(Pos.TOP_RIGHT);
            input1.getChildren().add(imageView);


            Label info = new Label("쿠폰 정보가 확인되었습니다.\n" + coupons.get(couponId - 1).getName() + "1장이 지급 되었습니다.");
            info.setStyle("-fx-background-color: lightblue; -fx-padding: 10; -fx-background-radius: 10;");
            info.setWrapText(true);
            input2.getChildren().add(info);
            input2.setAlignment(Pos.TOP_RIGHT);

            VBox element = new VBox(10);
            element.setAlignment(Pos.TOP_RIGHT);
            element.setOnMousePressed(i->{
                UserDAO UD= new UserDAO();

                Scene_userInfo SU = new Scene_userInfo();
                CH_Application.getInstance().stage.setScene(SU.getUserInfoScene(UD.login(user.getId(), user.getPw())));
            });

            element.getChildren().addAll(input2, input1);
            chatBox.getChildren().add(element);
        });
        p1.play();
    }

}

