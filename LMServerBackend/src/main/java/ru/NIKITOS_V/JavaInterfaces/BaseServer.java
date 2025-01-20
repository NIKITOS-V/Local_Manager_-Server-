package ru.NIKITOS_V.JavaInterfaces;

import ru.NIKITOS_V.PyInterfaces.RecipientMessages;

public interface BaseServer {
    boolean startServer(int port);
    boolean stopServer();
    void setBinder(RecipientMessages binder);
    void clearChat();
}
