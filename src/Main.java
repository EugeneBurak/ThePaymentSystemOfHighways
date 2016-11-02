
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Main {

    public static void main(String[] args) {
        int port = 8080;
        int theNumberOfRemoteDevices = 10;
        server.ProcessingOfReceivedMessages.getInstance();
        System.out.println("To expect the connection of " + theNumberOfRemoteDevices + " remote devices");
        System.out.println("Port number - " + port);
        try {
            ServerSocket socketListener = new ServerSocket(port);
            for (int i = 0; i < theNumberOfRemoteDevices; i++) {
                Socket client = null;
                while (client == null) {
                    client = socketListener.accept();
                }
                new server.Server(client); //Создаем новый поток, которому передаем сокет
            }
            System.out.println("The devices was released on bond.");
        } catch (SocketException e) {
            System.err.println("Socket exception");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("I/O exception");
            e.printStackTrace();
        }
    }
}
