package com.example.todo.service;


import com.example.todo.entity.User;
import com.example.todo.repository.UserRepository;
import com.example.todo.security.UserDetailsImpl;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsService {

	private final UserRepository userRepository;

	public UserDetailsImpl getUserDetails(String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Not Found" + username));
		return new UserDetailsImpl(user);
	}

}