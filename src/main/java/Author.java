import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Author {
    private final int idAuthor;
    private final String name;
}
