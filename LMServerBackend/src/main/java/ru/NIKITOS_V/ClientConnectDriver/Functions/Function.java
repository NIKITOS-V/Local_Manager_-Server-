package ru.NIKITOS_V.ClientConnectDriver.Functions;


import ru.NIKITOS_V.JavaInterfaces.Executable;
import ru.NIKITOS_V.JavaInterfaces.RecipientClientRequests;

import java.io.IOException;

public abstract class Function implements Executable {
    protected RecipientClientRequests recipient;

    public Function(RecipientClientRequests recipient){
        this.recipient = recipient;
    }

    @Override
    public abstract void execute() throws IOException;
}
