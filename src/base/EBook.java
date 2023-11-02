package base;

import java.time.LocalDate;
import java.util.Objects;

public class EBook extends Book {
    String fileSize;

    public EBook(Long id, String name, String author, Long isbn, LocalDate publishedDate, String fileSize) {
        super(id, name, author, isbn, publishedDate);
        this.fileSize = fileSize;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EBook) {
            Book book = (Book) obj;
            if (this.getName().equals(book.getName()) &&
                    this.getAuthor().equals(book.getAuthor()) &&
                    this.getIsbn().equals(book.getIsbn())) {
                return true;
            }
        }
        return false;
    }
}
