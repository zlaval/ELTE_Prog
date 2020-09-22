package library;

import java.util.Objects;

public class Book {
    private final String title;
    private final String author;
    private final int yearOfPublication;
    private final boolean borrowable;

    public Book(String title, String author, int yearOfPublication, boolean borrowable) {
        if (Objects.isNull(title)) {
            throw new IllegalArgumentException("Title must be set");
        }

        this.title = title;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
        this.borrowable = borrowable;
    }

    @Override
    public String toString() {
        String str = title + " was written by " + author + " in " + yearOfPublication;
        str += borrowable ? " is available now" : " is not available";
        return str;
    }
}
