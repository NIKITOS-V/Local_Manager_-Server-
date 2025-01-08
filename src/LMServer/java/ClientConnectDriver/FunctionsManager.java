package ClientConnectDriver;

import ClientConnectDriver.Functions.CloseConnection;
import ClientConnectDriver.Functions.Function;
import ClientConnectDriver.Functions.SendMessageToClients;
import ClientConnectDriver.Functions.SendToClientChatHistory;
import Interfaces.FunctionsController;
import Interfaces.RecipientClientRequests;
import RequestTypes.ClientRequestType;

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
