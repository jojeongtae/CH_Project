package com.example.ch_project_fx;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class User implements Liars{
    private String id;
    private String pw;
    private String name;
    private String phone;
    private String address;
    private String imgPath;
    private Image img;
    private int point;
    private String grade;
    private int totalPayed;
    private ArrayList<Book> buyList = new ArrayList<>();
    private ArrayList<Book> borrowList = new ArrayList<>();
    private List<Coupon> coupons = new ArrayList<>();
    private ArrayList <Book> allBuyList = new ArrayList<>();
    public String mainRank;
    public List<Card> PlayerDeck = new ArrayList<>();
    LiarsStrategy strategy;


    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getImgPath() {
        return imgPath;
    }

    public Image getImg() {
        return img;
    }

    public int getPoint() {
        return point;
    }

    public String getGrade() {
        return grade;
    }

    public int getTotalPayed() {
        return totalPayed;
    }

    public ArrayList<Book> getBuyList() {
        return buyList;
    }

    public ArrayList<Book> getBorrowList() {
        return borrowList;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setTotalPayed(int totalPayed) {
        this.totalPayed = totalPayed;
    }

    public void setBuyList(ArrayList<Book> buyList) {
        this.buyList = buyList;
    }

    public void setBorrowList(ArrayList<Book> borrowList) {
        this.borrowList = borrowList;
    }

    public ArrayList<Card> selectAct(List<Card> gameDeck) {
//        List<Card> selected = new ArrayList<>();
//        SP.s("제출 할 카드 번호들을 3장까지 선택하세요 (여러장 선택시 중간에 공백)",300);
//        showDeck();
//        while (true) {
//            String select = input.nextLine().trim();
//            if (select.isEmpty()) {
//                System.out.println("입력값이 없습니다. 다시 입력해주세요.");
//                continue;
//            }
//            String[] arr = select.split(" ");
//
//            if (arr.length > 3) {
//                System.out.println("카드는 3장까지 가능합니다");
//            } else if (arr.length > 0) {
//                boolean flag = true;
//                for (int i = arr.length - 1; i >= 0; i--) {
//                    int index = Integer.parseInt(arr[i]) - 1;
//                    if (index >= 0 && index < PlayerDeck.size()) {
//                        selected.add(PlayerDeck.get(index));
//                        PlayerDeck.remove(index);
//                    } else {
//                        System.out.println("잘못된 카드 번호: " + arr[i]);
//                        flag = false;
//                        break;
//                    }
//                }
//                if (flag) {
//                    break;
//                } else {
//                    System.out.println("다시 시도하세요. 1~" + PlayerDeck.size() + " 범위의 카드 번호를 입력해주세요.");
//                }
//            } else {
//                System.out.println("1장 이상의 카드를 선택해야 합니다.");
//            }
//        }
//        Collections.reverse(selected);
//        SP.s(getName() + "(이)가 카드를 내면서 말합니다! 😁 [" + this.mainRank + "] " + selected.size() + "장! ", 1000);
        System.out.println("hi");
        return null;
    }




    public boolean StrikeLiar(List<Card> LastPlayerCard) {
        for (Card c : LastPlayerCard) {
            if (!c.getRank().equals(this.mainRank) && !c.getRank().equals("Joker")) {
                return true;
            }
        }

        return false;
    }
    public List<Card> getHand(){
        return  this.PlayerDeck;
    }
    public LiarsStrategy getStrategy(){
        return null;
    }
}
