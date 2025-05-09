package com.example.ch_project_fx;
import java.sql.*;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;

public class Coupon {
    private int id;
    private String name;
    private String imgPath;
    private int value;
    private Image image;
    public Coupon(int id, String name, String imgPath, int value) {
        this.id = id;
        this.name = name;
        this.imgPath = imgPath;
        this.value = value;
        this.image = new Image(getClass().getResource(imgPath).toExternalForm());
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getImgPath() { return imgPath; }
    public int getValue() { return value; }
    public Image getImage(){return image;}
}
class CouponDAO {

    private Connection conn;

    public CouponDAO() {
        try {
            conn = DBConnector.getConnection(); // 너가 만든 DB 연결 클래스 사용
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 전체 쿠폰 목록 가져오기
    public List<Coupon> getAllCoupons() {
        List<Coupon> coupons = new ArrayList<>();
        String sql = "SELECT * FROM coupons";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String imgPath = rs.getString("imgPath");
                int value = rs.getInt("value");
                coupons.add(new Coupon(id, name, imgPath, value));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return coupons;
    }

    // 유저에게 쿠폰 지급
    public boolean giveCouponToUser(String userId, int couponId) {
        String sql = "INSERT INTO user_coupons (user_id, coupon_id) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            ps.setInt(2, couponId);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 유저가 쿠폰 사용 → 한 개만 삭제
    public boolean useCoupon(String userId, int couponId) {
        String sql = "DELETE FROM user_coupons WHERE user_id = ? AND coupon_id = ? LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            ps.setInt(2, couponId);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    // 특정 유저가 보유 중인 쿠폰 리스트 반환
    public List<Coupon> getUserCoupons(String userId) {
        List<Coupon> coupons = new ArrayList<>();
        String sql = "SELECT c.id, c.name, c.imgPath, c.value " +
                "FROM user_coupons uc " +
                "JOIN coupons c ON uc.coupon_id = c.id " +
                "WHERE uc.user_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String imgPath = rs.getString("imgPath");
                    int value = rs.getInt("value");
                    coupons.add(new Coupon(id, name, imgPath, value));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return coupons;
    }
}