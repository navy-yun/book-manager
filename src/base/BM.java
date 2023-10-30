package base;

import java.util.ArrayList;

// BookManager를 구현하는 구현 객체
public class BM extends BookManager {

    private ArrayList<Book> bookList = new ArrayList<>();

    @Override
    public void addBook(Book book) {

    }

    @Override
    public void printAllBook() {
        for (Book book : bookList) {
            System.out.print("[");
            System.out.print(book.getId());
            System.out.print(", ");
            System.out.print(book.getName());
            System.out.print(", ");
            System.out.print(book.getAuthor());
            System.out.print(", ");
            System.out.print(book.getIsbn());
            System.out.print(", ");
            System.out.print(book.getPublishedDate());
            System.out.print("]");
            System.out.println();
        }
    }

    @Override
    public void updateBook(Book book) {

    }

    @Override
    public void removeBook(Book book) {

    }
}
