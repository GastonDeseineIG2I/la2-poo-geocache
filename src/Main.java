import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import repository.JPA.JPARepository;
import repository.MONGODB.MONGODBRepository;


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
