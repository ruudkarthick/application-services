/**
 * 
 */
package com.fsd.program.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fsd.program.entity.User;
import com.fsd.program.repo.UserRepository;

/**
 * @author kj
 *
 */
@RestController
@RequestMapping("/users")
public class UserServices {

	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/getAllUsers")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@RequestMapping("/addUpdate")
	public List<User> addUpdateUser(@RequestBody User user) {
		userRepository.save(user);
		return userRepository.findAll();
	}
	
	@RequestMapping("/deleteUser")
	public List<User> deleteUser(@RequestBody User user) {
		userRepository.delete(user);
		return userRepository.findAll();
	}

}
