import java.util.Scanner;

public class MarketManager {
    Scanner sc = new Scanner(System.in);

    void run() {
        userSelect();

    }

    void userSelect() {
        while (true) {
            System.out.println("""
                    북마켓에 오신것을 환영합니다!
                    숫자로 선택해주세요
                    1. 책 검색
                    2. 장바구니 확인
                    3. 영수증 출력
                    4. 결제
                    """);
            int select = 0;
            try {
                select = sc.nextInt();
            } catch (Exception e) {
                System.out.println("숫자를 입력해주세여");
                sc.nextLine();
            }

            switch (select) {
                case 1:
                    SearchBook();
                    break;
                case 2:
                    cartList();
                    break;
                case 3:
                    showBill();
                    break;
                case 4:
                    payment();
                    break;
            }
        }
    }
    void SearchBook(){
        System.out.println("""
                검색하실 카테고리를 선택해주세요
                1.책 이름
                2.저자
                3.분류
                4.가격순
                """);
        int choice  = sc.nextInt();
        switch (choice){
            case 1:
                System.out.println("책 이름으로 검색");
                break;
            case 2:
                System.out.println("책 저자이름으로 검색");
                break;
            case 3:
                System.out.println("책 분류별로 검색");
                break;
            case 4:
                System.out.println("가격순으로 정렬");
                break;
        }
    }
    void cartList(){
        System.out.println("현재 장바구니 리스트입니다");
    }
    void showBill(){
        System.out.println("영수증 출력중");
    }
    void payment(){
        System.out.println("결제하기");
    }
}
