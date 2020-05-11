package br.com.jp.esloc.apilost.resources;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jp.esloc.apilost.responce.Response;

@RestController
@RequestMapping("/")
public class IndexResource {
	
@GetMapping()
@Secured({"ROLE_ADMIN"})
public String home() {
	return "API - lost";
}

@GetMapping("/userinfo")
@Secured({"ROLE_ADMIN"})
public ResponseEntity<Response<UserDetails>> userInfo(@AuthenticationPrincipal UserDetails user) {
	Response<UserDetails> response = new Response<UserDetails>();
	
	if(user == null) {
		response.setErrors(Arrays.asList("User not found"));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
	response.setData(user);
	
	return ResponseEntity.ok().body(response);
		
}
}
