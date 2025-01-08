package Formating;

import java.io.Serializable;
import java.util.List;

public class MessageData implements Serializable {
    private final Integer userID;
    private final String userName;
    private final List<String> linesFromMessage;

    public MessageData(Integer userID, String userName, List<String> linesFromMessage){
        this.linesFromMessage = linesFromMessage;
        this.userID = userID;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public Integer getUserID() {
        return userID;
    }

    public List<String> getLinesFromMessage() {
        return linesFromMessage;
    }
}
