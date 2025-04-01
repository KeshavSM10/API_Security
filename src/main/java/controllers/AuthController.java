package controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import authentication.JWTUtil;

//The login API takes a POST request with username and password. If the credentials are valid (admin/admin), 
//it generates a JWT token using JwtUtil.generateToken(username). If the credentials are invalid, it returns an error message.

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final JWTUtil jwtutil;
	
	public AuthController(JWTUtil jwtutil) {
		
		this.jwtutil = jwtutil;
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> Credentials) {
		
		String username = Credentials.get("username");
		String password = Credentials.get("password");
		
		if("admin".equals(username) && "admin".equals(password)) {
			
			String token = jwtutil.GenerateToken(username);
			return ResponseEntity.ok(Map.of("token", token));
		}
		else {
			
			return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
		}
	}

}
