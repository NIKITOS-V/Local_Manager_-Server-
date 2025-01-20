package ru.NIKITOS_V.ClientConnectDriver.Functions;

import ru.NIKITOS_V.JavaInterfaces.RecipientClientRequests;

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
