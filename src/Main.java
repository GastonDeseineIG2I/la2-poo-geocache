import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import repository.JPA.JPARepository;
import repository.MONGODB.MONGODBRepository;


public class Main {


    public static void main(final String[] args) throws Exception {
        Menu.menu();
    }

    public static boolean initSession(String type)
    {
        if(type.equals("MYSQL")){
            JPARepository.getSession(); //Initialise la session JPA
        }else if(type.equals("MONGODB")){
            MONGODBRepository.getSession();
        }else{
            return true;
        }
        return false;
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
