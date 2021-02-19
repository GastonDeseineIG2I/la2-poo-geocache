package repository.MONGODB;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.hibernate.HibernateException;
import repository.RepositoryInterface;

public abstract class MONGODBRepository implements RepositoryInterface
{
    private static MongoClient mongoClient;
    private static MongoDatabase database;

    static
    {

        mongoClient = new MongoClient(new MongoClientURI("mongodb://127.0.0.1:27017"));
        database = mongoClient.getDatabase("la2-geocache");

    }

    public static MongoClient getSession() throws HibernateException
    {
        return mongoClient;
    }
}
