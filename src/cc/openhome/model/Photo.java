package cc.openhome.model;

import java.time.*;

public class Photo {
    private String username;
    private Long millis;
    private String title;
    private String photoPath;
    
    public Photo(String username, Long millis,String title, String photoPath) {
        this.username = username;
        this.millis = millis;
        this.title = title;
        this.photoPath = photoPath;
    }

    public String getUsername() {
        return username;
    }

    public Long getMillis() {
        return millis;
    }
    
    public String getTitle() {
    	return title;
    }
    
    public String getPhotoPath() {
    	return photoPath;
    }
    
    public LocalDateTime getLocalDateTime() {
        return Instant.ofEpochMilli(millis)
                      .atZone(ZoneId.of("Asia/Taipei"))
                      .toLocalDateTime();
    }
}
