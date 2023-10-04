package classes;

import lombok.Builder;
import lombok.Getter;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

@Builder
public class User {
    @Getter
    private final int userId;
    @Getter
    private final String firstName;
    private final String lastName;
    private String login;
    private String password;
    @Getter
    private final Role role;
    private final LocalDate birthdate;
    private final int addressId;

    public ArrayList<Book> borrowedBooks() throws SQLException {
        String sql = "select b.bookid, name, authorid, publishingyear, available, isbn from book join public.borrow b on book.bookid = b.bookid join public.user_ u on u.userid = b.userid where u.userid = " + userId;
        DriverManager.registerDriver(new org.postgresql.Driver());
        try (
                Connection connection = ConnectionFactory.createConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql)
        ){
            ArrayList<Book> books = new ArrayList<>();
            while (rs.next()) {

                while (rs.next()) {
                    books.add(Book.builder()
                            .bookId(rs.getInt(1))
                            .title(rs.getString(2))
                            .authorId(rs.getInt(3))
                            .yearOfPublication(rs.getInt(4))
                            .available(rs.getBoolean(5))
                            .ISBN(rs.getString(6))
                            .build());
                }
            }
            return books;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
