package com.example.ch_project_fx;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PurchaseDAO {
    public boolean recordPurchase(String userId, String isbn, int amount) {
        String checkSql = "SELECT * FROM purchases WHERE user_id = ? AND isbn = ?";
        String insertSql = "INSERT INTO purchases (user_id, isbn, amount) VALUES (?, ?, ?)";
        String updateSql = "UPDATE purchases SET amount = amount + ? WHERE user_id = ? AND isbn = ?";

        try (Connection conn = DBConnector.getConnection()) {
            // 1. 해당 유저와 ISBN의 기존 구매 내역이 있는지 확인
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, userId);
                checkStmt.setString(2, isbn);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    // 2. 이미 구매 내역이 있다면 amount를 업데이트
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setInt(1, amount);
                        updateStmt.setString(2, userId);
                        updateStmt.setString(3, isbn);
                        int result = updateStmt.executeUpdate();
                        return result > 0;
                    }
                } else {
                    // 3. 구매 내역이 없다면 새 레코드를 삽입
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                        insertStmt.setString(1, userId);
                        insertStmt.setString(2, isbn);
                        insertStmt.setInt(3, amount);
                        int result = insertStmt.executeUpdate();
                        return result > 0;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Book> getUserPurchaseHistory(String userId) {
        List<Book> purchasedBooks = new ArrayList<>();
        String sql = "SELECT b.*, p.amount AS userAmount " +
                "FROM purchases p JOIN book b ON p.isbn = b.isbn " +
                "WHERE p.user_id = ?";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Book book = new Book(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getInt("date"),
                        rs.getString("author"),
                        rs.getString("description"),
                        rs.getString("category"),
                        rs.getString("publishDate"),
                        rs.getInt("userAmount"), // 여기만 user가 구매한 amount
                        rs.getString("publisher"),
                        rs.getInt("price"),
                        rs.getString("imgPath")
                );
                purchasedBooks.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return purchasedBooks;
    }
}
