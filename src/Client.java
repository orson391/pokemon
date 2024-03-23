import java.io.*;
import java.net.*;



public class Client {

    private String name;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter out;

    public Client(String name) {
        this.name = name;
        try {
            socket = new Socket("localhost", 12345); // Connect to server
            System.out.println("Connected to server.");
            reader = new BufferedReader(new InputStreamReader(System.in));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendNameToServer() {
        out.println(name);
    }

    public void startReceivingMessagesFromServer() {
        new Thread(() -> {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String serverResponse;
                while ((serverResponse = in.readLine()) != null) {
                    System.out.println("Server: " + serverResponse);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void sendMessagesToServer() {
        try {
            String userInput;
            while ((userInput = reader.readLine()) != null) {
                out.println(userInput);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*public static void main(String[] args) {
        Client client = new Client("YourName");
        client.sendNameToServer();
        client.startReceivingMessagesFromServer();
        client.sendMessagesToServer();
        client.closeConnection();
    }*/
}

