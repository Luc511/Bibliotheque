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
    public static void initBooks(ArrayList<Book> books, ArrayList<Author> authors) {
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
                                .available(rs.getBoolean(5))
                                .author(authors.stream().filter(author -> {
                                    try {
                                        return author.getIdAuthor() == rs.getInt(6);
                                    } catch (SQLException e) {
                                        throw new RuntimeException(e);
                                    }
                                }).findFirst().orElseThrow(() -> new IllegalArgumentException("Author not found")))
                                .build()
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void initBorrow(ArrayList<Borrow> borrows, ArrayList<Book> books, ArrayList<User> users) {
        String sql = "select * from borrow";
        try (
                Connection connection = ConnectionFactory.createConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql)
        ){
            while (rs.next()) {
                var borrowBuilder = Borrow.builder()
                        .borrowId(rs.getInt(1))
                        .user(users.stream()
                                .filter(user -> {
                                    try {
                                        return user.getUserId() == rs.getInt(4);
                                    } catch (SQLException e) {
                                        throw new RuntimeException(e);
                                    }
                                })
                                .findFirst()
                                .orElseThrow(() -> new RuntimeException("User not found")))
                        .book(books.stream()
                                .filter(book -> {
                                    try {
                                        return book.getBookId() == rs.getInt(5);
                                    } catch (SQLException e) {
                                        throw new RuntimeException(e);
                                    }
                                })
                                .findFirst()
                                .orElseThrow(() -> new RuntimeException("Book not found")));

                var startDate = rs.getDate(2);
                if (startDate != null) {
                    borrowBuilder.startDate(startDate.toLocalDate());
                }

                var endDate = rs.getDate(3);
                if (endDate != null) {
                    borrowBuilder.endDate(endDate.toLocalDate());
                }

                borrows.add(borrowBuilder.build());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void initAuthors(ArrayList<Author> authors) {
        String sql = "select * from author";
        try (
                Connection connection = ConnectionFactory.createConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql)
        ){
            while (rs.next()) {
                authors.add(Author.builder()
                        .idAuthor(rs.getInt(1))
                        .name(rs.getString(2))
                        .build());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
