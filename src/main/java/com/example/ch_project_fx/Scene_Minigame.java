package com.example.ch_project_fx;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Optional;

public class Scene_Minigame {
    Deck deck ;
    ArrayList <Card> cards;
    Scene currentScene;
    HBox userDeck = new HBox(20);
    HBox computerDeck = new HBox(20);
    ArrayList <Card> computerCards = new ArrayList<>();
    ArrayList <Card> userCards = new ArrayList<>();
    Label computerScoreLabel = new Label();
    Label userScoreLabel = new Label();
    User blackJackWinner = null;
    int count = 0;
    int userTotalPoint = 0;
    int computerTotalPoint = 0;
    int BetAmount;
    int rewardPoint=0;
    Card SplitCard;
    User currentUser = CH_Application.getInstance().getCurrentUser();
    Stage stage = CH_Application.getInstance().stage;
    void SelectGame(){

        VBox main = new VBox(20);
        Image logo = new Image(getClass().getResource("/img/bookMarket/logo.png").toExternalForm());
        ImageView imageViewLogo = new ImageView(logo);
        imageViewLogo.setFitHeight(100);
        imageViewLogo.setPreserveRatio(true);
        imageViewLogo.setOnMousePressed(e->{
            Scene_userSelect su = new Scene_userSelect();
            CH_Application.getInstance().stage.setScene(su.getUserSelectScene());
        });
        main.setStyle("-fx-background-color: white");
        main.setAlignment(Pos.CENTER);
        Label saySelect = new Label("게임을 선택하세요");
        saySelect.setStyle("-fx-text-fill: black;");
        main.getChildren().addAll(imageViewLogo,saySelect);

        HBox games = new HBox(20);
        games.setAlignment(Pos.CENTER);

        Image BJ = new Image(getClass().getResource("/img/minigame/blackjack.png").toExternalForm());
        ImageView imageView2 = new ImageView(BJ);
        imageView2.setFitWidth(350);
        imageView2.setPreserveRatio(true);
        imageView2.setOnMousePressed(e->{
            BlackJack();
            if(this.currentUser.getPoint()<0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initOwner(this.stage);
                alert.setTitle("포인트가 없네요!?");
                alert.setContentText("아쉽네요 ㅠ 좀더 독서를 한 뒤에 오세요 ");
                alert.showAndWait();
                Scene_Login SL = new Scene_Login();
                SL.Login();
            }
            this.stage.setScene(this.currentScene);
        });
        Image Lie = new Image(getClass().getResource("/img/minigame/Liar.png").toExternalForm());
        ImageView imageView3 = new ImageView(Lie);
        imageView3.setFitWidth(350);
        imageView3.setPreserveRatio(true);
        imageView3.setOnMousePressed(e->{
            Scene_LiarsGame LG = new Scene_LiarsGame();
            if(this.currentUser.getPoint()<0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initOwner(this.stage);
                alert.setTitle("포인트가 없네요!?");
                alert.setContentText("아쉽네요 ㅠ 좀더 독서를 한 뒤에 오세요 ");
                alert.showAndWait();
                Scene_Login SL = new Scene_Login();
                SL.Login();
            }else{
            LG.startBet();
            }
        });
        games.getChildren().addAll(imageView2,imageView3);

        main.getChildren().add(games);

        Scene SelectGameScene = new Scene(main);
        CH_Application.getInstance().stage.setScene(SelectGameScene);
    }
    void BlackJack() {
        // 스타일 공통 설정
        String labelStyle = "-fx-font-size: 16px; -fx-font-weight: bold;";
        String buttonStyle = "-fx-background-color: #4CAF50; -fx-text-fill: white;";
        String buttonHover = "-fx-background-color: #45a049;";
        String textFieldStyle = "-fx-font-size: 14px; -fx-border-color: #666; -fx-border-radius: 5px; -fx-padding: 5px;";
        String vboxStyle = "-fx-alignment: center; -fx-spacing: 15px;";
        String hboxStyle = "-fx-alignment: center; -fx-spacing: 20px;";

        // 메인 레이아웃
        VBox BlackJackMain = new VBox(20);
        BlackJackMain.setAlignment(Pos.CENTER);
        BlackJackMain.setStyle("-fx-background-color: green; -fx-border-color: black; -fx-border-width: 5px; -fx-border-radius: 20px; -fx-background-radius: 40px;");

        this.deck = new Deck();
        this.cards = deck.cards;
        startBlackJack();

        // 상단 정보
        HBox head = new HBox(20);
        head.setAlignment(Pos.CENTER);
        head.setStyle(hboxStyle);
        Label userName = new Label("이름: " + this.currentUser.getName());
        Label userId = new Label("아이디: " + this.currentUser.getId());
        Label userMoney = new Label("보유 포인트: " + this.currentUser.getPoint());
        userName.setStyle(labelStyle);
        userId.setStyle(labelStyle);
        userMoney.setStyle(labelStyle);
        head.getChildren().addAll(userId, userName, userMoney);

        // 중간 부분 - 점수
        VBox userScore = new VBox(20);
        userScore.setStyle(vboxStyle);
        this.computerScoreLabel.setText("딜러 점수 : ???");
        this.userScoreLabel.setText(this.currentUser.getName() + "님의 점수: ");
        computerScoreLabel.setStyle(labelStyle);
        userScoreLabel.setStyle(labelStyle);
        userScore.getChildren().addAll(computerScoreLabel, userScoreLabel);

        // 게임 버튼들
        Button hit = new Button("Hit");
        hit.setStyle(buttonStyle);
        hit.setOnMouseEntered(e -> hit.setStyle(buttonHover));
        hit.setOnMouseExited(e -> hit.setStyle(buttonStyle));
        hit.setOnMouseClicked(e -> {
            this.userCards.add(drawCard());
            updateUserDeck();
            if (this.userTotalPoint > 21) result();
        });

        Button doubleDown = new Button("Double Down");
        doubleDown.setStyle(buttonStyle);
        doubleDown.setOnMouseEntered(e -> doubleDown.setStyle(buttonHover));
        doubleDown.setOnMouseExited(e -> doubleDown.setStyle(buttonStyle));
        doubleDown.setOnMouseClicked(e -> {
            this.userCards.add(drawCard());
            this.userCards.add(drawCard());
            updateUserDeck();
            while (this.computerTotalPoint < 17) {
                this.count = 2;
                this.computerCards.add(drawCard());
                updateComputerDeck();
            }
            result();
        });

        Button split = new Button("Split");
        split.setStyle(buttonStyle);
        split.setOnMouseEntered(e -> split.setStyle(buttonHover));
        split.setOnMouseExited(e -> split.setStyle(buttonStyle));
        split.setOnMouseClicked(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initOwner(this.stage);
            alert.setTitle("split");
            alert.setContentText("다음 턴으로 넘길 카드를 선택하세요");
            alert.showAndWait();
            Stage smallStage = new Stage();
            smallStage.initModality(Modality.WINDOW_MODAL);
            smallStage.initOwner(this.stage);
            HBox all = new HBox(20);
            for (Card c : this.userCards) {
                ImageView img = new ImageView(c.img);
                img.setFitHeight(200);
                img.setPreserveRatio(true);
                img.setOnMousePressed(u -> {
                    this.SplitCard = c;
                    this.userCards.remove(c);
                    updateUserDeck();
                    smallStage.close();
                });
                all.getChildren().add(img);
            }
            split.setDisable(true);
            Scene scene = new Scene(all, 400, 300);
            smallStage.setScene(scene);
            smallStage.show();

            this.userCards.add(drawCard());
        });

