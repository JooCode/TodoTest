package com.example.todo.controller;

import com.example.todo.dto.*;
import com.example.todo.security.UserDetailsImpl;
import com.example.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

@RequestMapping("/api/todos")
@RestController
@RequiredArgsConstructor
public class TodoController {

	private final TodoService todoService;

	@PostMapping
	public ResponseEntity<TodoResponseDto> postTodo(@RequestBody TodoRequestDto todoRequestDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		TodoResponseDto responseDTO = todoService.createTodo(todoRequestDTO, userDetails.getUser());

		return ResponseEntity.status(201).body(responseDTO);
	}

	@GetMapping("/{todoId}")
	public ResponseEntity<CommonResponseDto> getTodo(@PathVariable Long todoId) {
		try {
			TodoResponseDto responseDTO = todoService.getTodoDto(todoId);
			return ResponseEntity.ok().body(responseDTO);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
		}
	}

	@GetMapping
	public ResponseEntity<List<TodoListResponseDto>> getTodoList() {
		List<TodoListResponseDto> response = new ArrayList<>();

		Map<UserDto, List<TodoResponseDto>> responseDTOMap = todoService.getUserTodoMap();

		responseDTOMap.forEach((key, value) -> response.add(new TodoListResponseDto(key, value)));

		return ResponseEntity.ok().body(response);
	}


	@PutMapping("/{todoId}")
	public ResponseEntity<CommonResponseDto> putTodo(@PathVariable Long todoId, @RequestBody TodoRequestDto todoRequestDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		try {
			TodoResponseDto responseDTO = todoService.updateTodo(todoId, todoRequestDTO, userDetails.getUser());
			return ResponseEntity.ok().body(responseDTO);
		} catch (RejectedExecutionException | IllegalArgumentException ex) {
			return ResponseEntity.badRequest().body(new CommonResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
		}
	}


	@PatchMapping("/{todoId}/complete")
	public ResponseEntity<CommonResponseDto> patchTodo(@PathVariable Long todoId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		try {
			TodoResponseDto responseDTO = todoService.competeTodo(todoId, userDetails.getUser());
			return ResponseEntity.ok().body(responseDTO);
		} catch (RejectedExecutionException | IllegalArgumentException ex) {
			return ResponseEntity.badRequest().body(new CommonResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
		}
	}
}
