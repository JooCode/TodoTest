package com.example.todo.dto;

import com.example.todo.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
	private String username;

	public UserDto(User user) {
		this.username = user.getUsername();
	}
}