        Button stand = new Button("Stand");
        stand.setStyle(buttonStyle);
        stand.setOnMouseEntered(e -> stand.setStyle(buttonHover));
        stand.setOnMouseExited(e -> stand.setStyle(buttonStyle));
        stand.setOnMouseClicked(e -> {
            while (getPointComputer() < 17) {
                computerCards.add(drawCard());
                updateComputerDeck();
            }
            result();
        });

        VBox buttons = new VBox(20);
        buttons.setStyle(vboxStyle);
        buttons.getChildren().addAll(hit, doubleDown, split, stand);

        // 중간 레이아웃
        HBox middle = new HBox(20);
        middle.setAlignment(Pos.CENTER);
        middle.setStyle(hboxStyle);
        middle.getChildren().addAll(userScore, buttons);

        // 블랙잭 메인에 추가
        BlackJackMain.getChildren().addAll(head, this.computerDeck, middle, this.userDeck);

        // 배팅 창
        Stage smallStage = new Stage();
        smallStage.initModality(Modality.WINDOW_MODAL);
        smallStage.initOwner(stage);

        VBox small = new VBox(20);
        small.setStyle(vboxStyle);
        VBox vipBox = new VBox(20);
        VBox vipSet = new VBox(20);

        Label inputBet = new Label("배팅할 금액을 입력하세요");
        inputBet.setStyle(labelStyle);
        Label userMoneyInfo = new Label(this.currentUser.getName() + " 보유 money: " + this.currentUser.getPoint());
        userMoneyInfo.setStyle(labelStyle);
        TextField textFieldBet = new TextField();
        textFieldBet.setStyle(textFieldStyle);
        textFieldBet.setAlignment(Pos.CENTER);

