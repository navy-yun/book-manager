package base.bookmanager;

import base.book.AudioBook;
import base.book.Book;
import base.book.EBook;
import base.repository.BookRepository;
import base.repository.MapRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class BM6 implements BookManageable {

    BookRepository books = new MapRepository();

    static Scanner sc = new Scanner(System.in);

    static final String BOOK = "1";
    static final String EBOOK = "2";
    static final String AUDIOBOOK = "3";

    @Override
    public void init() {

    }

    @Override
    public void interactWithUser() {
        while (true) {
            try {
                System.out.println("■■■■■■ 도서 관리 프로그램 ■■■■■■");
                System.out.println("(1) 도서 조회");
                System.out.println("(2) 도서 등록");
                System.out.println("(3) 도서 수정");
                System.out.println("(4) 도서 삭제");
                System.out.println("(q) 프로그램 종료");
                System.out.print("선택 >> ");
                String userInput = sc.nextLine();

                switch (userInput) {
                    case "1":
                        menuForSearch();
                        break;
                    case "2":
                        addBook();
                        break;
                    case "3":
                        updateBook();
                        break;
                    case "4":
                        removeBook();
                        break;
                    case "q":
                        System.out.println("프로그램 종료!");
                        return;
                }
            } catch (Exception e) {
                System.out.println("예외 발생!");
                sc = new Scanner(System.in);
            }
        }
    }

    private void menuForSearch() {

        while (true) {
            System.out.println("■■■■■■ 도서 조회 메뉴 ■■■■■■");
            System.out.println("(1) 전체 조회");
            System.out.println("(2) 책 제목으로 조회");
            System.out.println("(3) 책 제목 사전순 조회");
            System.out.println("(4) 출간일 기간으로 조회");
            System.out.println("(5) 출간일순 조회");
            System.out.println("(6) 중복 책 찾기");
            System.out.println("(q) 메뉴 나가기");
            System.out.print("선택 >> ");

            String userInput = sc.nextLine();
            switch (userInput) {
                case "1":
                    printAllBook();
                    break;
                case "2":
                    printByBooksName();
                    break;
                case "3":
                    printAllByBookNameOrder();
                    break;
                case "4":
                    printByPublishedPeriod();
                    break;
                case "5":
                    printAllByPublishedDate();
                    break;
                case "6":
                    printDuplicated();
                    break;
                case "q":
                    return;
            }
        }
    }

    private void printDuplicated() {

        List<Book> duplicatedBooks = books.getBooks(
                (book ->
                {
                    ArrayList<Book> bookList = new ArrayList<>(books.getBooks());
                    if (bookList.contains(book)) {
                        bookList.remove(book);
                        if (bookList.contains(book)) {
                            return true;
                        }
                    }
                    return false;
                }
                )
        );

        duplicatedBooks.sort(
                (book1, book2) -> book1.getName().compareTo(book2.getName()) > 0 ? 1 : -1
        );
        booksToScreen(duplicatedBooks);
        System.out.println("중복된 책의 개수 : " + duplicatedBooks.size());
    }

    private void printAllByPublishedDate() {
        booksToScreen(
            books.getBooks(
                    Comparator.comparing(book -> (book).getPublishedDate())
            )
        );
    }

    private void printByPublishedPeriod() {
        System.out.println("■■■■■■ 출판일 기간으로 조회 ■■■■■■");
        System.out.print("시작일(YYYY-MM-DD) >> ");
        LocalDate start = LocalDate.parse(sc.nextLine());
        System.out.print("종료일(YYYY-MM-DD) >> ");
        LocalDate end = LocalDate.parse(sc.nextLine());

        booksToScreen(
            books.getBooks(book -> book.getPublishedDate().isBefore(end) &&
                    book.getPublishedDate().isAfter(start))
        );
    }

    private void printAllByBookNameOrder() {
        booksToScreen(
            books.getBooks((a, b) -> (a.getName().compareTo(b.getName()) > 0 ) ? 1 : -1)
        );
    }

    void booksToScreen(List<Book> bookList) {
        for (Book book : bookList) {
            System.out.println(book);
        }
    }

    private void printByBooksName() {
        System.out.println("■■■■■■ 책 제목으로 조회 ■■■■■■");
        System.out.print("제목 >> ");
        String bookName = sc.nextLine();

        booksToScreen(
            books.getBooks(book -> book.getName().contains(bookName))
        );
    }

    @Override
    public void addBook() {

        Book book;

        System.out.println("등록할 책의 종류를 선택해주세요.");
        System.out.println("(1)Book");
        System.out.println("(2)EBook");
        System.out.println("(3)AudioBook");
        System.out.print(">> ");
        String bookType = sc.nextLine();

        String[] bookInfo = new String[10];
        System.out.print("id: ");
        bookInfo[0] = sc.nextLine();
        System.out.print("제목: ");
        bookInfo[1] = sc.nextLine();
        System.out.print("저자: ");
        bookInfo[2] = sc.nextLine();
        System.out.print("isbn: ");
        bookInfo[3] = sc.nextLine();
        System.out.print("출판일(YYYY-MM-DD): ");
        bookInfo[4] = sc.nextLine();

        if (bookType.equals(EBOOK)) {
            System.out.print("파일크기(mb): ");
            bookInfo[5] = sc.nextLine();
            book = new EBook(Long.parseLong(bookInfo[0]),
                    bookInfo[1],
                    bookInfo[2],
                    Long.parseLong(bookInfo[3]),
                    LocalDate.parse(bookInfo[4]),
                    bookInfo[5]);
        } else if (bookType.equals(AUDIOBOOK)) {
            System.out.print("파일크기(mb): ");
            bookInfo[5] = sc.nextLine();
            System.out.print("재생언어: ");
            bookInfo[6] = sc.nextLine();
            System.out.print("재생시간(초): ");
            bookInfo[7] = sc.nextLine();
            book = new AudioBook(Long.parseLong(bookInfo[0]),
                    bookInfo[1],
                    bookInfo[2],
                    Long.parseLong(bookInfo[3]),
                    LocalDate.parse(bookInfo[4]),
                    bookInfo[5],
                    bookInfo[6],
                    Integer.parseInt(bookInfo[7]));
        } else {
             book = new Book(Long.parseLong(bookInfo[0]),
                    bookInfo[1],
                    bookInfo[2],
                    Long.parseLong(bookInfo[3]),
                    LocalDate.parse(bookInfo[4]));
        }

        if (books.addBook(book)) {
            System.out.println("등록이 완료되었습니다...");
        } else {
            System.out.println("등록이 실패하였습니다...");
        }
    }

    @Override
    public void printAllBook() {
        booksToScreen(books.getBooks());
    }

    @Override
    public void updateBook() {
        System.out.print("수정할 도서번호를 입력해주세요: ");
        String id = sc.nextLine();
        Book book = books.getBook(Long.parseLong(id));

        if (book == null) {
            System.out.println("입력하신 책을 찾을 수 없습니다.");
            return;
        }
        // 책이 존재할 때
        String[] bookInfo = new String[10];
        bookInfo[0] = id;
        System.out.println("[수정 정보를 입력해주세요]");
        System.out.print("제목: ");
        bookInfo[1] = sc.nextLine();
        System.out.print("저자: ");
        bookInfo[2] = sc.nextLine();
        System.out.print("isbn: ");
        bookInfo[3] = sc.nextLine();
        System.out.print("출판일(YYYY-MM-DD): ");
        bookInfo[4] = sc.nextLine();

        if (book instanceof EBook) {
            System.out.print("파일크기(mb): ");
            bookInfo[5] = sc.nextLine();
            ((EBook) book).setFileSize(bookInfo[5]);
        } else if (book instanceof AudioBook) {
            System.out.print("파일크기(mb): ");
            bookInfo[5] = sc.nextLine();
            System.out.print("재생언어: ");
            bookInfo[6] = sc.nextLine();
            System.out.print("재생시간(초): ");
            bookInfo[7] = sc.nextLine();
            ((AudioBook) book).setFileSize(bookInfo[5]);
            ((AudioBook) book).setLanguage(bookInfo[6]);
            ((AudioBook) book).setPlayTime(Integer.parseInt(bookInfo[7]));
        }

        book.setName(bookInfo[1]);
        book.setAuthor(bookInfo[2]);
        book.setIsbn(Long.parseLong(bookInfo[3]));
        book.setPublishedDate(LocalDate.parse(bookInfo[4]));

        if (books.setBook(book)) {
            System.out.println("수정이 완료되었습니다...");
        } else {
            System.out.println("수정이 실패하였습니다...");
        }
    }

    @Override
    public void removeBook() throws Exception {
        System.out.print("삭제할 도서번호를 입력해주세요: ");
        String id = sc.nextLine();

        Book book = books.getBook(Long.parseLong(id));
        if (book == null) {
            System.out.println("입력하신 책을 찾을 수 없습니다.");
        }
        
        if (books.removeBook(book)) {
            System.out.println("삭제가 완료되었습니다...");
        } else {
            System.out.println("삭제가 실패하였습니다...");
        }
    }
}
