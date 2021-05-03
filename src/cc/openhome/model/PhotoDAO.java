package cc.openhome.model;

import java.util.List;

public interface PhotoDAO {
    List<Photo> PhotosBy(String username, String title);
    void createPhoto(Photo photo);
    void deletePhotoBy(String username, String millis);
    List<Photo> newestPhotos(int n);
}
