package com.example.ch_project_fx;

import javafx.scene.image.Image;

import java.util.Objects;

public class Book {
    String isbn;
    String title;
    int date;
    String author;
    String description;
    String category;
    String publishDate;
    int amount;
    String publisher;
    int price;
    String imgPath;
    Image image;

    public Book(String isbn, String title, int date, String author, String description, String category, String publishDate, int amount, String publisher, int price, String imgPath) {
        this.isbn = isbn;
        this.title = title;
        this.date = date;
        this.author = author;
        this.description = description;
        this.category = category;
        this.publishDate = publishDate;
        this.amount = amount;
        this.publisher = publisher;
        this.price = price;
        this.imgPath = imgPath;
        this.image = new Image(Objects.requireNonNull(getClass().getResource(imgPath)).toExternalForm());
    }
    public Book CopyBookForCart(Book b){
        Book newBook = new Book(b.getIsbn(),b.title,b.getDate(),b.getAuthor(),b.getDescription(),b.getCategory(),b.getPublishDate(),b.getAmount(),b.getPublisher(),b.getPrice(),b.getImgPath());
        newBook.amount = 1;
        return newBook;
    }
    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public int getDate() {
        return date;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public int getAmount() {
        return amount;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getPrice() {
        return price;
    }
    public String getImgPath(){
        return imgPath;
    }
    public Image getImage(){
        return image;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
