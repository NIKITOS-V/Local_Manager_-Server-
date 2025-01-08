package ClientConnectDriver.Functions;

import Interfaces.RecipientClientRequests;

import java.io.IOException;

public class SendToClientChatHistory extends Function{
    public SendToClientChatHistory(RecipientClientRequests recipient) {
        super(recipient);
    }

    @Override
    public void execute() throws IOException {
        this.recipient.sendToClientChatHistory();
    }
}
