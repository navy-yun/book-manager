package base.repository;

import base.Book;

import java.util.*;
import java.util.function.Predicate;

public class MapRepo implements BookRepository {

    //    private ArrayList<Book> bookList = new ArrayList<>();
    private HashMap<Long, Book> books = new HashMap<>();

    @Override
    public boolean addBook(Book book) {
        if (books.put(book.getId(), book) != null) {
            return true;
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

//    public List<Book> getBooks(Collector<Book> collector) {
//        Object[] objArr = books.values().stream().sorted(comparator).toArray();
//        Book[] bookArr = Arrays.copyOf(objArr, objArr.length, Book[].class);
//
//        return Arrays.asList(bookArr);
//    }

    @Override
    public boolean setBook(Book book) {
        if (books.put(book.getId(), book) != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean removeBook(Book book) {
        if (books.remove(book.getId()) != null) {
            return true;
        };
        return false;
    }
}
