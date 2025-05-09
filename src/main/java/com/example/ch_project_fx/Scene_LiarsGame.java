package com.example.ch_project_fx;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;

public class Scene_LiarsGame {

    User user = CH_Application.getInstance().getCurrentUser();
    LiarsPlayer player1;

    int stake;
    Card fieldCard;
    String mainRank;
    Random random = new Random();
    List<Card> gameDeck;
    List<Card> LastPlayerCard = new ArrayList<>();
    HBox userDeck = new HBox(10);
    HBox player1Deck = new HBox(10);
    HBox player2Deck = new HBox(10);
    VBox textArea = new VBox(10);
    VBox resultCardBox = new VBox(10);
    HBox playerSetCards = new HBox(10);
    int BetAmount;
    Liars winner = null;
    Liars loser = null;
    boolean isLie = false;

    void startBet() {

        if (this.user.getPoint() == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(null);
            alert.setContentText("포인트가 없어 입장이 불가능합니다 잘가요 ㅠㅠ");
            alert.showAndWait();
            Scene_Minigame SM = new Scene_Minigame();
            SM.SelectGame();
        }
        userDeck.setId("userDeck");
        player1Deck.setId("player1Deck");
        textArea.setId("textArea");

        Stage smallStage = new Stage();
        VBox small = new VBox(20);
        small.setStyle("-fx-background-color: #ffffff; -fx-padding: 20; -fx-border-radius: 10;");

        VBox vipBox = new VBox(20);
        VBox vipSet = new VBox(20);

        Label inputBet = new Label("배팅할 금액을 입력하세요");
        inputBet.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label userMoneyInfo = new Label(this.user.getName() + " 보유 money: " + this.user.getPoint());

        TextField textFieldBet = new TextField();
        textFieldBet.setMaxWidth(150);

        textFieldBet.setAlignment(Pos.CENTER);

        textFieldBet.setOnAction(a -> {
            if (this.user.getPoint() < Integer.parseInt(textFieldBet.getText())) {
                vipSet.getChildren().clear();
                Label sayNo = new Label("배팅 포인트가 부족합니다");
                vipSet.getChildren().add(sayNo);
            } else {
                vipSet.getChildren().clear();
                this.BetAmount = Integer.parseInt(textFieldBet.getText());
                this.user.setPoint(this.user.getPoint() - this.BetAmount);
                UserDAO UD = new UserDAO();
                UD.subtractPointFromUser(this.user.getId(), this.BetAmount);
                smallStage.close();
                playLiarsGame();
            }
        });

        vipBox.getChildren().addAll(inputBet, userMoneyInfo, textFieldBet);

        small.getChildren().addAll(vipBox, vipSet);
        small.setAlignment(Pos.CENTER);
        Scene smallScene = new Scene(small, 240, 160);
        smallScene.getStylesheets().add(getClass().getResource("/css/liarsgame.css").toExternalForm());
        smallStage.setScene(smallScene);
        smallStage.show();
    }

