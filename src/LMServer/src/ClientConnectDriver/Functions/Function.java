package ClientConnectDriver.Functions;

import Interfaces.Executable;
import Interfaces.RecipientClientRequests;

import java.io.IOException;

public abstract class Function implements Executable {
    protected RecipientClientRequests recipient;

    public Function(RecipientClientRequests recipient){
        this.recipient = recipient;
    }

    @Override
    public abstract void execute() throws IOException;
}
