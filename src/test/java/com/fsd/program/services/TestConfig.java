/**
 * 
 */
package com.fsd.program.services;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.fsd.program.entity.User;
import com.fsd.program.repo.UserRepository;

/**
 * @author kj
 *
 */
@Configuration
@Profile("junit")
@ComponentScan(basePackages = { "com.fsd.program.services" })
public class TestConfig {

	@Bean
	@Primary
	public UserRepository userRepository() {
		UserRepository mock = Mockito.mock(UserRepository.class);
		List<User> userList = new ArrayList<>();
		Mockito.when(mock.findAll()).thenReturn(userList);
		return mock;
	}

}
