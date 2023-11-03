package base.repository;

import base.book.Book;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public interface BookRepository {
    boolean addBook(Book book);
    Book getBook(Long id);
    List<Book> getBooks();
    List<Book> getBooks(Predicate<Book> predicate);
    List<Book> getBooks(Comparator<Book> comparator);
    boolean setBook(Book book);
    boolean removeBook(Book book);
}
