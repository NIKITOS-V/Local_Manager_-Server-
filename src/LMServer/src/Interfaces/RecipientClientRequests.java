package Interfaces;

import java.io.IOException;

public interface RecipientClientRequests {
    void sendToClientChatHistory();
    void sendMessageToClients() throws IOException, NumberFormatException;
    void closeConnection();
}
