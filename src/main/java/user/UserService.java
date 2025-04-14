package user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import monetization.APIPlan;
import repository.UserRepo;

@Service
public class UserService {


	@Autowired
	private PasswordEncoder passwordencoder;
	
	@Autowired
	private UserRepo userrepo;
	
	public ResponseEntity<?> addUser(User user) {
		
		user.setPassword(passwordencoder.encode(user.getPassword()));
		
		
        user.setApiPlan(APIPlan.FREE);
		
		userrepo.save(user);
		return ResponseEntity.ok("User with "+user+" Info has been saved");
	}
	
	public ResponseEntity<?> getUserByUsername(String username) {
        return ResponseEntity.ok(userrepo.findByUsername(username));
    }

    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userrepo.findAll());
    }
    
    public ResponseEntity<?> LoginService(LoginReq loginreq){
    	
    	String username1 = loginreq.getUsername();
    	Optional<User> Temp = userrepo.findByUsername(username1);
    	
    	if (Temp.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username");
        }
    	
    	User userTemp = Temp.get();
    	
    	if (passwordencoder.matches(loginreq.getPassword(), userTemp.getPassword())) {
            return ResponseEntity.ok("Login successful");
    }
    	else {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
    	}
    }
}
