package cc.openhome.model;

import java.time.*;

public class Message {
    private String username;
    private String title;
    private Long millis;
    private String message;
    
    public Message(String username, String title, Long millis, String message) {
        this.username = username;
        this.title = title;
        this.millis = millis;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }
    
    public String getTitle() {
    	return title;
    }
    
    public Long getMillis() {
        return millis;
    }
    
    public String getMessage() {
    	return message;
    }
    
    public LocalDateTime getLocalDateTime() {
        return Instant.ofEpochMilli(millis)
                      .atZone(ZoneId.of("Asia/Taipei"))
                      .toLocalDateTime();
    }
}