        textFieldBet.setOnAction(a -> {
            if (this.currentUser.getPoint() < Integer.parseInt(textFieldBet.getText())) {
                vipSet.getChildren().clear();
                Label sayNo = new Label("배팅 포인트가 부족합니다");
                vipSet.getChildren().add(sayNo);
            } else {
                vipSet.getChildren().clear();
                this.BetAmount = Integer.parseInt(textFieldBet.getText());
                this.currentUser.setPoint(this.currentUser.getPoint() - this.BetAmount);
                UserDAO UD = new UserDAO();
                UD.subtractPointFromUser(this.currentUser.getId(), this.BetAmount);
                smallStage.close();
                if (this.SplitCard == null) {
                    this.userCards.add(drawCard());
                    this.userCards.add(drawCard());
                    updateUserDeck();
                } else {
                    this.userCards.add(SplitCard);
                    this.userCards.add(drawCard());
                    this.SplitCard = null;
                    updateUserDeck();
                }
                this.computerCards.add(drawCard());
                this.computerCards.add(drawCard());
                updateComputerDeck();
                stage.setScene(new Scene(BlackJackMain));
            }
        });

        vipBox.getChildren().addAll(inputBet, userMoneyInfo, textFieldBet);

        small.getChildren().addAll(vipBox, vipSet);

        Scene smallScene = new Scene(small, 400, 300);