    //+++++++++++++++++++++++++++++++++++++++++++배팅 완료 +++++++++++++++++++++++++++++++++++++++++++++++++++++
    void playLiarsGame() {
        Deck LiarsDeck = new Deck();
        this.gameDeck = LiarsDeck.LiarsDecK();

        LiarsPlayer player1 = new LiarsPlayer();
        LiarsPlayer player2 = new LiarsPlayer();

        while (true) {
            if (player2.name.equals(player1.name))
                player2 = new LiarsPlayer();
            else break;
        }
        this.player1 = player1;


        this.player1.img = new Image(getClass().getResource("/img/minigame/liarplayer1.png").toExternalForm());


        for (int i = 0; i < 5; i++) {
            player1.PlayerDeck.add(this.gameDeck.remove(0));
            user.PlayerDeck.add(this.gameDeck.remove(0));
        }


        this.fieldCard = gameDeck.remove(0);
        if (this.fieldCard.getRank().equals("joker")) {
            this.fieldCard = gameDeck.remove(0);
        }
        if (this.fieldCard.getRank().equals("joker")) {
            this.fieldCard = gameDeck.remove(0);
        }
        this.mainRank = fieldCard.getRank();
        player1.mainRank = mainRank;
        player2.mainRank = mainRank;
        user.mainRank = mainRank;
        updatePlayersDeck();
//+++++++++++++++++++++++++++++++++++++++게임준비++++++++++++++++++++++++++++++++++++++++++++++++++
        BorderPane main = new BorderPane();

        HBox top = new HBox(50);

        HBox playersBox1 = new HBox(20);
        VBox player1Info = new VBox(10);


        ImageView player1img = new ImageView(this.player1.img);
        player1img.setFitHeight(160);
        player1img.setPreserveRatio(true);
        Label player1name = new Label(this.player1.getName());
        player1Info.getChildren().addAll(player1img, player1name);

        playersBox1.getChildren().addAll(player1Info, this.player1Deck);


        playersBox1.setStyle("-fx-border-color: black; -fx-border-width: 2px;");

        top.getChildren().addAll(playersBox1);
        main.setTop(top);
//++++++++++++++++++++++++++++ 상단 상대플레이어 덱 정보 박스 ++++++++++++++++++++++++++++++++++++++++

        HBox middle = new HBox(20);
        VBox mainRankCardBox = new VBox(10);
        Label label = new Label("메인랭크: "+this.mainRank);
        ImageView MainRankCard = new ImageView(this.fieldCard.img);
        MainRankCard.setFitHeight(100);
        mainRankCardBox.getChildren().addAll(label,MainRankCard);
        MainRankCard.setPreserveRatio(true);
        middle.setAlignment(Pos.CENTER);
        this.textArea.setAlignment(Pos.CENTER);
        resultCardBox.setAlignment(Pos.CENTER);
        mainRankCardBox.setAlignment(Pos.CENTER);
        middle.getChildren().addAll( mainRankCardBox,this.textArea, resultCardBox);


        main.setCenter(middle);
//+++++++++++++++++++++++++++중단 딜러 ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        HBox bottom = new HBox(20);
        VBox userInfoBox = new VBox(10);
        Label userName = new Label(this.user.getName());
        ImageView userImg = new ImageView(this.user.getImg());
        userImg.setFitHeight(200);
        userImg.setPreserveRatio(true);
        userInfoBox.getChildren().addAll(userName, userImg);
        userDeck.setStyle("-fx-border-width: 1; -fx-border-color: black");
        bottom.getChildren().addAll(userInfoBox, this.userDeck, this.playerSetCards);


        main.setBottom(bottom);
//++++++++++++++++++++++++++++하단 유저정보++++++++++++++++++++++++++++++++++++++++++++++++
        Scene LiarsGameScene = new Scene(main);
        LiarsGameScene.getStylesheets().add(getClass().getResource("/css/liarsgame.css").toExternalForm());

        CH_Application.getInstance().stage.setScene(LiarsGameScene);
        startGameFlow();

    }

    void startGameFlow() {
        setText(player1.getName() + "(이)가 입장했습니다");
        PauseTransition p1 = new PauseTransition(Duration.seconds(2));
        p1.setOnFinished(e -> showMainRank());
        p1.play();
    }

    void showMainRank() {
        setText("제시 랭크는 [" + this.mainRank + "] 입니다");
        PauseTransition p2 = new PauseTransition(Duration.seconds(2));
        p2.setOnFinished(e -> PlayerSetCard());
        p2.play();
    }

// ------------------------- 1턴 -------------------------

    void PlayerSetCard() {
        setText("플레이어 차례입니다. 제출할 카드를 3장까지 골라주세요");
        userTurnCardSelect(selectedCards -> {
            this.LastPlayerCard = selectedCards;
            showResultCardBox();
            PauseTransition p3 = new PauseTransition(Duration.seconds(2));
            p3.setOnFinished(e -> aiStrike());
            p3.play();
        });
    }

