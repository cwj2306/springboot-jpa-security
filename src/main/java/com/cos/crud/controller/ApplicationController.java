package com.cos.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.crud.model.MyUser;
import com.cos.crud.repository.MyUserRepository;
import com.cos.crud.security.MyUserDetails;

@Controller
public class ApplicationController {
	
	@Autowired //DI
	private MyUserRepository mRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping("/home")
	public @ResponseBody String home() {
		return "<h1>HOME</h1>";
	}
	

	@GetMapping("/user/joinForm")
	public String join() {
		return "joinForm";
	}
	
	@GetMapping("/user/loginForm")
	public String loginForm() {
		return "loginForm";
	}
	
	//1. csrf 설정
	//2. password 인코딩
	// 위 두가지를 설정해주어야 함
	@PostMapping("/user/create")
	public String create(MyUser user) { // jackson bind 라이브러리가
		
		// 시큐리티를 적용하면 password 암호화 
		String rawPassword = user.getPassword();
		String encPassword =passwordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		
		mRepo.save(user);
		return "redirect:/home";
	}
	
	
	@GetMapping("/admin/test")
	public @ResponseBody String adminTest(@AuthenticationPrincipal MyUserDetails userDetails) {
		StringBuffer sb = new StringBuffer();
		sb.append("id : " + userDetails.getUser().getId() + "<br/>");
		sb.append("id : " + userDetails.getUsername() + "<br/>");
		sb.append("id : " + userDetails.getPassword() + "<br/>");
		sb.append("id : " + userDetails.getUser().getEmail() + "<br/>");

		return sb.toString();
	}
}
