package classes;

import lombok.Builder;
import lombok.Getter;

import java.sql.SQLException;
import java.util.Objects;

@Builder
@Getter
public class Book {
    private final int bookId;
    private String title;
    private int authorId;
    private int yearOfPublication;
    private final String ISBN;
    private boolean available;

    /**
     * Display all information about a book, including title, author, year of publication,
     * ISBN, and availability.
     */
    public void displayAll() throws SQLException {
        System.out.printf("""
                Titre: %s
                Auteur: %s
                Année de publication: %d
                ISBN: %s
                Disponible: %b
                %n""", title, Queries.getAuthorNameById(authorId), yearOfPublication, ISBN, available);
    }

    @Override
    public String toString() {
        try {
            return ISBN + " / " + title + " de " + Queries.getAuthorNameById(authorId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return Objects.equals(ISBN, book.ISBN);
    }
}
