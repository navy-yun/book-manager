package base;

import java.time.LocalDate;
import java.util.*;

// BookManager를 구현하는 구현 객체
public class BM4 implements BookManageable {

//    private ArrayList<Book> bookList = new ArrayList<>();
    private HashMap<Long, Book> bookList = new HashMap<>();
    static Scanner sc = new Scanner(System.in);

    static final String BOOK = "1";
    static final String EBOOK = "2";
    static final String AUDIOBOOK = "3";

    @Override
    public void init() {
        bookList.put(1L, new Book(1L, "돈의 속성(300쇄 리커버에디션)", "김승호", Long.parseLong("9791188331796"), LocalDate.parse("2020-06-15")));
        bookList.put(2L, new Book(2L,"K 배터리 레볼루션", "박순혁", Long.parseLong("9791191521221"), LocalDate.parse("2023-02-20")));
        bookList.put(3L, new Book(3L, "위기의 역사", "오건영", Long.parseLong("9791169850360"), LocalDate.parse("2023-07-19")));
        bookList.put(6L, new Book(1L, "돈의 속성(300쇄 리커버에디션)", "김승호", Long.parseLong("9791188331796"), LocalDate.parse("2020-06-15")));
        bookList.put(7L, new Book(2L,"K 배터리 레볼루션", "박순혁", Long.parseLong("9791191521221"), LocalDate.parse("2023-02-20")));
        bookList.put(8L, new Book(3L, "위기의 역사", "오건영", Long.parseLong("9791169850360"), LocalDate.parse("2023-07-19")));
        bookList.put(4L, new EBook(4L,
                "진짜 쓰는 실무 엑셀",
                "오빠두(전진권)",
                Long.parseLong("9791192469454"),
                LocalDate.parse("2022-09-27"),
                "122.11"));
        bookList.put(5L, new AudioBook(5L,
                "이솝 & 세계 명작 동화",
                "서울미디어",
                Long.parseLong("8804678148796"),
                LocalDate.parse("2015-06-09"),
                "210.18",
                "한국어",
                Integer.parseInt("2376")));
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
                        // 조회
                        menuForSearch();
                        break;
                    case "2":
                        // 등록
                        addBook();
                        break;
                    case "3":
                        // 수정
                        updateBook();
                        break;
                    case "4":
                        // 삭제
                        removeBook();
                        break;
                    case "q":
                        // 메소드를 종료
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
                    printByBookName();
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

        HashSet<Book> distinctBooks = new HashSet<>();

        Set<Map.Entry<Long, Book>> entrySet = bookList.entrySet();
        Iterator<Map.Entry<Long, Book>> it = entrySet.iterator();

        while (it.hasNext()) {
            distinctBooks.add(it.next().getValue());
        }

        for (Book book: distinctBooks) {
            System.out.println(book);
        }
    }

    private void printAllByPublishedDate() {
        HashMap<Long, Book> orderedBookList;
//        for (Book book : bookList) {
//            orderedBookList.add(book);
//        }

        orderedBookList = (HashMap<Long, Book>) bookList.clone();

        Collections.sort((List)orderedBookList, (book1, book2) ->
                ((Book)book1).getPublishedDate().compareTo(((Book)book2).getPublishedDate()));
        booksToScreen(orderedBookList);
    }

