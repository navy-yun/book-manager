package base;

import base.bookmanager.BM5;
import base.bookmanager.BM6;
import base.bookmanager.BookManageable;

public class Execute {

    static BookManageable bm = new BM6();

    public static void main(String[] args) {
        bm.init();
        bm.interactWithUser();
    }
}
