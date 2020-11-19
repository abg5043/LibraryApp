import java.time.LocalDate;

public class Book extends Asset {

    public Book(String bookName) {
        super(bookName);
    }

    public Book(String bookName, String renter) {
        super(bookName, renter);
    }

    public Book(String assetName, String renter, Boolean isRented, LocalDate dateRented, LocalDate dateDue) {
        super(assetName, renter, isRented, dateRented, dateDue);
    }

    @Override
    public String toString() {
        return "Book";
    }
}


