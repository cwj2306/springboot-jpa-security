package com.cos.crud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.crud.handler.MyLogoutSuccessHandler;

@Configuration
@EnableWebSecurity  //스프링 시큐리터 필터에 등록하는 어노테이션
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	// 1.
	// @Bean은 메서드에 붙여서 객체 생성시에 사용함
	@Bean
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}

	// 2.
	// 필터링
	@Override
   protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		
		http.authorizeRequests()
		.antMatchers("/board/list").permitAll()
		.antMatchers("/admin/**", "/board/**").authenticated() // 권한이 필요한 주소 설정
		.anyRequest().permitAll()
		.and()
		.formLogin()
//		.usernameParameter("username") User 클래스의 필드명 설정임 username, password가 디폴드 값임
//		.passwordParameter("password")
		.loginPage("/user/loginForm")
		.loginProcessingUrl("/user/loginProcess")
		.defaultSuccessUrl("/home")
		.and()
		.logout().logoutSuccessHandler(new MyLogoutSuccessHandler()); // 로그아웃 성공했을 때 특정 로직을 처리하고 넘어갈 수 있음
   }

	
	// 내가 인코딩하는게 아니라, 어떤 인코딩으로 패스워드가 만들어졌는지 알려주는 것.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(encodePWD());
	}

}