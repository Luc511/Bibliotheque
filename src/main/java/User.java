import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
public class User {
    @Getter
    private final int userId;
    private final String firstName;
    private final String lastName;
    private String login;
    private String password;
    @Getter
    private final Role role;
    private final LocalDate birthdate;
    private final int addressId;
}
