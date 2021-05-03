package cc.openhome.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class PhotoDAOJdbcImpl implements PhotoDAO {
	private DataSource dataSource;
    
    public PhotoDAOJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    @Override
    public List<Photo> PhotosBy(String username, String title) {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM t_photo WHERE name = ? AND title = ?")) {
            stmt.setString(1, username);
            stmt.setString(2, title);
            ResultSet rs = stmt.executeQuery();
            
            List<Photo> photos = new ArrayList<>();
            while(rs.next()) {
            	photos.add(new Photo(
                    rs.getString(1),
                    rs.getLong(2),
                    rs.getString(3),
                    Integer.toString(rs.getInt(4)))
                );
            }
            return photos;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void createPhoto(Photo photo) {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO t_photo(name, time, title) VALUES(?, ?, ?)")) {
            stmt.setString(1, photo.getUsername());
            stmt.setLong(2, photo.getMillis());
            stmt.setString(3, photo.getTitle());
            stmt.executeUpdate();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deletePhotoBy(String username, String millis) {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM t_photo WHERE name = ? AND time = ?")) {
            stmt.setString(1, username);
            stmt.setLong(2, Long.parseLong(millis));
            stmt.executeUpdate();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public List<Photo> newestPhotos(int n) {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM t_photo ORDER BY time DESC LIMIT ?")) {
        	stmt.setInt(1, n);
        	ResultSet rs = stmt.executeQuery();
            
            List<Photo> photos = new ArrayList<>();
            while(rs.next()) {
                photos.add(new Photo(
                    rs.getString(1),
                    rs.getLong(2),
                    rs.getString(3),
                    Integer.toString(rs.getInt(4))
                ));
            }
            return photos;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
