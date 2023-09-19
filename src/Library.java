import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> bookList = new ArrayList<>();
    private List<User> userList = new ArrayList<>();
    private List<Borrow> borrowsHistoric = new ArrayList<>();


    public List<Borrow> getBorrowsHistoric() {
        return borrowsHistoric;
    }

    public void setBorrowsHistoric(List<Borrow> borrowsHistoric) {
        this.borrowsHistoric = borrowsHistoric;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public void addBook(Book book, User user) {

    }
    public void deleteBook(Book book, User user) {

    }
    public void modifyBook(Book book, User user) {

    }
    public void displayAvailableBooks() {

    }
    public void searchBook(Book book) {

    }


    public void verifyRole(User user, Role role) {
        if (user.getRole() != role) {
            throw new SecurityException("Action non autorisée: l'utilisateur doit être " + role);
        }
    }
}
