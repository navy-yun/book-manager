package base;

import java.time.LocalDate;
import java.util.Scanner;

public class Execute {
    // 사용자 입력을 받기 위한 스캐너 객체
    static Scanner sc = new Scanner(System.in);
    static BM bm = new BM();
    public static void main(String[] args) {

        while (true) {
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
                    printAllBook();
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
                    deleteBook();
                    break;
                case "q":
                    System.out.println("프로그램 종료!");
                    return; // 메소드를 종료
            }

        }
    }

    private static void deleteBook() {
        System.out.println("삭제 메서드 실행");
        // bm.removeBook(?);
    }

    private static void updateBook() {
        System.out.println("수정 메서드 실행");
        // bm.updateBook(?);
    }

    private static void addBook() {
        System.out.println("등록 메서드 실행");
        // 1. 콘솔화면을 통해 사용자로부터 도서정보를 입력을 받는다.
            // id, 제목, 저자, isbn, 출판일 (5가지) (v)
            // 위의 정보로 책 객체를 생성한다. (v)
        // 2. 도서를 등록한다.
            // 사서를 통해 도서 저장 요청

        System.out.print("id: ");
        String id = sc.nextLine();
        System.out.print("제목: ");
        String name = sc.nextLine();
        System.out.print("저자: ");
        String author = sc.nextLine();
        System.out.print("isbn: ");
        String isbn = sc.nextLine();
        System.out.print("출판일(YYYY-MM-DD): ");
        String publishDate = sc.nextLine();

        // id, isbn는 String 타입이므로 Long으로 변환 후 매개값을 주어야한다.
        // publishedDate는 String 타입인데 LocalDate 타입으로 변환해주어야 한다. ==> "구글링"
        Book book = new Book(Long.parseLong(id),
                        name,
                        author,
                        Long.parseLong(isbn),
                        LocalDate.parse(publishDate));

        bm.addBook(book);
    }

    private static void printAllBook() {
        System.out.println("조회 메서드 실행");
        bm.printAllBook();
    }
}
