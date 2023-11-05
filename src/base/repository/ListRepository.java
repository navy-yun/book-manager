package base.repository;

import base.book.Book;
import base.repository.storage.FileStorage;
import base.repository.storage.Storable;

import java.util.*;
import java.util.function.Predicate;

public class ListRepository implements BookRepository {

    private Storable repo = new FileStorage();
    private List<Book> books = repo.load();

    @Override
    public boolean addBook(Book book) {

        if (books.add(book) != false) {
            return repo.save(books);
        }
        return false;
    }

    @Override
    public Book getBook(Long id) {
        return books.stream().filter(book -> book.getId().equals(id)).findAny().get();
    }

    @Override
    public List<Book> getBooks() {
        List<Book> bookList = new ArrayList<>(books);
        Collections.sort(bookList, (o1, o2) -> {
            if (o1.getId() >= o2.getId()) {
                return 1;
            } else {
                return -1;
            }
        });
        return bookList;
    }

    @Override
    public List<Book> getBooks(Predicate<Book> predicate) {

        Object[] objArr = books.stream().filter(predicate).toArray();
        Book[] bookArr = Arrays.copyOf(objArr, objArr.length, Book[].class);

        return Arrays.asList(bookArr);
    }

    @Override
    public List<Book> getBooks(Comparator<Book> comparator) {
        Object[] objArr = books.stream().sorted(comparator).toArray();
        Book[] bookArr = Arrays.copyOf(objArr, objArr.length, Book[].class);

        return Arrays.asList(bookArr);
    }

    @Override
    public boolean setBook(Book book) {
        return repo.save(books);
    }

    @Override
    public boolean removeBook(Book book) {
        if (books.remove(book) != false) {
            return repo.save(books);
        }
        return false;
    }
}
