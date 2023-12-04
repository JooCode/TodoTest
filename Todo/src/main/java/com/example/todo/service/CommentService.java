package com.example.todo.service;

import com.example.todo.dto.CommentRequestDto;
import com.example.todo.dto.CommentResponseDto;
import com.example.todo.entity.Comment;
import com.example.todo.entity.Todo;
import com.example.todo.entity.User;
import com.example.todo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;
	private final TodoService todoService;

	public CommentResponseDto createComment(CommentRequestDto dto, User user) {
		Todo todo = todoService.getTodo(dto.getTodoId());

		Comment comment = new Comment(dto);
		comment.setUser(user);
		comment.setTodo(todo);

		commentRepository.save(comment);

		return new CommentResponseDto(comment);
	}

	@Transactional
	public CommentResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDTO, User user) {
		Comment comment = getUserComment(commentId, user);

		comment.setText(commentRequestDTO.getText());

		return new CommentResponseDto(comment);
	}

	public void deleteComment(Long commentId, User user) {
		Comment comment = getUserComment(commentId, user);

		commentRepository.delete(comment);
	}

	private Comment getUserComment(Long commentId, User user) {
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글 ID 입니다."));

		if(!user.getId().equals(comment.getUser().getId())) {
			throw new RejectedExecutionException("작성자만 수정할 수 있습니다.");
		}
		return comment;
	}

}
