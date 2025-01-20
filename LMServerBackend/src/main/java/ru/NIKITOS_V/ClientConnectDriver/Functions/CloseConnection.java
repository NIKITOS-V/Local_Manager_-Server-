package ru.NIKITOS_V.ClientConnectDriver.Functions;


import ru.NIKITOS_V.JavaInterfaces.RecipientClientRequests;

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
