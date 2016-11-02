package sendingemail;

public class MailSender {

    public String sender;
    public String recipient;
    public String password;

    private static MailSender instance = null;
    private MailSender()   {}
    public static MailSender getInstance() {
        if (instance == null) {
            instance = new MailSender();
        }
        return instance;
    }
    public void SendMail(String clientName, int priceOfTravel, String terminalCheckInID, String terminalCheckOutID, String timeCheckIn, String timeCheckOut)    {
        String mailBody = "Dear " + clientName + " a bill for the fare is " + priceOfTravel + " EURO. The place of entrance - Terminal №" +
                terminalCheckInID + ", the time - " + timeCheckIn + ". The place of departure Terminal №" + terminalCheckOutID + ", time - " +
                timeCheckOut + ". Have a good day!";
        GMailSender messageSender = new GMailSender(sender, password);
        try {
            messageSender.sendMail("Bill for the use highway.", mailBody, sender, recipient);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
