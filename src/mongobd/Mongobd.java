package mongobd;

import com.mongodb.*;
import java.net.UnknownHostException;

public class Mongobd {
    private static Mongobd instance;

    private DBCollection collClients;
    private DBCollection collCheckpoints;

    private Mongobd()   {
        try {
            MongoClient mongo = new MongoClient("localhost", 27017);
            DB db = mongo.getDB("EugeneBURAK");
            System.out.println("Имя базы данных: "+db.getName()+"\n");
            collClients = db.getCollection("clients");
            collCheckpoints = db.getCollection("checkpoints");
        } catch (UnknownHostException | MongoException e) {
            e.printStackTrace();
            System.out.println("Exception - private Mongobd() !!!!!");
        }
    }
    public static synchronized Mongobd getInstance() {          //считаю что синхр здесь не нужна тк обращение идет из синхронезированного метода messageHandling
        if (instance == null)   {
            instance = new Mongobd();
        }
        return instance;
    }
    public String GetClientName(String clientID)   {    //возможно стоит использовать блок try/catch
        String clientName = "notFound";
        String clientNameFromBD = "null";   //почему если инициализировать null, то NullPointerException?????????????????????????????????

        System.out.println(" >>>>>>>>>> clientID >>>>>>>>>> " + clientID);

        BasicDBObject queryClient = new BasicDBObject();
        queryClient.put("clientNumber", clientID);

//        DBCursor cur = collClients.find(queryClient);
//
//        while(cur.hasNext())
//        {
//            System.out.println(cur.next());
//        }
//
//        System.out.println();

        DBCursor curNameFromBD = collClients.find(queryClient);
        while (curNameFromBD.hasNext()) {
            clientNameFromBD = (String) curNameFromBD.next().get("clientName");
        }

        if (!clientNameFromBD.equals("null"))    {    //в реальной системе номер уникален, создается базой и поэтому проверки на уникальность не надо.
            clientName = clientNameFromBD;
        }

        return clientName;

    }
    public int CalculationFare(String terminalCheckIn, String terminalCheckOut) {
        int priceOfTravel = 10;
        String priceFromBD = "null";

        System.out.println(" >>>>>>>>>> terminalCheckIn >>>>>>>>>> " + terminalCheckIn);
        System.out.println(" >>>>>>>>>> terminalCheckOut >>>>>>>>>> " + terminalCheckOut);

        String checkOut = "checkOut".concat(terminalCheckOut);

        BasicDBObject queryCheckpoint = new BasicDBObject();
        queryCheckpoint.put("checkIn", terminalCheckIn);

        DBCursor curPriceFromBD = collCheckpoints.find(queryCheckpoint);
        while (curPriceFromBD.hasNext()) {
            priceFromBD = (String) curPriceFromBD.next().get(checkOut);
        }

        if (!priceFromBD.equals("null"))    {    //в реальной системе номер уникален, создается базой и поэтому проверки на уникальность не надо.
            priceOfTravel = Integer.parseInt(priceFromBD);
        }

        return priceOfTravel;

    }
}
