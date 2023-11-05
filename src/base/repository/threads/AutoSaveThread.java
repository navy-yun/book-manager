package base.repository.threads;

import base.book.Book;
import base.repository.storage.Storable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class AutoSaveThread implements Runnable {
    private Storable storage;
    private HashMap<Long, Book> books;
    Stack<List<Book>> history = new Stack<>();

    public AutoSaveThread(Storable storage, HashMap<Long, Book> books) {
        this.storage = storage;
        this.books = books;
    }

    @Override
    public void run() {
        history.push(new ArrayList<>(books.values()));
        while (true) {
            List<Book> prev = history.pop();
            List<Book> curr = new ArrayList<>(books.values());
            if (!prev.equals(curr)) {
                history.push(curr);
                storage.save(mapToList(books));
            } else {
                history.push(prev);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    List<Book> mapToList(HashMap<Long, Book> books) {
        return new ArrayList<>(books.values());
    }
}
