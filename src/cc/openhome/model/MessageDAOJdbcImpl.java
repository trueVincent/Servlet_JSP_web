package cc.openhome.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class MessageDAOJdbcImpl implements MessageDAO {
	private DataSource dataSource;
    
    public MessageDAOJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    @Override
    public List<Message> MessagesBy(String username) {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM t_message WHERE name = ?")) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            List<Message> messages = new ArrayList<>();
            while(rs.next()) {
            	messages.add(new Message(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getLong(3),
                    rs.getString(4))
                );
            }
            return messages;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public Message MessageBy(String username, String title) {
    	try(Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM t_message WHERE name = ? AND title = ?")) {
                stmt.setString(1, username);
                stmt.setString(2, title);
                ResultSet rs = stmt.executeQuery();
                
                Message message = null;
                if(rs.next()) {
                	message = new Message(
                    		rs.getString(1),
                			rs.getString(2),
                			rs.getLong(3),
                			rs.getString(4));
                }
                return message;
            } catch(SQLException e) {
                throw new RuntimeException(e);
            }
    }
    
    @Override
    public void createMessage(Message message) {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO t_message(name, title, time, message) VALUES(?, ?, ?, ?)")) {
            stmt.setString(1, message.getUsername());
            stmt.setString(2, message.getTitle());
            stmt.setLong(3, message.getMillis());
            stmt.setString(4, message.getMessage());
            stmt.executeUpdate();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteMessageBy(String username, String title, String millis) {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM t_message WHERE name = ? AND title = ? AND time = ?")) {
            stmt.setString(1, username);
            stmt.setString(2, title);
            stmt.setLong(3, Long.parseLong(millis));
            stmt.executeUpdate();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
