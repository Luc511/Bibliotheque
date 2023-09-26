import lombok.Builder;

import java.time.LocalDate;
@Builder
public class Borrow {
    private final int borrowId;
    private final Book book;
    private final User user;
    private final LocalDate startDate;
    private LocalDate endDate;

    public Borrow(int borrowId, Book book, User user) {
        this.borrowId = borrowId;
        this.book = book;
        this.user = user;
        startDate = LocalDate.now();
    }

    public Borrow(int borrowId, Book book, User user, LocalDate startDate, LocalDate endDate) {
        this.borrowId = borrowId;
        this.book = book;
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
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
