import java.util.*;

interface BookFilter {
    boolean apply(Book book);
}

class TitleFilter implements BookFilter {
    private String title;

    public TitleFilter(String title) {
        this.title = title;
    }

    @Override
    public boolean apply(Book book) {
        return book.title.contains(title);
    }
}

enum BookSize {
    BIG,
    MEDIUM,
    SMALL
}

class BookSizeFilter implements BookFilter {
    private BookSize desiredSize;

    public BookSizeFilter(BookSize desiredSize) {
        this.desiredSize = desiredSize;
    }

    @Override
    public boolean apply(Book book) {
        int minPages = 0;
        int maxPages = 0;

        switch (desiredSize) {
            case BIG:
                minPages = 0;
                maxPages = 100;
                break;
            case MEDIUM:
                minPages = 101;
                maxPages = 500;
                break;
            case SMALL:
                minPages = 501;
                maxPages = Integer.MAX_VALUE;
                break;
        }
        return book.pageCount >= minPages && book.pageCount < maxPages;
    }
}

class Book {
    String title;
    int pageCount;
    
    public Book(String title, int pageCount) {
        this.title = title;
        this.pageCount = pageCount;
    }
}

public class BookFilterUtil {
    public static boolean bookPassesFilters(Book book, List<BookFilter> filters) {
        for (BookFilter filter : filters) {
            if (!filter.apply(book)) {
                return false;
            }
        }
        return true;
    }

    public static Set<Book> filterBooks(List<Book> bookList, List<BookFilter> filters) {
        Set<Book> result = new HashSet<>();
        for (Book book : bookList) {
            if (bookPassesFilters(book, filters)) {
                result.add(book);
            }
        }
        return result;
    }
}
