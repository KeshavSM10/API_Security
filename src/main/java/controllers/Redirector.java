package controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import user.LoginReq;
import user.User;
import user.UserService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/apisecurity")
public class Redirector {

	public Redirector() {
		super();
	}
	
	@Autowired
	private UserService userservice;
	
	@GetMapping("/public/initialization")
	public Map<String, String> getMethodName() {
		return Map.of("message", "API Gateway is running!");
	}
	
	@GetMapping("/private/data")
	public String AuthTesting() {
		return new String("auth working");
	}
	
	@PostMapping("/public/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        ResponseEntity<?> newUser = userservice.addUser(user);
        return newUser;
    }
	
	@GetMapping("/public/signin")
	public ResponseEntity<?> getMethodName(@RequestBody LoginReq loginRequest) {
		
		return userservice.LoginService(loginRequest);
	}
}
