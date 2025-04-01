package controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/apisecurity")
public class Redirector {

	public Redirector() {
		super();
	}
	
	@GetMapping("/public/initialization")
	public Map<String, String> getMethodName() {
		return Map.of("message", "API Gateway is running!");
	}
	
	@GetMapping("/private/data")
	public String getMethodName1() {
		return new String("auth working");
	}
	
}
