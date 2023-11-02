package base;

import java.time.LocalDate;
import java.util.Objects;

public class Book {
    private Long id;
    private String name;
    private String author;
    private Long isbn;
    private LocalDate publishedDate;

    public Book(Long id, String name, String author, Long isbn, LocalDate publishedDate) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.isbn = isbn;
        this.publishedDate = publishedDate;
    }

    public Book() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    @Override
    public int hashCode() {
//        return Objects.hash(name, author, isbn);
        return this.name.hashCode() + this.author.hashCode() + (int)(long)this.isbn;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Book) {
            Book book = (Book) obj;
            if (this.getName().equals(book.getName())) {
                if (this.getAuthor().equals(book.getAuthor())) {
                    if (this.getIsbn().equals(book.getIsbn())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("[")
            .append(getId())
            .append(", ")
            .append(getName())
            .append(", ")
            .append(getAuthor())
            .append(", ")
            .append(getIsbn())
            .append(", ")
            .append(getPublishedDate());

        if (this instanceof EBook) {
            sb.append(", ")
                .append(((EBook) this).getFileSize())
                .append("mb");
        } else if (this instanceof AudioBook) {
            sb.append(", ")
                    .append(((AudioBook) this).getFileSize())
                    .append("mb")
                    .append(", ")
                    .append(((AudioBook) this).getLanguage())
                    .append(", ")
                    .append(((AudioBook) this).getPlayTime())
                    .append("초");
        }

        sb.append("]");
        return sb.toString();
    }
}
