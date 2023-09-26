import lombok.Builder;

import java.time.LocalDate;

@Builder
public class User {
    private final int userId;
    private final String firstName;
    private final String lastName;
    private String login;
    private String password;
    private final Role role;
    private final LocalDate birthdate;
    private final int addressId;

    public User(int userId, String firstName, String lastName, String login, String password, Role role, LocalDate birthdate, int addressId) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.role = role;
        this.birthdate = birthdate;
        this.addressId = addressId;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public int getAddressId() {
        return addressId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }
}
