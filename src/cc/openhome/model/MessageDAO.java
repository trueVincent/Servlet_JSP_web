package cc.openhome.model;

import java.util.List;

public interface MessageDAO {
    List<Message> MessagesBy(String username);
    Message MessageBy(String username, String title);
    void createMessage(Message message);
    void deleteMessageBy(String username, String title, String millis);
}
