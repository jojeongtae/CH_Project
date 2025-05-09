package com.example.ch_project_fx;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAO {
public void saveUserToDB(User user) {
    String sql = "INSERT INTO users (id, pw, name, phone, address, imgPath, img, point, grade, totalPayed) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try (Connection conn = DBConnector.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, user.getId());
        pstmt.setString(2, user.getPw());
        pstmt.setString(3, user.getName());
        pstmt.setString(4, user.getPhone());
        pstmt.setString(5, user.getAddress());
        pstmt.setString(6, user.getImgPath());
        pstmt.setInt(8, user.getPoint());
        pstmt.setString(9, user.getGrade());
        pstmt.setInt(10, user.getTotalPayed());

        pstmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    public User login(String id, String pw) {
        String sql = "SELECT * FROM users WHERE id = ? AND pw = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            pstmt.setString(2, pw);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // DB에서 유저 정보 가져와서 User 객체 생성
                User user = new User();
                user.setId(rs.getString("id"));
                user.setPw(rs.getString("pw"));
                user.setName(rs.getString("name"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setImgPath(rs.getString("imgPath"));
                user.setImg(new Image(getClass().getResource(user.getImgPath()).toExternalForm()));
                user.setPoint(rs.getInt("point"));
                user.setGrade(rs.getString("grade"));
                user.setTotalPayed(rs.getInt("totalPayed"));

                // user_id가 String이므로 그대로 넘기면 됨
                CouponDAO couponDAO = new CouponDAO();
                List<Coupon> coupons = couponDAO.getUserCoupons(user.getId());
                user.setCoupons(coupons);

                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // 로그인 실패
    }
    public void updateUserAfterPurchase(String userId, int purchaseAmount) {
        String selectSql = "SELECT totalPayed, grade, point FROM users WHERE id = ?";
        String updateSql = "UPDATE users SET totalPayed = ?, grade = ?, point = ? WHERE id = ?";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {

            selectStmt.setString(1, userId);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                int currentTotal = rs.getInt("totalPayed");
                String currentGrade = rs.getString("grade");
                int currentPoint = rs.getInt("point");

                int newTotal = currentTotal + purchaseAmount;
                int newPoint = currentPoint + (int)(purchaseAmount * 0.1); // 10% 포인트 적립

                // 새 등급 계산
                String newGrade;
                if (newTotal >= 300000) {
                    newGrade = "vip";
                } else if (newTotal >= 100000) {
                    newGrade = "gold";
                } else if (newTotal >= 50000) {
                    newGrade = "silver";
                } else {
                    newGrade = "normal";
                }

                // DB 업데이트
                try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                    updateStmt.setInt(1, newTotal);
                    updateStmt.setString(2, newGrade);
                    updateStmt.setInt(3, newPoint);
                    updateStmt.setString(4, userId);
                    updateStmt.executeUpdate();
                }

                // 등급이 바뀌었으면 알림
                if (!currentGrade.equals(newGrade)) {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("등급 상승");
                        alert.setHeaderText("축하합니다!");
                        alert.setContentText("회원님의 등급이 '" + currentGrade + "'에서 '" + newGrade + "'로 상승했습니다!");
                        alert.showAndWait();
                    });
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean addPointToUser(String userId, int pointToAdd) {
        String sql = "UPDATE users SET point = point + ? WHERE id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, pointToAdd);
            pstmt.setString(2, userId);

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean subtractPointFromUser(String userId, int pointToSubtract) {
        String sql = "UPDATE users SET point = point - ? WHERE id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, pointToSubtract);
            pstmt.setString(2, userId);

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
