package com.example.todo.dto;




import com.example.todo.entity.Todo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class TodoResponseDto extends CommonResponseDto {
	private Long id;
	private String title;
	private String content;
	private Boolean isCompleted;
	private UserDto user;
	private LocalDateTime createDate;

	public TodoResponseDto(Todo todo) {
		this.id = todo.getId();
		this.title = todo.getTitle();
		this.content = todo.getContent();
		this.isCompleted = todo.getIsCompleted();
		this.user = new UserDto(todo.getUser());
		this.createDate = todo.getCreateDate();
	}
}
