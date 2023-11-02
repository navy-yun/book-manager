package base;

import java.time.LocalDate;

public class AudioBook extends Book {
    String fileSize;
    String language;
    int playTime;

    public AudioBook(Long id, String name, String author, Long isbn, LocalDate publishedDate, String fileSize, String language, int playTime) {
        super(id, name, author, isbn, publishedDate);
        this.fileSize = fileSize;
        this.language = language;
        this.playTime = playTime;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getPlayTime() {
        return playTime;
    }

    public void setPlayTime(int playTime) {
        this.playTime = playTime;
    }
}
