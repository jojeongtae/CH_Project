package com.example.ch_project_fx;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    // 책 추가
    public void saveBookToDB(Book book) {
        String sql = "INSERT INTO book (isbn, title, date, author, description, category, publishDate, amount, publisher, price, imgPath) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getIsbn());
            pstmt.setString(2, book.getTitle());
            pstmt.setInt(3, book.getDate());
            pstmt.setString(4, book.getAuthor());
            pstmt.setString(5, book.getDescription());
            pstmt.setString(6, book.getCategory());
            pstmt.setString(7, book.getPublishDate());
            pstmt.setInt(8, book.getAmount());
            pstmt.setString(9, book.getPublisher());
            pstmt.setInt(10, book.getPrice());
            pstmt.setString(11, book.getImgPath());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 책 타이틀로 검색 (contains)
    public List<Book> searchBooksByTitle(String keyword) {
        String sql = "SELECT * FROM book WHERE title LIKE ?";
        List<Book> books = new ArrayList<>();

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Book book = mapResultSetToBook(rs);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    // 책 저자로 검색
    public List<Book> searchBooksByAuthor(String author) {
        String sql = "SELECT * FROM book WHERE author LIKE ?";
        List<Book> books = new ArrayList<>();

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + author + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Book book = mapResultSetToBook(rs);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    // 책 카테고리로 검색
    public List<Book> searchBooksByCategory(String category) {
        String sql = "SELECT * FROM book WHERE category LIKE ?";
        List<Book> books = new ArrayList<>();

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + category + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Book book = mapResultSetToBook(rs);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    // 가격순 정렬
    public List<Book> getBooksSortedByPrice() {
        String sql = "SELECT * FROM book ORDER BY price";
        List<Book> books = new ArrayList<>();

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Book book = mapResultSetToBook(rs);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    // ResultSet을 Book 객체로 변환
    private Book mapResultSetToBook(ResultSet rs) throws SQLException {
        String isbn = rs.getString("isbn");
        String title = rs.getString("title");
        int date = rs.getInt("date");
        String author = rs.getString("author");
        String description = rs.getString("description");
        String category = rs.getString("category");
        String publishDate = rs.getString("publishDate");
        int amount = rs.getInt("amount");
        String publisher = rs.getString("publisher");
        int price = rs.getInt("price");
        String imgPath = rs.getString("imgPath");

        // Book 객체는 이미지 경로로 이미지 생성 이미 처리하므로 추가로 Image 객체 생성은 필요하지 않음
        return new Book(isbn, title, date, author, description, category, publishDate, amount, publisher, price, imgPath);
    }
    public List<Book> getAllBooksFromDB() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM book";  // 책 테이블에서 모든 책을 가져오는 쿼리

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // 각 책 정보 추출
                String isbn = rs.getString("isbn");
                String title = rs.getString("title");
                int date = rs.getInt("date");
                String author = rs.getString("author");
                String description = rs.getString("description");
                String category = rs.getString("category");
                String publishDate = rs.getString("publishDate");
                int amount = rs.getInt("amount");
                String publisher = rs.getString("publisher");
                int price = rs.getInt("price");
                String imgPath = rs.getString("imgPath");
                // Book 객체 생성하여 리스트에 추가
                Book book = new Book(isbn, title, date, author, description, category, publishDate, amount, publisher, price, imgPath);
                books.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books; // 모든 책 리스트 반환
    }
    public void increaseBookAmount(String isbn, int amountToAdd) {
        String sql = "UPDATE book SET amount = amount + ? WHERE isbn = ?";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, amountToAdd);
            pstmt.setString(2, isbn);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }public List<Book> getBorrowedBooks(String userId) {
        List<Book> borrowedBooks = new ArrayList<>();
        String sql = "SELECT b.* FROM borrows br JOIN book b ON br.isbn = b.isbn WHERE br.user_id = ?";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Book book = new Book(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getInt("date"), // 출판 날짜가 int로 저장되어 있다면
                        rs.getString("author"),
                        rs.getString("description"),
                        rs.getString("category"),
                        rs.getString("publishDate"),
                        rs.getInt("amount"),
                        rs.getString("publisher"),
                        rs.getInt("price"),
                        rs.getString("imgPath")
                );
                borrowedBooks.add(book); // borrowedBooks 리스트에 추가

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(borrowedBooks.isEmpty()){
            return null;
        }else{
        return borrowedBooks;
    }}
    public int calculateOverdueDays(String userId) throws SQLException {
        String query = "SELECT due_date FROM borrows WHERE user_id = ? AND due_date < CURDATE()"; // 연체된 책만 조회
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            int overdueDays = 0;
            while (rs.next()) {
                Date dueDate = rs.getDate("due_date");
                if (dueDate != null) {
                    // 현재 날짜와 due_date의 차이를 구함
                    LocalDate dueLocalDate = dueDate.toLocalDate();
                    LocalDate currentDate = LocalDate.now();
                    long daysBetween = ChronoUnit.DAYS.between(dueLocalDate, currentDate);
                    if (daysBetween > 0) {
                        overdueDays += daysBetween; // 연체일수 합산
                    }
                }
            }
            return overdueDays;
        }
    }
    public void insertBorrow(String userId, String isbn) {
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO borrows (user_id, isbn, borrow_date, due_date, remaining_days) " +
                             "VALUES (?, ?, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), 7)"
             )) {

            pstmt.setString(1, userId);
            pstmt.setString(2, isbn);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

