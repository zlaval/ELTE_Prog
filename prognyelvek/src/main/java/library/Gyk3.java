package library;

public class Gyk3 {

    public static void main(String[] args) {
        var book1 = new Book("Lord of the Rings", "J.R.R Tolkien", 1937, true);
        var book2 = new Book("Sojourn", "R. A. Salvatore", 1991, false);
        System.out.println(book1);
        System.out.println(book2);
        try {
            new Book(null, "", 1, false);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

}
