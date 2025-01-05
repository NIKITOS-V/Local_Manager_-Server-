package ClientConnectDriver;

import Interfaces.FunctionsController;
import Interfaces.RecipientClientRequests;
import RequestTypes.ClientRequestType;
import RequestTypes.ServerRequestType;
import Interfaces.CCDController;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ClientConnectDriver implements Runnable, RecipientClientRequests {
    private final FunctionsController functionsController;

    private final Integer userID;
    private final String userName;

    private final CCDController server;

    private final Socket socket;
    private final BufferedWriter bufferedWriter;
    private final BufferedReader bufferedReader;

    public ClientConnectDriver(Integer userID, String userName, Socket socket, CCDController server) throws IOException {
        this.functionsController = new FunctionsManager(this);

        this.socket = socket;
        this.server = server;

        this.bufferedWriter = new BufferedWriter(
                new OutputStreamWriter(
                        socket.getOutputStream(),
                        StandardCharsets.UTF_8
                )
        );

        this.bufferedReader = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream(),
                        StandardCharsets.UTF_8
                )
        );

        this.userID = userID;

        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public Integer getUserID() {
        return userID;
    }

    public void sendMessageToClient(String userName, List<String> linesFromMessage){
        try {
            addTextToWriter(ServerRequestType.acceptMessage);

            addTextToWriter(userName);

            addTextToWriter(linesFromMessage.size());

            for (String line: linesFromMessage){
                addTextToWriter(line);
            }

            this.bufferedWriter.flush();

        } catch (IOException e){
            closeConnection();
        }
    }

    @Override
    public void sendToClientChatHistory(){
        server.sendChatHistoryToClient(this);
    }

    @Override
    public void sendMessageToClients() throws IOException, NumberFormatException {
        List<String> linesFromMessage = new LinkedList<>();

        int numberLines = Integer.parseInt(this.bufferedReader.readLine());

        for (int i = 0; i < numberLines; i++){
            linesFromMessage.add(this.bufferedReader.readLine());
        }

        this.server.sendMessageToClients(
                this.userID,
                this.userName,
                linesFromMessage
        );
    }

    private void addTextToWriter(String text) throws IOException {
        this.bufferedWriter.write(text);
        this.bufferedWriter.newLine();
    }

    private void addTextToWriter(Integer number) throws IOException {
        addTextToWriter(String.valueOf(number));
    }

    private void addTextToWriter(int number) throws IOException {
        addTextToWriter(String.valueOf(number));
    }

    private void addTextToWriter(ServerRequestType requestType) throws IOException {
        addTextToWriter(String.valueOf(requestType));
    }

    @Override
    public void run() {
        while (!this.socket.isClosed()){
            try {
                String requestType = this.bufferedReader.readLine();

                this.functionsController.execute(
                        requestType == null ? null : ClientRequestType.valueOf(requestType)
                );

            } catch (Exception e) {
                closeConnection();
            }
        }
    }

    @Override
    public void closeConnection(){
        System.out.println("close con start");

        this.server.delClientFromList(this);

        try {
            this.socket.close();
            this.bufferedWriter.close();
            this.bufferedReader.close();

        } catch (IOException e) {
            System.out.println("close con fail");
            e.printStackTrace();
        }

        System.out.println("close con nice");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        ClientConnectDriver clientConnectDriver = (ClientConnectDriver) obj;

        return this.userID.equals(clientConnectDriver.getUserID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.userID);
    }
}
