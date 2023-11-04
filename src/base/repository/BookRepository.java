package base.repository;

import base.book.Book;

import java.util.List;

public interface BookRepository {
    void init();
    boolean save(List<Book> books);
    List<Book> load();
    void backUp(List<Book> books);
}
