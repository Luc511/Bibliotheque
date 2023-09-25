import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Requetes {
    public static void initUsers(ArrayList<User> users) {
        String sql = "select * from user_";
        try (
                Connection connection = ConnectionFactory.createConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql)
                ){
            while (rs.next()) {
                users.add(
                        User.builder()
                                .userId(rs.getInt(1))
                                .firstName(rs.getString(2))
                                .lastName(rs.getString(3))
                                .birthdate(rs.getDate(4).toLocalDate())
                                .login(rs.getString(5))
                                .password(rs.getString(6))
                                .role(Role.valueOf(rs.getString(7).toUpperCase()))
                                .addressId(rs.getInt(8))
                                .build()
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void initBooks(ArrayList<Book> books) {
        String sql = "select * from book";
        try (
                Connection connection = ConnectionFactory.createConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql)
        ){
            while (rs.next()) {
                books.add(
                        Book.builder()
                                .bookId(rs.getInt(1))
                                .title(rs.getString(2))
                                .yearOfPublication(rs.getInt(3))
                                .ISBN(rs.getString(4))
                                .author(rs.getInt(5)) //rechercher author dans la liste qu'on ajoutera en paramètre
                                .build()
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void initBorrow(ArrayList<Borrow> borrows) {

    }

    // TODO: 25/09/2023 Appeler cette fonction avant initBooks
    public static void initAuthors(ArrayList<Author> authors) {

    }
}
