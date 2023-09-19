import java.util.Objects;

public class Book {
    private String title;
    private String author;
    private int yearOfPublication;
    private final String ISBN;
    private boolean available;

    public Book(String title, String author, int yearOfPublication, String ISBN) {
        this.title = title;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
        this.ISBN = ISBN;
        available = true;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
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
        return ISBN + " / " + title + " de " + author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return Objects.equals(ISBN, book.ISBN);
    }
}
