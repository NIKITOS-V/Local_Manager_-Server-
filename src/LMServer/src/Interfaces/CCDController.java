package Interfaces;

import ClientConnectDriver.ClientConnectDriver;

import java.util.List;

public interface CCDController {
    void delClientFromList(ClientConnectDriver clientConnectDriver);
    void sendMessageToClients(Integer userID, String userName, List<String> linesFromMessage);
    void sendChatHistoryToClient(ClientConnectDriver clientConnectDriver);
}
