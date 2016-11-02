package server;

import mongobd.Mongobd;
import sendingemail.MailSender;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

public class ProcessingOfReceivedMessages {

    private String sender;          // = "eugene.burak@gmail.com";          //стоит ли конвертировать в локал
    private String recipient;           // = "bea@sgsoft.com.ua";
    private String password;            // = "";

    private static HashMap<String, String> dataFromTerminal = new HashMap<>();

    private static ProcessingOfReceivedMessages instance = null;

    private ProcessingOfReceivedMessages()  {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter your Google mail: ");
            sender = bufferedReader.readLine();
            System.out.println("Your Google mail - " + sender);
            System.out.println();
            System.out.println("Enter the password of your Google mail: ");
            password = bufferedReader.readLine();
            System.out.println("Your password - " + password);
            System.out.println();
            System.out.println("Enter the recipient's address: ");
            recipient = bufferedReader.readLine();
            System.out.println("Your recipient's address - " + recipient);
            System.out.println();
            MailSender.getInstance().sender = sender;
            MailSender.getInstance().password = password;
            MailSender.getInstance().recipient = recipient;
            bufferedReader.close();
        }   catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static synchronized ProcessingOfReceivedMessages getInstance() {
        if (instance == null)   {
            instance = new ProcessingOfReceivedMessages();
        }
        return instance;
    }

    static synchronized String messageHandling(String line) {           //line = TerminalID;ClientID;DataTime   ->  1;1;Wed Oct 26 16:29:58 EEST 2016
        String result = "Error - client not found!";
        int priceOfTravel;
        String clientName;
        String[] lines = line.split(";");
        String terminalID = lines[0];
        String clientID = lines[1];
        String dataTime = lines[2];

        if (dataFromTerminal.containsKey(clientID)) {           //значит клиент выезжает

            String[] dataEntry = dataFromTerminal.get(clientID).split(";");         // -> 1;1;Wed Oct 26 16:29:58 EEST 2016;clientName
            priceOfTravel = Mongobd.getInstance().CalculationFare(dataEntry[0], terminalID);          //передаем номер терминала въезда и выезда
            MailSender.getInstance().SendMail(dataEntry[3], priceOfTravel, dataEntry[0], terminalID, dataEntry[2], dataTime);         //на почту след данные - имя, сумма платежа, терминал въезда и выезда, время въезда и выезда.
            dataFromTerminal.remove(clientID);
            result = dataEntry[3] + " - check-out time - " + dataTime + ". The price of travel - " + priceOfTravel;

            System.out.println(" находится в масиве >>>>>>>>>> ");
            for (HashMap.Entry entry : dataFromTerminal.entrySet()) {
                System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());
            }
            System.out.println();

        } else {            //значит клиент въезжает и его данные необходимо внести

//            System.out.println(" >>>>>>>>>> lines[1] >>>>>>>>>>" + clientID);

            clientName = mongobd.Mongobd.getInstance().GetClientName(clientID); //получаем имя клиента из базы используя его ClientID
            if (!clientName.equals("notFound"))   {
                line = line + ";" + clientName;
                dataFromTerminal.put(clientID, line);
                result = clientName + " - check-in time - " + dataTime;

                System.out.println(" положенно в масив >>>>>>>>>> " + dataFromTerminal.get(clientID));

            }
        }
        return result;
    }
}

