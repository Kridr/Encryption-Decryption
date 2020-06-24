class Movie {
    private String title;
    private String desc;
    private int year;

    Movie(String title, String desc, int year) {
        this.title = title;
        this.desc = desc;
        this.year = year;
    }

    Movie(String title, int year) {
        this(title, "empty", year);
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public int getYear() {
        return year;
    }
}