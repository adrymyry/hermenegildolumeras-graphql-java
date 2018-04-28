package domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Stat {

    private String date;
    private String userAgent;
    private String referer;
    // ToDo add representative http headers

    // Needed in mongo POJO implementation
    public Stat() {
    }

    public Stat(String date, String userAgent, String referer) {
        this.date = date;
        this.userAgent = userAgent;
        this.referer = referer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }
}
