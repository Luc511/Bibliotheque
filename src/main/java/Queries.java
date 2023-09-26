import java.sql.*;
import java.time.LocalDate;

public class Queries {
    public static void addBook(Book book, User user) {
        verifyRole(user, Role.ADMIN);
        String sql = "insert into book (name, publishingyear, isbn, available, authorid, libraryid) values (?,?,?,true,?,1)";
        try (
                Connection connection = ConnectionFactory.createConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ){
            statement.setString(1,book.getTitle());
            statement.setInt(2,book.getYearOfPublication());
            statement.setString(3, book.getISBN());
            statement.setInt(4,book.getAuthorId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Livre non ajouté", e);
        }
    }
    public static void deleteBook(Book book, User user) {
        verifyRole(user, Role.ADMIN);
        String sql = "delete from book where bookid = ?";
        try (
                Connection connection = ConnectionFactory.createConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ){
            statement.setInt(1,book.getBookId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Livre non supprimé", e);
        }
    }
    public static void modifyBook(Book originalBook, Book updatedBook, User user) {
        verifyRole(user, Role.ADMIN);
        String sql = "update book set name = ?, publishingyear = ?, isbn = ?, authorid = ? where bookid = ?";
        try (
                Connection connection = ConnectionFactory.createConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ){
            statement.setString(1, updatedBook.getTitle());
            statement.setInt(2, updatedBook.getYearOfPublication());
            statement.setString(3, updatedBook.getISBN());
            statement.setInt(4, updatedBook.getAuthorId());
            statement.setInt(5, originalBook.getBookId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Livre non modifié", e);
        }
    }
    public static void displayBooks() {
        String sql = "select isbn, b.name, a.name from book b join public.author a on a.authorid = b.authorid";
        try (
                Connection connection = ConnectionFactory.createConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql)
        ){
            while (rs.next()) {
                System.out.println(rs.getString(1) + " / " + rs.getString(2) + " de " + rs.getString(3));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void displayAvailableBooks() {
        String sql = "select isbn, b.name, a.name from book b join public.author a on a.authorid = b.authorid where available = true";
        try (
                Connection connection = ConnectionFactory.createConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql)
        ){
            while (rs.next()) {
                System.out.println(rs.getString(1) + " / " + rs.getString(2) + " de " + rs.getString(3));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void borrowBook(Book book, User user) {
        String sql = "insert into borrow (borrowdate, returndate, userid, bookid) values (?,null,?,?);" +
                "update book set available = false where bookid = ?";
        try (
                Connection connection = ConnectionFactory.createConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ){
            statement.setDate(1, Date.valueOf(LocalDate.now()));
            statement.setInt(2, user.getUserId());
            statement.setInt(3, book.getBookId());
            statement.setInt(4, book.getBookId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Emprunt impossible", e);
        }
    }
    public static void returnBook(Book book, User user) {
        String sql = "update borrow set returndate = ? where bookid = ? and userid = ?;" +
                "update book set available = true where bookid = ?";
        try (
                Connection connection = ConnectionFactory.createConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ){
            statement.setDate(1, Date.valueOf(LocalDate.now()));
            statement.setInt(2, book.getBookId());
            statement.setInt(3, user.getUserId());
            statement.setInt(4, book.getBookId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Emprunt inexistant", e);
        }
    }
    public static void verifyRole(User user, Role role) {
        if (user.getRole() != role) {
            throw new SecurityException("Action non autorisée: l'utilisateur doit être " + role);
        }
    }
    public static Book findByISBN(String ISBN) {
        String sql = "select bookid, name, authorid, publishingyear, available from book where isbn like(?)";
        try (
                Connection connection = ConnectionFactory.createConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ){
            statement.setString(1,ISBN);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return Book.builder()
                    .bookId(rs.getInt(1))
                    .title(rs.getString(2))
                    .authorId(rs.getInt(3))
                    .yearOfPublication(rs.getInt(4))
                    .available(rs.getBoolean(5))
                    .ISBN(ISBN)
                    .build();

        } catch (SQLException e) {
            throw new RuntimeException("Livre introuvable", e);
        }
    }
    public static String getAuthorNameById(int id) {
        String sql = "select name from author where authorid = ?";
        try (
                Connection connection = ConnectionFactory.createConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ){
            statement.setInt(1,id);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getString(1);

        } catch (SQLException e) {
            throw new RuntimeException("Mauvais auteur", e);
        }
    }
    public static int getAuthorIdByName(String name) {
        String sql = "select authorid from author where name = ?";
        try (
                Connection connection = ConnectionFactory.createConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ){
            if (authorExist(name)) {
                statement.setString(1,name);
                ResultSet rs = statement.executeQuery();
                rs.next();
                return rs.getInt(1);
            } else {
                createAuthor(name);
                return getAuthorIdByName(name);
            }


        } catch (SQLException e) {
            throw new RuntimeException("Mauvais auteur", e);
        }
    }
    public static boolean authorExist(String authorName) {
        String sql = "SELECT COUNT(*) FROM author WHERE name = ?";
        try (Connection conn = ConnectionFactory.createConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, authorName);
            ResultSet rs = statement.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public static void createAuthor(String authorName) {
        String sql = "insert into author (name) values (?)";
        try (
                Connection connection = ConnectionFactory.createConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ){
            statement.setString(1,authorName);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Livre non ajouté", e);
        }
    }
    public static User connect(String login, String password) {
        String sql = "select userid, firstname, lastname, birthdate, login, password, userrole, addressid from user_ where login = ? and password = ?";
        try (
                Connection connection = ConnectionFactory.createConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ){
            statement.setString(1,login);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return User.builder()
                    .userId(rs.getInt(1))
                    .firstName(rs.getString(2))
                    .lastName(rs.getString(3))
                    .birthdate(rs.getDate(4).toLocalDate())
                    .login(rs.getString(5))
                    .password(rs.getString(6))
                    .role(Role.valueOf(rs.getString(7).toUpperCase()))
                    .addressId(rs.getInt(8))
                    .build();

        } catch (SQLException e) {
            throw new RuntimeException("Identifiant ou mot de passe incorrect", e);
        }
    }
}
