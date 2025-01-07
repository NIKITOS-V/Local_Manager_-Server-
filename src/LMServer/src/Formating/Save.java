package Formating;

import java.io.Serializable;
import java.util.List;

public class Save implements Serializable {
    private final List<MessageData> chatHistory;

    public Save(List<MessageData> chatHistory){
        this.chatHistory = chatHistory;
    }

    public List<MessageData> getChatHistory() {
        return this.chatHistory;
    }
}
