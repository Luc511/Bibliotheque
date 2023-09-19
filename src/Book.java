import java.util.Objects;

public class Book {
    private final String title;
    private final String author;
    private final int yearOfPublication;
    private final String ISBN;
    private boolean available;

    public Book(String title, String author, int yearOfPublication, String ISBN) {
        this.title = title;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
        this.ISBN = ISBN;
        available = true;
    }

    public void borrowBook(User user, Book book) {

    }
    public void returnBook(User user, Book book) {

    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
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

    @Override
    public String toString() {
        return title + " de " + author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return Objects.equals(ISBN, book.ISBN);
    }
}
