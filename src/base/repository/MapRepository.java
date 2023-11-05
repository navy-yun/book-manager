package base.repository;

import base.book.Book;
import base.repository.storage.DBStorage;
import base.repository.storage.Storable;
import base.repository.threads.AutoSaveThread;
import base.repository.threads.BackupThread;

import java.util.*;
import java.util.function.Predicate;

public class MapRepository implements BookRepository {

//    private Storable storage = new FileStorage();
    private Storable storage = new DBStorage();
    private HashMap<Long, Book> books = new HashMap<>();

    public MapRepository() {
        init();
    }
    void init() {
        storage.load().stream().forEach(
                (book) -> {
                    books.put(book.getId(), book);
                }
        );

        Thread autoSaveThread = new Thread(new AutoSaveThread(storage, books));
        Thread backupThread = new Thread(new BackupThread(storage));

        autoSaveThread.setDaemon(true);
        autoSaveThread.start();
        backupThread.setDaemon(true);
        backupThread.start();
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
    public boolean addBook(Book book) {
        HashMap<Long, Book> prev = new HashMap<>(books);
        books.put(book.getId(), book);
        if (detectUpdate(prev, books)) {
            return true;
        }
        return false;
    }
    @Override
    public boolean setBook(Book book) {
        HashMap<Long, Book> prev = new HashMap<>(books);
        books.put(book.getId(), book);
        if (detectUpdate(prev, books)) {
            return true;
        }
        return false;
    }
    @Override
    public boolean removeBook(Book book) {
        HashMap<Long, Book> prev = new HashMap<>(books);
        books.remove(book.getId());
        if (detectUpdate(prev, books)) {
            return true;
        }
        return false;
    }

    boolean detectUpdate(HashMap<Long, Book> prev, HashMap<Long, Book> curr) {
        return !prev.equals(curr);
    }
}
