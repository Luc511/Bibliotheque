import lombok.Builder;
import lombok.Data;

/**
 * The Author class represents an author of a book.
 *
 * This class provides a way to store and retrieve information about an author,
 * such as their ID and name.
 *
 * int id = author.getIdAuthor();
 * String name = author.getName();
 *
 * Note: This class uses the Lombok library to generate getters and builders.
 */
@Data
@Builder
public class Author {
    private final int idAuthor;
    private final String name;
}
