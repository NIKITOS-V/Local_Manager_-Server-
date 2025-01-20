package ru.NIKITOS_V.JavaInterfaces;

import ru.NIKITOS_V.ClientConnectDriver.ClientConnectDriver;

import java.util.List;

public interface CCDController {
    void delClientFromList(ClientConnectDriver clientConnectDriver);
    void sendMessageToClients(int userID, String userName, List<String> linesFromMessage);
    void sendChatHistoryToClient(ClientConnectDriver clientConnectDriver);
}
