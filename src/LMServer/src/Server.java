import Interfaces.CCDController;
import RequestTypes.ClientRequestType;
import Formating.MessageData;
import ClientConnectDriver.ClientConnectDriver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Server implements CCDController {
    private static Integer LAST_USER_ID = 0;
    private static final String SERVER_NAME = "SERVER";

    private final List<ClientConnectDriver> clientConnectDriverList;

    private final List<MessageData> messageDataList;

    private ServerSocket serverSocket;

    public Server(){
        this.clientConnectDriverList = new LinkedList<>();
        this.messageDataList = new LinkedList<>();
    }

    public void startServer(){
        try {
            this.serverSocket = new ServerSocket(5050);

            startListeningThread();

        } catch (IOException e){
            e.printStackTrace();
            stopServer();
        }
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

                        addClient(userName, socket);

                        sendServerMessageToClient(
                                String.format(
                                        "Пользователь %s подключился к чату",
                                        userName
                                )
                        );
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void addClient(String userName, Socket socket) throws IOException {
        System.out.println("new socket");
        ClientConnectDriver clientConnectDriver = new ClientConnectDriver(
                LAST_USER_ID++,
                userName,
                socket,
                this
        );

        new Thread(clientConnectDriver).start();

        this.clientConnectDriverList.add(clientConnectDriver);

        System.out.println(this.clientConnectDriverList);
    }

    public void stopServer(){
        System.out.println("stop server");

        try {
            if (this.serverSocket != null) {

                serverSocket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delClientFromList(ClientConnectDriver clientConnectDriver) {
        synchronized (this.clientConnectDriverList){
            this.clientConnectDriverList.remove(clientConnectDriver);
        }

        sendServerMessageToClient(
                String.format(
                        "Пользователь %s покинул чат",
                        clientConnectDriver.getUserName()
                )
        );

        System.out.println(this.clientConnectDriverList);
    }

    @Override
    public void sendMessageToClients(Integer userID, String userName, List<String> linesFromMessage) {
        synchronized (this.messageDataList){
            this.messageDataList.add(
                new MessageData(userID, userName, linesFromMessage)
            );
        }

        synchronized (this.clientConnectDriverList){
            for (ClientConnectDriver clientConnectDriver: this.clientConnectDriverList){
                clientConnectDriver.sendMessageToClient(userName, linesFromMessage);
            }
        }

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
        synchronized (this.messageDataList){
            for (MessageData messageData: this.messageDataList){
                clientConnectDriver.sendMessageToClient(
                        messageData.getUserName(), messageData.getLinesFromMessage()
                );
            }
        }
    }
}
