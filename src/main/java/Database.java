import domain.Stat;
import domain.Url;
import domain.User;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.exclude;
import static com.mongodb.client.model.Updates.addToSet;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class Database {

    MongoCollection<User> users;
    MongoCollection<Url> urls;

    public Database(){

        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoClient mongoClient = new MongoClient("localhost", MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());

        MongoDatabase database = mongoClient.getDatabase("mydb");

        this.users = database.getCollection("users", User.class);
        this.urls = database.getCollection("urls", Url.class);

    }

    public void createUser (User user) {
        this.users.insertOne(user);
    }

    public User getUser (String username) {
        return this.users.find(eq("_id", username)).first();
    }

    public void deleteUser (String username) {
        this.users.deleteOne(eq("_id", username));
    }

    public void createUrl (Url url) {
        this.urls.insertOne(url);
    }

    public Url getUrl (String id) {
        return this.urls.find(eq("_id", id)).first();
    }

    public void deleteUrl (String id) {
        this.urls.deleteOne(eq("_id", id));
    }

    public List<Url> getUrls (String username) {
        final List<Url> userUrls = new LinkedList<Url>();

        this.urls.find(eq("userOwner", username)).forEach(new Block<Url>() {
            public void apply (Url url) {
                userUrls.add(url);
            }
        });

        return userUrls;
    }

    public List<Stat> getStats (String urlid) {

        return this.urls.find(eq("_id", urlid)).first().getStats();
    }

    public void addStat (String urlid, Stat stat) {

        this.urls.updateOne(eq("_id", urlid), addToSet("stats", stat));
    }

    public List<Stat> getStats (String urlid, int limit, int order) {

        Url url = this.urls.aggregate(Arrays.asList(
                // { "$match": { "_id": "id1"} },
                new Document("$match", new Document("_id", urlid)),
                // { "$unwind": "$stats" },
                new Document("$unwind", "$stats"),
                // { "$sort": { "stats.date": -1 }},
                new Document("$sort", new Document("stats.date", order)),
                //  { "$limit": 1 },
                new Document("$limit", limit),
                // { "$group": {
                //                "_id": "$_id",
                //                "stats": { "$push": { "date": "$stats.date",
                //                    "origin": "$stats.origin",
                //                    "userAgent": "$stats.userAgent"
                //            }
                //        }
                //    }}
                new Document("$group", new Document("_id", "$_id")
                        .append("stats", new Document( "$push", new Document("date", "$stats.date")
                                                .append("origin", "$stats.origin")
                                                .append("userAgent", "$stats.userAgent")
                                )
                        )
                )
        )).first();
        // sanity check
        if (url != null) {
            return url.getStats();
        } else {
            return new LinkedList<Stat>();
        }
    }

    public List<Stat> getStats (String urlid, Date start, Date end) {

        System.out.println(start.toGMTString() + " " + end.toGMTString());

        Url url = this.urls.aggregate(Arrays.asList(
                // { "$match": { "_id": "id1"} },
                new Document("$match", new Document("_id", urlid)),
                // { "$unwind": "$stats" },
                new Document("$unwind", "$stats"),
                // {"$match": {
                //    "stats.date": {
                //        $gte: ISODate("2018-04-18T10:53:00.000Z"),
                //        $lte: ISODate("2018-04-18T10:54:00.000Z")
                //    }
                // }},
                new Document("$match", new Document("stats.date", new Document("$gte", start)
                                            .append("$lte", end)
                                        )
                ),
                // { "$group": {
                //                "_id": "$_id",
                //                "stats": { "$push": { "date": "$stats.date",
                //                    "origin": "$stats.origin",
                //                    "userAgent": "$stats.userAgent"
                //            }
                //        }
                //    }}
                new Document("$group", new Document("_id", "$_id")
                        .append("stats", new Document( "$push", new Document("date", "$stats.date")
                                        .append("origin", "$stats.origin")
                                        .append("userAgent", "$stats.userAgent")
                                )
                        )
                )
        )).first();
        // sanity check
        if (url != null) {
            return url.getStats();
        } else {
            return new LinkedList<Stat>();
        }
    }

}