    void aiStrike() {
        if (this.winner != null) return;
        this.userDeck.setStyle("-fx-border-color: black; -fx-border-width: 2;");
        aiChooseForStrike();
        PauseTransition p4 = new PauseTransition(Duration.seconds(2));
        p4.setOnFinished(e -> aiSetCard());
        p4.play();
    }
    void aiSetCard(){
        if (this.winner != null) return;
        this.LastPlayerCard = this.player1.selectAct(this.gameDeck);
        setText(this.player1.getName()+"(이)가 카드를 제출합니다");
        showHiddenResultCartBox();
        updatePlayersDeck();
        PauseTransition p5 = new PauseTransition(Duration.seconds(2));
        p5.setOnFinished(e->playerStrike());
        p5.play();
    }
    void playerStrike(){
        if (this.winner != null) return;
        setText("라이어? or 넘기기");

        PauseTransition p = new PauseTransition(Duration.seconds(0.5)); // 아주 짧은 딜레이
        p.setOnFinished(ev -> {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("라이어 선언");
                alert.setGraphic(null);
                alert.setContentText("라이어를 선언하시겠습니까?");
                ButtonType yes = new ButtonType("라이어!");
                ButtonType no = new ButtonType("넘기기");
                alert.getButtonTypes().setAll(yes, no);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == yes) {
                    // 라이어 선언
                    showResultCardBox();
                    this.isLie = this.user.StrikeLiar(this.LastPlayerCard);
                    if(isLie){
                        setText(this.player1.getName()+"(은)는 라이어가 맞았습니다");
                        this.winner = this.user;
                        this.loser = this.player1;
                    }else{
                        setText(this.player1.getName()+"(은)는 라이어가 아니었습니다");
                        this.winner = this.player1;
                        this.loser = this.user;
                    }
                    PauseTransition p6 = new PauseTransition(Duration.seconds(2));
                    p6.setOnFinished(e -> getResultScene());
                    p6.play();
                } else {
                    if (user.getHand().isEmpty()) {
                        setText("카드가 없는데 라이어를 참다니! " + player1.getName() + "의 승리!");
                        winner = player1;
                        loser = user;

                        PauseTransition p1 = new PauseTransition(Duration.seconds(2));
                        p1.setOnFinished(e2 -> getResultScene());
                        p1.play();
                        return;
                    }
                    PauseTransition p6 = new PauseTransition(Duration.seconds(2));
                    p6.setOnFinished(e -> PlayerSetCard());
                    p6.play();
                }
            });
        });
        p.play();
    }


    void updatePlayersDeck() {
        this.player1Deck.getChildren().clear();
        this.userDeck.getChildren().clear();
        for (Card c : this.player1.PlayerDeck) {
            ImageView img = new ImageView( new Image(getClass().getResource("/img/cards/Card-back.png").toExternalForm()));
            img.setFitHeight(140);
            img.setPreserveRatio(true);
            img.setDisable(true);
            VBox imageBox = new VBox();
            imageBox.getChildren().add(img);
            this.player1Deck.getChildren().add(imageBox);
        }
        for (Card c : this.user.PlayerDeck) {
            ImageView img = new ImageView(c.img);
            img.setPreserveRatio(true);
            img.setFitHeight(140);
            VBox imageBox = new VBox();
            imageBox.getChildren().add(img);
            this.userDeck.getChildren().add(imageBox);
        }

    }

    void setText(String text) {
        Label input1 = new Label(text);
        input1.setId("textLabel");
        this.textArea.getChildren().addAll(input1);
        if (this.textArea.getChildren().size() > 7) {
            this.textArea.getChildren().remove(0);
        }
    }

    void userTurnCardSelect(Consumer<List<Card>> onSubmit) {
        this.userDeck.getChildren().clear();
        this.userDeck.setStyle("-fx-border-color: red; -fx-border-width: 2;");
        List<Card> selected = new ArrayList<>();

        for (Card c : this.user.PlayerDeck) {
            ImageView img = new ImageView(c.img);
            img.setPreserveRatio(true);
            img.setFitHeight(140);

            VBox imageBox = new VBox();
            imageBox.getChildren().add(img);

            imageBox.setOnMousePressed(e -> {
                if (selected.contains(c)) return; // 중복 방지
                if (selected.size() >= 3) {
                    System.out.println("3장 이상은 제출할 수 없습니다.");
                    return;
                }
                selected.add(c);
                showPlayersSelectCards(c.img);
                this.user.getHand().remove(c);
                img.setVisible(false);
                System.out.println("선택된 카드: " + c.getRank());
                this.userDeck.getChildren().remove(imageBox);
            });

            this.userDeck.getChildren().add(imageBox);
        }

        Button submit = new Button("확인");
        submit.setOnAction(e -> {
            onSubmit.accept(selected);
            updatePlayersDeck();// 선택 완료 후 콜백으로 결과 전달
        });
        this.userDeck.getChildren().add(submit);
    }

    void showResultCardBox() {
        this.resultCardBox.getChildren().clear();
        Label label = new Label("제출한 카드");
        HBox element = new HBox();
        for (Card C : this.LastPlayerCard) {

            ImageView img = new ImageView(C.img);
            img.setPreserveRatio(true);
            img.setFitHeight(100);
            element.getChildren().add(img);
        }
        this.resultCardBox.getChildren().addAll(label, element);
    }
    void showPlayersSelectCards(Image img){
        Label label = new Label("제출됨");

        VBox element = new VBox();
        ImageView imgage = new ImageView(img);
        imgage.setFitHeight(140);
        imgage.setPreserveRatio(true);
        element.getChildren().addAll(label,imgage);
        this.playerSetCards.getChildren().add(element);
    }

    void showHiddenResultCartBox() {
        this.resultCardBox.getChildren().clear();
        Label label = new Label("상대 제출카드");
        HBox element = new HBox();
        for (Card C : this.LastPlayerCard) {
            Image img = new Image(getClass().getResource("/img/cards/Card-back.png").toExternalForm());
            ImageView img1 = new ImageView(img);
            img1.setPreserveRatio(true);
            img1.setFitHeight(100);
            element.getChildren().add(img1);
        }
        this.resultCardBox.getChildren().addAll(label, element);
    }

    void aiChooseForStrike() {
        setText(player1.getName() + "(이)가 고민중입니다...");
        PauseTransition p1 = new PauseTransition(Duration.seconds(0.5));
        p1.setOnFinished(e -> {
            switch (player1.getStrategy()) {
                case RANDOM:
                    int choose = random.nextInt(100) + 1;
                    if (user.getHand().isEmpty())
                        choose -= 90;
                    if (choose < 51) {
                        setText(player1.getName() + "(이)가 " + user.getName() + " 에게 라이어 선언");
                        PauseTransition p2 = new PauseTransition(Duration.seconds(0.5));
                        p2.setOnFinished(e1 -> {
                            this.isLie = player1.StrikeLiar(this.LastPlayerCard);
                            if (isLie) {
                                setText(user.getName() + "(은)는 라이어 였습니다");
                                this.winner = this.player1;
                                this.loser = this.user;
                            } else {
                                setText(user.getName() + "(은)는 라이어가 아니였습니다");
                                this.winner = this.user;
                                this.loser = this.player1;
                            }
                            getResultScene();
                        });
                        p2.play();
                    } else {
                        PauseTransition p2 = new PauseTransition(Duration.seconds(0.5));
                        p2.setOnFinished(e1 -> {
                            setText(this.player1.getName() + "(은)는 라이어 선언을 참았습니다");
                        });
                        p2.play();
                    }
                    break;
                case DEFENSIVE:
                    choose = random.nextInt(100) + 1;
                    if (user.getHand().isEmpty())
                        choose -= 90;
                    if (choose < 15) {
                        setText(player1.getName() + "(이)가 " + user.getName() + " 에게 라이어 선언");
                        PauseTransition p2 = new PauseTransition(Duration.seconds(0.5));
                        p2.setOnFinished(e1 -> {
                            this.isLie = player1.StrikeLiar(this.LastPlayerCard);
                            if (isLie) {
                                setText(user.getName() + "(은)는 라이어 였습니다");
                                this.winner = this.player1;
                                this.loser = this.user;
                            } else {
                                setText(user.getName() + "(은)는 라이어가 아니였습니다");
                                this.winner = this.user;
                                this.loser = this.player1;
                            }getResultScene();
                        });
                        p2.play();
                    } else {
                        PauseTransition p2 = new PauseTransition(Duration.seconds(0.5));
                        p2.setOnFinished(e1 -> {
                            setText(this.player1.getName() + "(은)는 라이어 선언을 참았습니다");
                        });
                        p2.play();
                    }
                    break;
                case AGGRESSIVE:
                    choose = random.nextInt(100) + 1;
                    if (this.LastPlayerCard.size() == 3 && this.player1.getHand().stream().anyMatch(e4 -> e4.getRank().equals(this.mainRank)))
                        choose -= 90;
                    if (user.getHand().isEmpty())
                        choose -= 90;
                    if (choose < 20) {
                        setText(player1.getName() + "(이)가 " + user.getName() + " 에게 라이어 선언");
                        PauseTransition p2 = new PauseTransition(Duration.seconds(0.5));
                        p2.setOnFinished(e1 -> {
                            this.isLie = player1.StrikeLiar(this.LastPlayerCard);
                            if (isLie) {
                                setText(user.getName() + "(은)는 라이어 였습니다");
                                this.winner = this.player1;
                                this.loser = this.user;
                            } else {
                                setText(user.getName() + "(은)는 라이어가 아니였습니다");
                                this.winner = this.user;
                                this.loser = this.player1;
                            }getResultScene();
                        });
                        p2.play();
                    } else {
                        PauseTransition p2 = new PauseTransition(Duration.seconds(0.5));
                        p2.setOnFinished(e1 -> {
                            setText(this.player1.getName() + "(은)는 라이어 선언을 참았습니다");
                        });
                        p2.play();
                    }
                    break;
                case CHEATER:
                    if (this.LastPlayerCard.stream().noneMatch(e3 -> e3.getRank().equals(this.mainRank) || e3.getRank().equals("joker"))) {
                        setText(player1.getName() + "(이)가 " + user.getName() + " 에게 라이어 선언");
                        PauseTransition p2 = new PauseTransition(Duration.seconds(0.5));
                        p2.setOnFinished(e1 -> {
                            this.isLie = player1.StrikeLiar(this.LastPlayerCard);
                            if (isLie) {
                                setText(user.getName() + "(은)는 라이어 였습니다");
                                this.winner = this.player1;
                                this.loser = this.user;
                            } else {
                                setText(user.getName() + "(은)는 라이어가 아니였습니다");
                                this.winner = this.user;
                                this.loser = this.player1;
                            }getResultScene();
                        });
                        p2.play();

                    } else {
                        PauseTransition p2 = new PauseTransition(Duration.seconds(0.5));
                        p2.setOnFinished(e1 -> {
                            setText(this.player1.getName() + "(은)는 라이어 선언을 참았습니다");
                        });
                        p2.play();
                    }
                    break;
            }if(this.user.getHand().isEmpty()){
                this.winner = user;
                this.loser = player1;
                getResultScene();
            }

        });
        p1.play();
    }
    public boolean StrikeLiar(List<Card> LastPlayerCard) {

        for (Card c : LastPlayerCard) {
            if (!c.getRank().equals(this.mainRank) && !c.getRank().equals("Joker")) {
                return true; // 하나라도 다르면 거짓말
            }
        }

        return false; // 전부 다 같으면 진실
    }
    boolean isGameOver() {
        return this.winner != null && this.loser != null;
    }
    void getResultScene() {
        if (!isGameOver()) return;

        setText("게임 종료!");
        setText("승자: " + this.winner.getName());
        setText("패자: " + this.loser.getName());
        PauseTransition showResult = new PauseTransition(Duration.seconds(2));
        showResult.setOnFinished(e->{
            if(this.winner == user){
                UserDAO UD = new UserDAO();
                UD.addPointToUser(this.user.getId(),this.BetAmount*2);
                setText(this.BetAmount*2+"포인트 지급됨!");
                if(this.BetAmount*2>10000){
                    CouponDAO CD = new CouponDAO();
                    CD.giveCouponToUser(this.user.getId(),4);
                    setText("10000포인트를 따내어 쿠폰이 지급되었습니다!");
                }
            }else{
                setText("다음기회에 또 도전하세요!");
            }
        });
        showResult.play();

        PauseTransition resetDelay = new PauseTransition(Duration.seconds(5));
        resetDelay.setOnFinished(e ->  {
            CH_Application.getInstance().getCurrentUser().PlayerDeck.clear();
            this.textArea.getChildren().clear();
            this.resultCardBox.getChildren().clear();
            this.playerSetCards.getChildren().clear();
            this.userDeck.getChildren().clear();
            this.player1Deck.getChildren().clear();
            this.BetAmount=0;
            // 승패 초기화
            this.winner = null;
            this.loser = null;
            this.LastPlayerCard.clear();
        Scene_Minigame SM = new Scene_Minigame();
        SM.SelectGame();});
        resetDelay.play();

    }
}
