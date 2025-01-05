package ClientConnectDriver.Functions;

import Interfaces.RecipientClientRequests;

import java.io.IOException;

public class SendMessageToClients extends Function{
    public SendMessageToClients(RecipientClientRequests recipient) {
        super(recipient);
    }

    @Override
    public void execute() throws IOException {
        this.recipient.sendMessageToClients();
    }
}
