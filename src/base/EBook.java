package base;

import java.time.LocalDate;

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
}
