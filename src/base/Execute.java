package base;

public class Execute {

    static BookManageable bm = new BM5();

    public static void main(String[] args) {
        bm.init();
        bm.interactWithUser();
    }
}
