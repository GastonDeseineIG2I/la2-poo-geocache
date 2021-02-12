import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import modele.CacheEntity;
import modele.VisiteEntity;
import modele.CacheEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.internal.expression.function.UpperFunction;
import repository.*;
import repository.JPA.JPARepository;
import repository.MONGODB.MONGODBRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {

    public static final String BDD = "MONGODB"; // MONGODB | MYSQL
    protected static MongoClient mongoClient;
    protected static MongoDatabase database;



    public static void main(final String[] args) throws Exception {
        if(BDD == "MYSQL"){
            JPARepository.getSession(); //Initialise la session JPA
        }else if(BDD == "MONGODB"){
            MONGODBRepository.getSession();
        }else{
            return;
        }

        Menu.menu();
    }





    public static int isNumeric(String res){
        int numeric;
        try
        {
            numeric = Integer.parseInt(res);
        } catch (NumberFormatException nfe)
        {
            numeric = -1;
        }

        return numeric;
    }


}
