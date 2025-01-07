package Interfaces;

public interface BaseServer {
    boolean startServer(int port);
    boolean stopServer();
    void setMSBinder(RecipientMessages msBinder);
}
