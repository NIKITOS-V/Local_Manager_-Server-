package ClientConnectDriver.Functions;

import Interfaces.RecipientClientRequests;

import java.io.IOException;

public class CloseConnection extends Function{
    public CloseConnection(RecipientClientRequests recipient) {
        super(recipient);
    }

    @Override
    public void execute() throws IOException {
        this.recipient.closeConnection();
    }
}
