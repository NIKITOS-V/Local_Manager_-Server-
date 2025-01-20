package ru.NIKITOS_V.ClientConnectDriver;

import ru.NIKITOS_V.ClientConnectDriver.Functions.CloseConnection;
import ru.NIKITOS_V.ClientConnectDriver.Functions.Function;
import ru.NIKITOS_V.ClientConnectDriver.Functions.SendMessageToClients;
import ru.NIKITOS_V.ClientConnectDriver.Functions.SendToClientChatHistory;
import ru.NIKITOS_V.JavaInterfaces.FunctionsController;
import ru.NIKITOS_V.JavaInterfaces.RecipientClientRequests;
import ru.NIKITOS_V.RequestTypes.ClientRequestType;

import java.io.IOException;
import java.util.LinkedHashMap;

public class FunctionsManager implements FunctionsController {
    private final LinkedHashMap<ClientRequestType, Function> functionHashMap;

    public FunctionsManager(RecipientClientRequests recipient){
        this.functionHashMap = new LinkedHashMap<>();

        fillMap(recipient);
    }

    private void fillMap(RecipientClientRequests recipient){
        this.functionHashMap.put(
                null,
                new CloseConnection(recipient)
        );

        this.functionHashMap.put(
                ClientRequestType.getChatHistory,
                new SendToClientChatHistory(recipient)
        );

        this.functionHashMap.put(
                ClientRequestType.sendMessage,
                new SendMessageToClients(recipient)
        );
    }

    @Override
    public void execute(ClientRequestType requestType) throws IOException {
        this.functionHashMap.get(requestType).execute();
    }
}
