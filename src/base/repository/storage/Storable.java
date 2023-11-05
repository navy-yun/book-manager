package base.repository.storage;

import base.book.Book;

import java.util.List;

public interface Storable {
    void init();
    boolean save(List<Book> books);
    List<Book> load();
    void backUp();
}
