import lombok.Data;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

// TODO: 26/09/2023 Impossible d'ajouter, modifier un livre, si l'auteur n'existe pas, en créer un, n° ISBN ne s'affiche pas quand on displayAll les informations d'un livre
@Data
public class Library {
    private ArrayList<Book> bookList = new ArrayList<>();
    private ArrayList<User> userList = new ArrayList<>();
    private ArrayList<Borrow> borrowsHistoric = new ArrayList<>();
    private ArrayList<Author> authorList = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);

    public void addBook(Book book, User user) {
        verifyRole(user, Role.ADMIN);
        String sql = "insert into book (bookid, name, publishingyear, isbn, available, authorid, libraryid) values (DEFAULT,?,?,?,true,?,1)";
        try (
                Connection connection = ConnectionFactory.createConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ){
            statement.setString(1,book.getTitle());
            statement.setInt(2,book.getYearOfPublication());
            statement.setString(3, book.getISBN());
            statement.setInt(4,book.getAuthor());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Livre non ajouté", e);
        }
    }
    public void deleteBook(Book book, User user) {
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
    public void modifyBook(Book originalBook, Book updatedBook, User user) {
        verifyRole(user, Role.ADMIN);
        String sql = "update book set name = ?, publishingyear = ?, isbn = ?, authorid = ? where bookid = ?";
        try (
                Connection connection = ConnectionFactory.createConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ){
            statement.setString(1, updatedBook.getTitle());
            statement.setInt(2, updatedBook.getYearOfPublication());
            statement.setString(3, updatedBook.getISBN());
            statement.setInt(4, updatedBook.getAuthor());
            statement.setInt(5, originalBook.getBookId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Livre non modifié", e);
        }
    }
    public void displayBooks() {
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
    public void displayAvailableBooks() {
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
    public void borrowBook(Book book, User user) {
        String sql = "insert into borrow (borrowid, borrowdate, returndate, userid, bookid) values (default,?,null,?,?);" +
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
    public void returnBook(Book book, User user) {
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
    public void verifyRole(User user, Role role) {
        if (user.getRole() != role) {
            throw new SecurityException("Action non autorisée: l'utilisateur doit être " + role);
        }
    }

    public Book findByISBN(String ISBN) {
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
    public int getAuthorIdByName(String name) {
        String sql = "select authorid from author where name = ?";
        try (
                Connection connection = ConnectionFactory.createConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
        ){
            statement.setString(1,name);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException("Mauvais auteur", e);
        }
    }

    public User connect(String login, String password) {
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

    public Book encodeBook() {
        System.out.println("Titre: ");
        String title = sc.nextLine();
        System.out.println("Auteur: ");
        String author = sc.nextLine();
        System.out.println("Année de publication: ");
        int year = Integer.parseInt(sc.nextLine());
        System.out.println("ISBN: ");
        String ISBN = sc.nextLine();
        return Book.builder()
                .bookId(-1) //id n'est jamais utilisé après pour un livre encodé
                .authorId(getAuthorIdByName(author))
                .title(title)
                .yearOfPublication(year)
                .ISBN(ISBN)
                .available(true)
                .build();
    }

    public boolean start() {

        String connect = """
                *********************
                *   Bibliothèque    *
                *********************
                login:""";
        String menuAdmin = """
                *********************
                *   Bibliothèque    *
                *********************
                Admin mode
                1. Afficher les livres disponibles
                2. Afficher les informations d'un livre
                3. Emprunter un livre
                4. Retourner un livre
                5. Ajouter un livre
                6. Modifier un livre
                7. Supprimer un livre
                
                0. Quitter
                """;
        String menu = """
                *********************
                *   Bibliothèque    *
                *********************
                1. Afficher les livres disponibles
                2. Afficher les informations d'un livre
                3. Emprunter un livre
                4. Retourner un livre
                
                0. Quitter
                """;
        User user;
        try {
            String login, password;
            System.out.println(connect);
            login = sc.nextLine();
            System.out.println("password: ");
            password = sc.nextLine();
            user = connect(login,password);
        }catch (Exception e) {
            System.out.println(e);
            return false;
        }

        if (user.getRole().equals(Role.ADMIN)) {
            menu = menuAdmin;
        }

        int choice;
        while (true) {
            try {
                System.out.println(menu);
                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 0:
                        return true;
                    case 1:
                        displayAvailableBooks();
                        break;
                    case 2:
                        displayBooks();
                        System.out.println("Choisir par ISBN: ");
                        findByISBN(sc.nextLine()).displayAll();
                        break;
                    case 3:
                        displayAvailableBooks();
                        System.out.println("Quel livre emprunter (ISBN)?");
                        borrowBook(findByISBN(sc.nextLine()), user);
                        break;
                    case 4:
                        displayBooks();
                        System.out.println("Quel livre souhaitez-vous rendre");
                        returnBook(findByISBN(sc.nextLine()), user);
                        break;
                    case 5:
                        addBook(encodeBook(), user);
                        break;
                    case 6:
                        displayBooks();
                        System.out.println("Quel livre modifier ? : ");
                        modifyBook(findByISBN(sc.nextLine()), encodeBook(), user);
                        break;
                    case 7:
                        displayBooks();
                        System.out.println("Quel livre supprimer ? : ");
                        deleteBook(findByISBN(sc.nextLine()), user);
                }
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
}
