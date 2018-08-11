package model;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Ad {

    private long id;
    private String theme;
    private String author;
    private int answersNumber;
    private int viewsNumber;
    private String dateTime;

    public boolean isJavaAd() {
        String lowercaseTheme = theme.toLowerCase();
        boolean is = false;
        if (lowercaseTheme.contains("java")) {
            if (!lowercaseTheme.contains("script")) {
                is = true;
            }
        }
        return is;
    }

    public boolean isNotFromThisYear() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("mm dd yy, HH:mm");
        DateTime dtAd = formatter.parseDateTime(dateTime);
        DateTime dtNow = new DateTime(System.currentTimeMillis());
        return dtAd.getYear() < dtNow.getYear();
    }

    public long datetime2timestamp() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("mm dd yy, HH:mm");
        DateTime dt = formatter.parseDateTime(dateTime);
        return dt.getMillis() / 1000;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getAnswersNumber() {
        return answersNumber;
    }

    public void setAnswersNumber(int answersNumber) {
        this.answersNumber = answersNumber;
    }

    public int getViewsNumber() {
        return viewsNumber;
    }

    public void setViewsNumber(int viewsNumber) {
        this.viewsNumber = viewsNumber;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Ad{" + "id=" + id + ", theme='" + theme + '\'' + ", author='"
                + author + '\'' + ", answersNumber=" + answersNumber
                + ", viewsNumber=" + viewsNumber + ", dateTime='"
                + dateTime + '\'' + '}';
    }
}
