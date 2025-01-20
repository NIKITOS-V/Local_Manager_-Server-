package ru.NIKITOS_V.JavaInterfaces;

import java.io.IOException;

public interface RecipientClientRequests {
    void sendToClientChatHistory();
    void sendMessageToClients() throws IOException, NumberFormatException;
    void closeConnection();
}
