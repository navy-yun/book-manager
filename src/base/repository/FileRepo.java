package base.repository;

import base.book.AudioBook;
import base.book.Book;
import base.book.EBook;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileRepo implements BookRepository {

    BufferedReader reader = null;
    BufferedWriter writer = null;
    List<Book> books = new ArrayList<>();
    static String url = "src/books.txt";

    @Override
    public void init() {

    }

    @Override
    public boolean save(List<Book> books) {
        try {
            writer = new BufferedWriter(new FileWriter(url, false));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        books.stream().map(
            book -> {
                String strBook = book.getId() + "#" + book.getName() + "#" + book.getAuthor()
                        + "#" + book.getIsbn() + "#" + book.getPublishedDate();
                if (book instanceof EBook) {
                    strBook += "#" + ((EBook) book).getFileSize();
                } else if (book instanceof AudioBook) {
                    strBook += "#" + ((AudioBook) book).getFileSize() + "#" + ((AudioBook) book).getLanguage()
                            + "#" + ((AudioBook) book).getPlayTime();
                }
                return strBook;
            }
        ).forEach(
            (strBook) -> {
                try {
                    writer.append(strBook);
                    writer.newLine();
                } catch (IOException e) {
                    System.out.println("예외 발생");
                    throw new RuntimeException(e);
                }
            }
        );
        try {
            writer.close();
            return true;
        } catch (IOException e) {
            return false;
        }

    }

    @Override
    public List<Book> load() {
        try {
            reader = new BufferedReader(new FileReader(url));

            while (true) {
                String line = reader.readLine();
                if (line == null) break;

                String[] book = line.split("#");

                if (book.length > 7) {
                    books.add(
                            new AudioBook(Long.parseLong(book[0]), book[1], book[2]
                                    ,Long.parseLong(book[3]), LocalDate.parse(book[4])
                                    ,book[5], book[6], Integer.parseInt(book[7])));
                    // 오디오북
                } else if (book.length > 5) {
                    books.add(
                            new EBook(Long.parseLong(book[0]), book[1], book[2]
                                    ,Long.parseLong(book[3]), LocalDate.parse(book[4])
                                    ,book[5]));
                } else {
                    books.add(
                            new Book(Long.parseLong(book[0]), book[1], book[2]
                                    ,Long.parseLong(book[3]), LocalDate.parse(book[4])));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return books;
        }
    }

    @Override
    public void backUp(List<Book> books) {

    }
}
