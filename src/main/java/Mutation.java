import com.coxautodev.graphql.tools.GraphQLRootResolver;
import domain.Stat;
import domain.Url;
import domain.User;

import java.util.Date;
import java.util.LinkedList;

public class Mutation implements GraphQLRootResolver {

    private final Database db;
    private static int lastId = 0;

    public Mutation (Database db) {
        this.db = db;
    }

    public User createUser(String username, String password, String email) {
        User newUser = new User(username, password, email);
        db.createUser(newUser);
        return newUser;
    }

    public Url createUrl(String longUrl, String user) {
        lastId++;
        Url newUrl = new Url("id"+lastId, longUrl, null, new Date().toString(), new LinkedList<>(), user);
        newUrl.setShortUrl("hlum.com/u/"+ newUrl.getId());
        db.createUrl(newUrl);
        return newUrl;
    }

    public Stat addStat(String urlid, String userAgent, String referer) {
        Stat stat = new Stat(new Date().toString(), userAgent, referer);
        db.addStat(urlid, stat);
        return stat;
    }
}