        smallStage.setScene(smallScene);
        smallStage.setTitle("배팅 금액");
        smallStage.show();
    }
    void startBlackJack(){
        ImageView unknownCard = new ImageView(deck.unknown.img);
        unknownCard.setFitHeight(250);
        unknownCard.setPreserveRatio(true);
        ImageView unknownCard2 = new ImageView(deck.unknown.img);
        unknownCard2.setFitHeight(250);
        unknownCard2.setPreserveRatio(true);
        ImageView unknownCard3 =  new ImageView(deck.unknown.img);
        unknownCard3.setFitHeight(250);
        unknownCard3.setPreserveRatio(true);
        ImageView unknownCard4 =  new ImageView(deck.unknown.img);
        unknownCard4.setFitHeight(250);
        unknownCard4.setPreserveRatio(true);
        this.computerDeck.getChildren().addAll(unknownCard,unknownCard2);
        this.userDeck.getChildren().addAll(unknownCard3,unknownCard4);
    }
    Card drawCard(){
        Card returnCard = this.cards.get(0);
        this.cards.remove(0);
        return  returnCard;
    }
    void updateUserDeck(){
        this.userDeck.getChildren().clear();
        int totalValue = 0;
        for(Card c : this.userCards){
            ImageView imageView = new ImageView(c.img);
            imageView.setFitHeight(250);
            imageView.setPreserveRatio(true);
            this.userDeck.getChildren().add(imageView);
            totalValue += c.getValue();
        }
        this.userScoreLabel.setText(this.currentUser.getName()+" 님의 점수: " +totalValue);
        this.userTotalPoint=totalValue;
    }
    void updateComputerDeck(){
        this.computerDeck.getChildren().clear();
        int totalValue = 0;
        for(Card c : this.computerCards){
            if(this.computerCards.size()==2) {
                ImageView imageViewU = new ImageView(this.deck.unknown.img);
                imageViewU.setFitHeight(250);
                imageViewU.setPreserveRatio(true);
                this.computerDeck.getChildren().add(imageViewU);
                ImageView imageView2 = new ImageView(this.computerCards.get(1).img);
                imageView2.setFitHeight(250);
                imageView2.setPreserveRatio(true);
                this.computerDeck.getChildren().add(imageView2);
                break;
            }else {
                ImageView imageView = new ImageView(c.img);
                imageView.setFitHeight(250);
                imageView.setPreserveRatio(true);
                this.computerDeck.getChildren().add(imageView);
                totalValue += c.getValue();
            }
        }
        this.computerTotalPoint = totalValue;
    }
    int getPointComputer(){
        int result = 0 ;
        for(Card c : this.computerCards){
            result+=c.getValue();
        }
        return result;
    }
    int getPointUser(){
        int result = 0 ;
        for(Card c : this.userCards){
            result+=c.getValue();
        }
        return result;
    }
    void result(){
        if(this.userTotalPoint<= 21){
            if(this.computerTotalPoint > 21 || this.userTotalPoint> this.computerTotalPoint){
                this.blackJackWinner = this.currentUser;
                if(this.count==2){
                    this.BetAmount*=4;
                }else
                    this.BetAmount*=2;
                System.out.println("승리 판정 진입");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initOwner(this.stage);
                alert.setTitle("축하합니다. "+this.BetAmount+"원 획득! ");
                alert.setContentText("당신이 승리했습니다! "+"\n"+"내 점수: "+getPointUser()+" 딜러점수: "+getPointComputer());
                this.currentUser.setPoint(this.currentUser.getPoint()+this.BetAmount);
                UserDAO UD = new UserDAO();
                UD.addPointToUser(this.currentUser.getId(),this.BetAmount);
                alert.showAndWait();
                this.rewardPoint+=this.BetAmount;
                if(this.rewardPoint>10000){
                    CouponDAO CD = new CouponDAO();
                    CD.giveCouponToUser(currentUser.getId(),4);
                    this.rewardPoint = 0;
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setGraphic(null);
                    alert1.setContentText("10000포인트를 얻어 쿠폰이 지급됬습니다!");
                    CH_Application.getInstance().setCurrentUser(UD.login(currentUser.getId(),currentUser.getPw()));
                    alert1.showAndWait();
                }
                playAgain();
            }
            else{
                System.out.println("승리 판정 진입");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initOwner(this.stage);
                alert.setTitle("버스트!");
                alert.setContentText("당신이 패배했습니다! "+"\n"+"내 점수: "+getPointUser()+" 딜러점수: "+getPointComputer());
                alert.showAndWait();
                playAgain();
            }
        }  else {
            // 유저가 버스트인 경우
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initOwner(this.stage);
            alert.setTitle("버스트!");
            alert.setContentText("당신이 패배했습니다! " + "\n" + "내 점수: " + getPointUser() + " 딜러 점수: " + getPointComputer());
            alert.showAndWait();
            playAgain();
        }
    }
    void playAgain(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(this.stage);
        alert.setTitle("확인");
        alert.setHeaderText(null);
        alert.setContentText("한판더?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == ButtonType.CANCEL) {
                resetAll();
                SelectGame();
            } else if (result.get() == ButtonType.OK) {
                resetAll();
                BlackJack();
            }
        }
    }
    void resetAll(){
        this.BetAmount = 0 ;
        this.userCards = new ArrayList<Card>();
        this.computerCards = new ArrayList<Card>();
        this.count = 0;
        this.userScoreLabel.setText("내 점수 : ");
        this.computerDeck.getChildren().clear();
        this.userDeck.getChildren().clear();
    }
}
