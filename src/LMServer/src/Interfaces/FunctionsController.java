package Interfaces;

import RequestTypes.ClientRequestType;

import java.io.IOException;

public interface FunctionsController {
    void execute(ClientRequestType requestType) throws IOException;
}
