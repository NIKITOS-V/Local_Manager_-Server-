import Formating.Save;
import Interfaces.*;
import RequestTypes.ClientRequestType;
import Formating.MessageData;
import ClientConnectDriver.ClientConnectDriver;
import Writers.ClassSaver;
import Writers.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Server implements CCDController, BaseServer {
    private static int LAST_USER_ID = 0;
    private static final String SERVER_NAME = "SERVER";
    private static final String SAVE_NAME = "chatHistory";
    private static final int SERVER_ID = -1;

    private final List<ClientConnectDriver> clientConnectDriverList;
    private final List<MessageData> chatHistory;

    private ServerSocket serverSocket;

    private RecipientMessages msBinder;

    private final ClassWriter classWriter;
    private final LogWriter logWriter;

    private boolean chatHistorySendToGUI;

    public Server() throws IOException {
        this.classWriter = new ClassSaver("Saves");
        this.logWriter = new Logger("Logs");

        this.clientConnectDriverList = new LinkedList<>();

        List<MessageData> uploadChatHistory = new LinkedList<>();

        try {
            uploadChatHistory = ((Save) this.classWriter.openObject(SAVE_NAME)).getChatHistory();

            this.logWriter.addLog("The chat history is uploaded.");

        } catch (Exception e){
            this.logWriter.addLog(e.toString());
        }

        this.chatHistory = uploadChatHistory;

        this.logWriter.addLog(
                String.format(
                        "The %s class has been launched.",
                        this.getClass().getSimpleName()
                )
        );

        this.chatHistorySendToGUI = false;
    }

    @Override
    public boolean startServer(int port){
        try {
            if (this.serverSocket == null || this.serverSocket.isClosed()){
                this.serverSocket = new ServerSocket(port);

                startListeningThread();

                this.logWriter.addLog("The server was running.");

                if (!this.chatHistorySendToGUI) {
                    for (MessageData messageData : this.chatHistory) {
                        StringBuilder message = new StringBuilder();

                        for (String line : messageData.getLinesFromMessage()) {
                            message.append(line).append("\n");
                        }

                        this.msBinder.accept_message(
                                messageData.getUserID(),
                                messageData.getUserName(),
                                message.toString()
                        );
                    }

                    this.chatHistorySendToGUI = true;

                }

                sendServerMessageToGUI("Сервер запущен");
            }
        } catch (IOException e){
            this.logWriter.addLog(e.toString());

            stopServer();

            return false;
        }

        return true;
    }

    private void startListeningThread(){
        new Thread(() ->{
            while (!this.serverSocket.isClosed()){
                String requestType;
                String userName;

                try {
                    Socket socket = serverSocket.accept();

                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(
                                    socket.getInputStream(),
                                    StandardCharsets.UTF_8
                            )
                    );

                    requestType = bufferedReader.readLine();

                    if (requestType.equals(String.valueOf(ClientRequestType.connect))){
                        userName = bufferedReader.readLine();

                        this.logWriter.addLog(String.format(
                                "the user %s has connected.",
                                userName
                                )
                        );

                        addClient(userName, socket);

                        sendServerMessageToClient(
                                String.format(
                                        "Пользователь %s подключился к чату",
                                        userName
                                )
                        );
                    }

                } catch (IOException e) {
                    this.logWriter.addLog(e.toString());
                }
            }
        }).start();

        this.logWriter.addLog("The message acceptance thread has been started.");
    }

    private void addClient(String userName, Socket socket) throws IOException {
        System.out.println("new socket");
        ClientConnectDriver clientConnectDriver = new ClientConnectDriver(
                LAST_USER_ID++,
                userName,
                socket,
                this,
                this.logWriter
        );

        new Thread(clientConnectDriver).start();

        this.clientConnectDriverList.add(clientConnectDriver);

        this.logWriter.addLog(String.format(
                "the user %s has been added to the list.",
                userName
                )
        );
    }

    @Override
    public boolean stopServer(){
        boolean serverStop = false;

        try {
            if (this.serverSocket != null && !this.serverSocket.isClosed()) {
                for (ClientConnectDriver clientConnectDriver: this.clientConnectDriverList){
                    clientConnectDriver.closeConnection();
                }

                this.clientConnectDriverList.clear();
                this.logWriter.addLog("the list of users has been cleared.");

                this.serverSocket.close();
                this.logWriter.addLog("the server has been stopped.");

                sendServerMessageToGUI("Сервер остановлен");

                serverStop = true;
            }

        } catch (IOException e) {
            this.logWriter.addLog(e.toString());
        }

        try {
            this.classWriter.saveObject(new Save(this.chatHistory), SAVE_NAME);
            this.logWriter.addLog("The chat history has been saved");

        } catch (Exception e){
            this.logWriter.addLog(e.toString());
        }

        return serverStop;
    }

    @Override
    public void setMSBinder(RecipientMessages msBinder) {
        this.msBinder = msBinder;

        this.logWriter.addLog("The binding class has been accepted");
    }

    @Override
    public void clearChat() {
        this.chatHistory.clear();

        this.logWriter.addLog("chat history cleared");
    }

    @Override
    public void delClientFromList(ClientConnectDriver clientConnectDriver) {
        synchronized (this.clientConnectDriverList){
            this.clientConnectDriverList.remove(clientConnectDriver);

            this.logWriter.addLog("The user has been removed from the list");

            sendServerMessageToClient(
                String.format(
                        "Пользователь %s покинул чат",
                        clientConnectDriver.getUserName()
                )
            );
        }
    }

    @Override
    public void sendMessageToClients(int userID, String userName, List<String> linesFromMessage) {
        synchronized (this.chatHistory){
            this.chatHistory.add(
                new MessageData(userID, userName, linesFromMessage)
            );

            StringBuilder message = new StringBuilder();

            for (String line: linesFromMessage){
                message.append(line).append("\n");
            }

            this.msBinder.accept_message(
                    userID,
                    userName,
                    message.toString()
            );
        }

        synchronized (this.clientConnectDriverList){
            for (ClientConnectDriver clientConnectDriver: this.clientConnectDriverList){
                clientConnectDriver.sendMessageToClient(userName, linesFromMessage);
            }
        }
    }

    private void sendServerMessageToGUI(String message){
        this.chatHistory.add(
                new MessageData(
                        SERVER_ID,
                        SERVER_NAME,
                        Collections.singletonList(message)
                )
        );

        this.msBinder.accept_message(
                SERVER_ID,
                SERVER_NAME,
                message + "\n"
        );
    }

    private void sendServerMessageToClient(String message){
        sendMessageToClients(
                -1,
                SERVER_NAME,
                Collections.singletonList(message)
        );
    }


    @Override
    public void sendChatHistoryToClient(ClientConnectDriver clientConnectDriver) {
        synchronized (this.chatHistory){
            for (MessageData messageData: this.chatHistory){
                clientConnectDriver.sendMessageToClient(
                        messageData.getUserName(), messageData.getLinesFromMessage()
                );
            }
        }
    }
}
