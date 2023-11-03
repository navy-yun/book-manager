package base;

import base.bookmanager.BM5;
import base.bookmanager.BookManageable;

public class Execute {

    static BookManageable bm = new BM5();

    public static void main(String[] args) {
        bm.init();
        bm.interactWithUser();
    }
}
