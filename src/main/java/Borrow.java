import java.time.LocalDate;

public class Borrow {
    private final Book book;
    private final User user;
    private final LocalDate startDate;
    private LocalDate endDate;

    public Borrow(Book book, User user) {
        this.book = book;
        this.user = user;
        startDate = LocalDate.now();
    }

    public Book getBook() {
        return book;
    }

    public User getUser() {
        return user;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
