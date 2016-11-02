package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Server extends Thread {
    private Socket socket;

    public Server(Socket socket) {
        this.socket = socket;
        this.start();
    }

    public void run() {
        try {
            System.out.println("Got a NEW client :)");
            System.out.println();

            // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиенту.
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

            String line;
            String serverResponse;
            while (true) {
                line = in.readUTF(); // ожидаем пока клиент пришлет строку текста.
                System.out.println("Client sent me this line : " + line);
                serverResponse = server.ProcessingOfReceivedMessages.getInstance().messageHandling(line);
                out.writeUTF(serverResponse); // отсылаем клиенту результат выполнения    //почему можно без инстанце
                out.flush(); // заставляем поток закончить передачу данных.
                System.out.println("Waiting for the next line...");
                System.out.println();
                try {                                       //для разгрузки ЦП
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("Server - command thread sleep - not OK!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
