package classes;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Builder
public class Book {
    @Getter
    private final int bookId;
    @Getter
    private String title;
    @Getter
    private int authorId;
    @Getter
    private int yearOfPublication;
    @Getter
    private final String ISBN;
    private boolean available;

    /**
     * Display all information about a book, including title, author, year of publication,
     * ISBN, and availability.
     */
    public void displayAll() {
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
        return ISBN + " / " + title + " de " + Queries.getAuthorNameById(authorId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return Objects.equals(ISBN, book.ISBN);
    }
}
