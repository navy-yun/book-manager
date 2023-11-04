package base.repository;

import base.book.Book;

import java.util.*;
import java.util.function.Predicate;

public class MapDM implements DataManager {

    private BookRepository repo = new FileRepo();
    private HashMap<Long, Book> books = new HashMap<>();

    public MapDM() {
        repo.load().stream().forEach(
                (book) -> {
                    books.put(book.getId(), book);
                }
        );
    }
    List<Book> mapToList(HashMap<Long, Book> books) {
        return new ArrayList<>(books.values());
    }
    @Override
    public boolean addBook(Book book) {
        if (books.put(book.getId(), book) != null) {
            return repo.save(mapToList(books));
        }
        return false;
    }
    @Override
    public Book getBook(Long id) {
        return books.get(id);
    }
    @Override
    public List<Book> getBooks() {
        List<Book> bookList = new ArrayList<>(books.values());
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

        Object[] objArr = books.values().stream().filter(predicate).toArray();
        Book[] bookArr = Arrays.copyOf(objArr, objArr.length, Book[].class);

        return Arrays.asList(bookArr);
    }
    @Override
    public List<Book> getBooks(Comparator<Book> comparator) {
        Object[] objArr = books.values().stream().sorted(comparator).toArray();
        Book[] bookArr = Arrays.copyOf(objArr, objArr.length, Book[].class);

        return Arrays.asList(bookArr);
    }
    @Override
    public boolean setBook(Book book) {
        if (books.put(book.getId(), book) != null) {
            return repo.save(mapToList(books));
        }
        return false;
    }
    @Override
    public boolean removeBook(Book book) {
        if (books.remove(book.getId()) != null) {
            return repo.save(mapToList(books));
        }
        return false;
    }
}
