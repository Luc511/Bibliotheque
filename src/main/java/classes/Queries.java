package classes;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Queries {
    public static boolean addBook(Book book, User user) throws SQLException {
        verifyRole(user, Role.ADMIN);
        String sql = "insert into book (name, publishingyear, isbn, available, authorid, libraryid) values (?,?,?,true,?,1)";
        DriverManager.registerDriver(new org.postgresql.Driver());
        try (
                Connection connection = ConnectionFactory.createConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ){
            statement.setString(1,book.getTitle());
            statement.setInt(2,book.getYearOfPublication());
            statement.setString(3, book.getISBN());
            statement.setInt(4,book.getAuthorId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Livre non ajouté", e);
        }
    }
    public static boolean deleteBook(Book book, User user) throws SQLException {
        verifyRole(user, Role.ADMIN);
        String sql = "delete from book where bookid = ?";
        DriverManager.registerDriver(new org.postgresql.Driver());
        try (
                Connection connection = ConnectionFactory.createConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ){
            statement.setInt(1,book.getBookId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Livre non supprimé", e);
        }
    }
    public static void modifyBook(Book originalBook, Book updatedBook, User user) throws SQLException {
        verifyRole(user, Role.ADMIN);
        String sql = "update book set name = ?, publishingyear = ?, isbn = ?, authorid = ? where bookid = ?";
        DriverManager.registerDriver(new org.postgresql.Driver());
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
    public static ArrayList<Book> displayBooks() throws SQLException {
        String sql = "select bookid, name, authorid, publishingyear, available, isbn from book";
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
    public static ArrayList<Book> displayAvailableBooks() throws SQLException {
        String sql = "select bookid, name, authorid, publishingyear, available, isbn from book where available = true";
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
    public static boolean borrowBook(Book book, User user) throws SQLException {
        String sql = "insert into borrow (borrowdate, returndate, userid, bookid) values (?,null,?,?);" +
                "update book set available = false where bookid = ?";
        DriverManager.registerDriver(new org.postgresql.Driver());
        try (
                Connection connection = ConnectionFactory.createConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ){
            statement.setDate(1, Date.valueOf(LocalDate.now()));
            statement.setInt(2, user.getUserId());
            statement.setInt(3, book.getBookId());
            statement.setInt(4, book.getBookId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Emprunt impossible", e);
        }
    }

    /**
     * supprime complètement le borrow..
     * @param book
     * @param user
     * @return
     * @throws SQLException
     */
    public static boolean returnBook(Book book, User user) throws SQLException {
        String sql = "delete from borrow where bookid = ? and userid = ?;" +
                "update book set available = true where bookid = ?";
        DriverManager.registerDriver(new org.postgresql.Driver());
        try (
                Connection connection = ConnectionFactory.createConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ){
            statement.setInt(1, book.getBookId());
            statement.setInt(2, user.getUserId());
            statement.setInt(3, book.getBookId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Emprunt inexistant", e);
        }
    }
    public static void verifyRole(User user, Role role) {
        if (user.getRole() != role) {
            throw new SecurityException("Action non autorisée: l'utilisateur doit être " + role);
        }
    }
    public static Book findByISBN(String ISBN) throws SQLException {
        String sql = "select bookid, name, authorid, publishingyear, available from book where isbn like(?)";
        DriverManager.registerDriver(new org.postgresql.Driver());
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
    public static String getAuthorNameById(int id) throws SQLException {
        String sql = "select name from author where authorid = ?";
        DriverManager.registerDriver(new org.postgresql.Driver());
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

    /**
     * This method retrieves the ID of an author using their name.
     *
     * It first checks if the author already exists in the database. If so, it retrieves
     * and returns its ID. If the author does not exist, it creates them and recursively calls this method
     * to get and return the newly created ID.
     *
     * @param name The name of the author for whom to get the ID.
     * @return The ID of the author from the database.
     * @throws RuntimeException If an SQL error occurs while executing the query or creating the author.
     */
    public static int getAuthorIdByName(String name) throws SQLException {
        String sql = "select authorid from author where name = ?";
        DriverManager.registerDriver(new org.postgresql.Driver());
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
    public static boolean authorExist(String authorName) throws SQLException {
        String sql = "SELECT COUNT(*) FROM author WHERE name = ?";
        DriverManager.registerDriver(new org.postgresql.Driver());
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
    public static void createAuthor(String authorName) throws SQLException {
        String sql = "insert into author (name) values (?)";
        DriverManager.registerDriver(new org.postgresql.Driver());
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
    public static User connect(String login, String password) throws SQLException {
        String sql = "select userid, firstname, lastname, birthdate, login, password, userrole, addressid from user_ where login = ? and password = ?";
        DriverManager.registerDriver(new org.postgresql.Driver());
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