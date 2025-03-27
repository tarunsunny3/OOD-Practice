interface Filter {
    boolean apply(Title title);
}

class ReleaseYearFilter implements Filter {
    private int year;
    private String condition;

    public ReleaseYearFilter(int year, String condition) {
        this.year = year;
        this.condition = condition;
    }

    @Override
    public boolean apply(Title title) {
        if (condition.equals("<")) {
            return title.releaseYear < year;
        } else if (condition.equals(">")) {
            return title.releaseYear > year;
        }
        return false;
    }
}

class DurationFilter implements Filter {
    private int minDuration;

    public DurationFilter(int minDuration) {
        this.minDuration = minDuration;
    }

    @Override
    public boolean apply(Title title) {
        return title.duration > minDuration;
    }
}

class RatingFilter implements Filter {
    private double minRating;

    public RatingFilter(double minRating) {
        this.minRating = minRating;
    }

    @Override
    public boolean apply(Title title) {
        return title.rating >= minRating;
    }
}

public class TitleFilter {

    public static List<Title> filterTitles(List<Title> titles, Filter filter) {
        List<Title> result = new ArrayList<Title>();
        for (Title title : titles) {
            if (filter.apply(title)) {
                result.add(title);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        List<Title> titles = Arrays.asList(
            new Title("Movie A", 1995, 120, 8.5),
            new Title("Movie B", 2001, 90, 7.2),
            new Title("Movie C", 1987, 15, 6.8),
            new Title("Movie D", 1998, 9, 9.0)
        );

        List<Title> before1998 = filterTitles(titles, new ReleaseYearFilter(1998, "<"));
        System.out.println("Titles released before 1998: " + before1998);

        List<Title> highRatedTitles = filterTitles(titles, new RatingFilter(8.0));
        System.out.println("Titles with rating >= 8.0: " + highRatedTitles);
    }
}
