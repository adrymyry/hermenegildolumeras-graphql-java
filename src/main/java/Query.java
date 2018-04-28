import com.coxautodev.graphql.tools.GraphQLRootResolver;
import domain.Stat;
import domain.Url;
import domain.User;

import java.util.List;

public class Query implements GraphQLRootResolver {

    private final Database db;

    public Query (Database db) {

        this.db = db;
    }
    
    public User user(String username) {
        return db.getUser(username);
    }

    public Url url(String urlid) {
        return db.getUrl(urlid);
    }

    public List<Url> allUserUrls(String username) {
        return db.getUrls(username);
    }

    public List<Stat> allStats(String urlid) {
        return db.getStats(urlid);
    }
}
