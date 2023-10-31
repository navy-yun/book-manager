package base;

public class Execute {

    static BookManager bm = new BM();

    public static void main(String[] args) {
        bm.init();
        bm.interactWithUser();
    }
}
