package ru.NIKITOS_V.ClientConnectDriver;

import ru.NIKITOS_V.JavaInterfaces.CCDController;
import ru.NIKITOS_V.JavaInterfaces.FunctionsController;
import ru.NIKITOS_V.JavaInterfaces.LogWriter;
import ru.NIKITOS_V.JavaInterfaces.RecipientClientRequests;
import ru.NIKITOS_V.RequestTypes.ClientRequestType;
import ru.NIKITOS_V.RequestTypes.ServerRequestType;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ClientConnectDriver implements Runnable, RecipientClientRequests {
    private final FunctionsController functionsController;

    private final int userID;
    private final String userName;

    private final CCDController server;
    private final LogWriter logWriter;

    private final Socket socket;
    private final BufferedWriter bufferedWriter;
    private final BufferedReader bufferedReader;

    public ClientConnectDriver(
            Integer userID,
            String userName,
            Socket socket,
            CCDController server,
            LogWriter logWriter
    ) throws IOException {
        this.functionsController = new FunctionsManager(this);

        this.socket = socket;
        this.server = server;
        this.logWriter = logWriter;

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

    public int getUserID() {
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
            this.logWriter.addLog(e.toString());

            closeConnection();
        }
    }

    @Override
    public void sendToClientChatHistory(){
        server.sendChatHistoryToClient(this);

        this.logWriter.addLog("The chat history has been sent");
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

        this.logWriter.addLog(
                String.format(
                        "the client %s(%s) sent the message",
                        this.userName,
                        this.userID
                )
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
                this.logWriter.addLog(e.toString());
                closeConnection();
            }
        }
    }

    @Override
    public void closeConnection(){
        try {
            if (!this.socket.isClosed()){
                this.server.delClientFromList(this);

                this.socket.close();
                this.logWriter.addLog(
                        String.format(
                                "The socket of %s(%s) was closed.",
                                this.userName,
                                this.userID
                        )
                );

                this.bufferedWriter.close();
                this.logWriter.addLog(
                        String.format(
                                "The bufferedWriter of %s(%s) was closed.",
                                this.userName,
                                this.userID
                        )
                );

                this.bufferedReader.close();
                this.logWriter.addLog(
                        String.format(
                                "The bufferedReader of %s(%s) was closed.",
                                this.userName,
                                this.userID
                        )
                );
            }

        } catch (IOException e) {
            this.logWriter.addLog(e.toString());
        }

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        ClientConnectDriver clientConnectDriver = (ClientConnectDriver) obj;

        return this.userID == clientConnectDriver.getUserID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.userID);
    }
}
