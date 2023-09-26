import lombok.Builder;

import java.util.Objects;

@Builder
public class Book {
    private final int bookId;
    private String title;
    private Author author;
    private int yearOfPublication;
    private final String ISBN;
    private boolean available;


    public Book(int bookId, String title, Author author, int yearOfPublication, String ISBN) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
        this.ISBN = ISBN;
        this.available = true;
    }
    public Book(int bookId, String title, Author author, int yearOfPublication, String ISBN, boolean available) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
        this.ISBN = ISBN;
        this.available = available;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public int getBookId() {
        return bookId;
    }

    public Author getAuthor() {
        return author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public String getTitle() {
        return title;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public String getISBN() {
        return ISBN;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void displayAll() {
        System.out.printf("""
                Titre: %s
                Auteur: %s
                Année de publication: %d
                ISBN: %s
                Disponible: %b
                %n""", title, author, yearOfPublication, ISBN, available);
    }

    @Override
    public String toString() {
        return ISBN + " / " + title + " de " + author.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return Objects.equals(ISBN, book.ISBN);
    }
}
