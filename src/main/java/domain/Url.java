package domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

@XmlRootElement
public class Url {

    private String id;
    private String longUrl;
    private String shortUrl;
    private String date;
    private List<Stat> stats;
    private String userOwner;

    // Needed in mongo POJO implementation
    public Url () {
    }

    public Url(String id, String longUrl, String shortUrl, String date, List<Stat> stats, String userOwner) {
        this.id = id;
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
        this.date = date;
        this.stats = stats;
        this.userOwner = userOwner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Stat> getStats() {
        return stats;
    }

    public void setStats(List<Stat> stats) {
        this.stats = stats;
    }

    public String getUserOwner () {

        return userOwner;
    }

    public void setUserOwner (String userOwner) {

        this.userOwner = userOwner;
    }
}
