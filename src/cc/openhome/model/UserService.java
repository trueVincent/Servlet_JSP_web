package cc.openhome.model;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class UserService {
    private final AccountDAO acctDAO;
    private final PhotoDAO photoDAO;
    private final MessageDAO messageDAO;

    public UserService(AccountDAO acctDAO, PhotoDAO photoDAO, MessageDAO messageDAO) {
        this.acctDAO = acctDAO;
        this.photoDAO = photoDAO;
        this.messageDAO = messageDAO;
    }

    public Optional<Account> tryCreateUser(String email, String username, String password)  {
        if(emailExisted(email) || userExisted(username)) {
            return Optional.empty();
        }
        return Optional.of(createUser(username, email, password));
    }

    private Account createUser(String username, String email, String password) {
        int salt = (int) (Math.random() * 100);
        int encrypt = salt + password.hashCode();
        Account acct = new Account(username, email, String.valueOf(encrypt), String.valueOf(salt));
        acctDAO.createAccount(acct);
        return acct;
    }
    
    public boolean userExisted(String username) {
    	return acctDAO.accountByUsername(username).isPresent();
    }
    
    public boolean emailExisted(String email) {
    	return acctDAO.accountByEmail(email).isPresent();
    }
    
    public Optional<Account> verify(String email, String token){
    	Optional<Account> optionalAcct = acctDAO.accountByEmail(email);
    	if(optionalAcct.isPresent()) {
    		Account acct = optionalAcct.get();
    		if(acct.getPassword().equals(token)) {
    			acctDAO.activateAccount(acct);
    			return Optional.of(acct);
    		}
    	}
    	return Optional.empty();
    }
    
    public Optional<String> encryptedPassword(String username, String password) {
        Optional<Account> optionalAcct = acctDAO.accountByUsername(username);
        if(optionalAcct.isPresent()) {
            Account acct = optionalAcct.get();
            int salt = Integer.parseInt(acct.getSalt());
            return Optional.of(String.valueOf(password.hashCode() + salt));
        }
        return Optional.empty();
    }

    public boolean login(String username, String password) {
        if(username == null || username.trim().length() == 0) {
            return false;
        }
        
        Optional<Account> optionalAcct = acctDAO.accountByUsername(username);
        if(optionalAcct.isPresent()) {
            Account acct = optionalAcct.get();
            int encrypt = Integer.parseInt(acct.getPassword());
            int salt = Integer.parseInt(acct.getSalt());
            return password.hashCode() + salt == encrypt;
        }
        return false;
    }
    
    public Optional<Account> accountByNameEmail(String name, String email){
    	Optional<Account> optionalAcct = acctDAO.accountByUsername(name);
    	if(optionalAcct.isPresent() && optionalAcct.get().getEmail().equals(email)) {
    		return optionalAcct;
    	}
    	return Optional.empty();
    }
    
    public void resetPassword(String name, String password) {
    	int salt = (int) (Math.random() * 100);
    	int encrypt = salt + password.hashCode();
    	acctDAO.updatePasswordSalt(name, String.valueOf(encrypt), String.valueOf(salt));
    }
    
    public List<Photo> photos(String username, String title) {
        List<Photo> photos = photoDAO.PhotosBy(username, title);
        photos.sort(Comparator.comparing(Photo::getMillis).reversed());
        return photos;
    }   
    
    public void addPhoto(String username, String title) {
        photoDAO.createPhoto(
                new Photo(
                        username, Instant.now().toEpochMilli(), title, null));
    }    
    
    public void deletePhoto(String username, String millis) {
    	photoDAO.deletePhotoBy(username, millis);
    }
    
    public boolean exist(String username) {
        return acctDAO.accountByUsername(username).isPresent();
    }
    
    public List<Photo> newestPhotos(int n) {
        return photoDAO.newestPhotos(n);
    }
    
    public List<Message> messages(String username){
    	List<Message> messages = messageDAO.MessagesBy(username);
    	messages.sort(Comparator.comparing(Message::getMillis).reversed());
    	return messages;
    }
    
    public Message message(String username, String title) {
    	Message message = messageDAO.MessageBy(username, title);
    	return message;
    }
    
    public void addMessage(String username, String title, String message) {
    	messageDAO.createMessage(
    			new Message(
    					username, title, Instant.now().toEpochMilli(), message));
    }
    
    public void deleteMessage(String username, String title, String millis) {
    	messageDAO.deleteMessageBy(username, title, millis);
    }
}
