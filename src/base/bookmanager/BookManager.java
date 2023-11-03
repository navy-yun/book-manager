package base.bookmanager;


public abstract class BookManager {
    abstract void init();
    abstract void interactWithUser();
    abstract void addBook();
    abstract void printAllBook();
    abstract void updateBook();
    abstract void removeBook();
}
