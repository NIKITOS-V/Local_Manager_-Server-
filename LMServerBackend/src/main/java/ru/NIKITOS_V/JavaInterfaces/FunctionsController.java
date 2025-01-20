package ru.NIKITOS_V.JavaInterfaces;

import ru.NIKITOS_V.RequestTypes.ClientRequestType;

import java.io.IOException;

public interface FunctionsController {
    void execute(ClientRequestType requestType) throws IOException;
}
