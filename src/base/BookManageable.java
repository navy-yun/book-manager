package base;

public interface BookManageable {
    void init();
    void interactWithUser();
    void addBook() throws Exception;
    void printAllBook();
    void updateBook() throws Exception;
    void removeBook() throws Exception;
}
