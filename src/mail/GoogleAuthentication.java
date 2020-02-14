package mail;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class GoogleAuthentication extends Authenticator {
	PasswordAuthentication passAuth;
    
    public GoogleAuthentication(){
        passAuth = new PasswordAuthentication("1215lhj@gmail.com", "ciibwtveuaxbqtut");
    }
 
    public PasswordAuthentication getPasswordAuthentication() {
        return passAuth;
    }
}
