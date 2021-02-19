import repository.MYSQL.MYSQLRepository;
import repository.MONGODB.MONGODBRepository;


public class Main
{


    public static void main(final String[] args) throws Exception
    {
        try{
            Menu.menu();
        }catch (Exception e){
            System.out.println("------------------------------------------------------------");
            System.out.println("Erreur");
            System.out.println(e.getMessage());
            System.out.println("------------------------------------------------------------");
        }

    }

    public static boolean initSession(String type)
    {
        if (type.equals("MYSQL"))
        {
            MYSQLRepository.getSession(); //Initialise la session JPA
        } else if (type.equals("MONGODB"))
        {
            MONGODBRepository.getSession();
        } else
        {
            return true;
        }
        return false;
    }


    public static int isNumeric(String res)
    {
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