    private void printByPublishedPeriod() {
        HashMap<Long, Book> listSelected = new HashMap<>();

        System.out.println("■■■■■■ 출판일 기간으로 조회 ■■■■■■");
        System.out.print("시작일(YYYY-MM-DD) >> ");
        LocalDate start = LocalDate.parse(sc.nextLine());
        System.out.print("종료일(YYYY-MM-DD) >> ");
        LocalDate end = LocalDate.parse(sc.nextLine());

        Set<Map.Entry<Long, Book>> entrySet = bookList.entrySet();
        Iterator<Map.Entry<Long, Book>> it = entrySet.iterator();

        while (it.hasNext()) {
            Book book = it.next().getValue();
            if (book.getPublishedDate().isBefore(end) &&
                book.getPublishedDate().isAfter(start)) {
                listSelected.put(book.getId(), book);
            }
        }

//        for (Book book : bookList) {
//            if (book.getPublishedDate().isBefore(end) &&
//                book.getPublishedDate().isAfter(start)) {
//                listSelected.add(book);
//            }
//        }

        if (listSelected.size() > 0) {
            booksToScreen(listSelected);
        } else {
            System.out.println("조회된 도서가 하나도 없습니다.");
        }

    }

    private void printAllByBookNameOrder() {
        HashMap<Long, Book> orderedBookList;
        // Collections.copy(orderedBookList, bookList);
//        for (Book book : bookList) {
//            orderedBookList.add(book);
//        }
//        orderedBookList = (ArrayList<Book>) bookList.clone();
        orderedBookList = bookList;

        Collections.sort((List)orderedBookList, (book1, book2) ->
                ((Book)book1).getName().compareTo(((Book)book2).getName()));
        booksToScreen(orderedBookList);
    }
    void booksToScreen(HashMap<Long, Book> bookList) {

        Set<Map.Entry<Long, Book>> entrySet = bookList.entrySet();
        Iterator<Map.Entry<Long, Book>> it = entrySet.iterator();

        while (it.hasNext()) {
            Map.Entry<Long, Book> next = it.next();
            System.out.println(next.getValue());
        }
    }

    private void printByBookName() {
        HashMap<Long, Book> listSelected = new HashMap<>();

        System.out.println("■■■■■■ 책 제목으로 조회 ■■■■■■");
        System.out.print("제목 >> ");
        String bookName = sc.nextLine();

        Set<Map.Entry<Long, Book>> entrySet = bookList.entrySet();
        Iterator<Map.Entry<Long, Book>> it = entrySet.iterator();

        while (it.hasNext()) {
            Book book = it.next().getValue();
            if (book.getName().contains(bookName)) {
                listSelected.put(book.getId(), book);
            }
        }

        if (listSelected.size() > 0) {
            booksToScreen(listSelected);
        } else {
            System.out.println("조회된 도서가 하나도 없습니다.");
        }
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

        bookList.put(book.getId(), book);
    }

    @Override
    public void printAllBook() {
        booksToScreen(bookList);
    }

    @Override
    public void updateBook() {

        System.out.println("수정 메서드 실행");
        // 1. 수정할 도서를 찾는다. (사서는 알 수 있다.) (v)
        // 있으면 수정 가능
        // 없으면 수정 불가
        // 2. 수정할 도서가 있을 때:
        // 새로운 입력 값 : 사용자로부터 입력받는다.
        // 도서 정보(필드)를 바꾼다.

        System.out.print("수정할 도서번호를 입력해주세요: ");
        String id = sc.nextLine();
        Book book = findBook(Long.parseLong(id));

        // 책이 존재하지 않을 때
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
    }

    @Override
    public void removeBook() throws Exception {
        System.out.println("삭제 메서드 실행");
        // 1. 삭제할 도서를 찾는다.
        // 없으면 삭제 불가
        // 있으면 삭제 가능
        // 2. 삭제할 도서가 있다면
        // 사서한테 도서 삭제 요청
        System.out.print("삭제할 도서번호를 입력해주세요: ");
        String id = sc.nextLine();

        Book book = findBook(Long.parseLong(id));
        if (book == null) {
            System.out.println("입력하신 책을 찾을 수 없습니다.");
        }
        bookList.remove(book);
    }

    public Book findBook(long id) {

        for (Book book : bookList.values()) {
            if (id == book.getId()) {
                return book;
            }
        }
        // bookList를 다 돌았는데 해당 id의 도서를 못찾았다.
        return null;
    }
}
