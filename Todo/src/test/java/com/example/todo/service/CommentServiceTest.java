package com.example.todo.service;

import com.example.todo.dto.CommentRequestDto;
import com.example.todo.dto.CommentResponseDto;
import com.example.todo.entity.Comment;
import com.example.todo.entity.Todo;
import com.example.todo.entity.User;
import com.example.todo.repository.CommentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private TodoService todoService;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // 댓글 작성
    @Test
    public void testCreateComment() {
        // Given
        CommentRequestDto commentRequestDto = new CommentRequestDto();
        commentRequestDto.setTodoId(1L); // 적절한 할 일 ID 설정
        User user = new User(); // 적절한 사용자 정보 설정
        Todo todo = new Todo(); // 할 일 객체 설정

        Mockito.when(todoService.getTodo(commentRequestDto.getTodoId())).thenReturn(todo);

        // When
        CommentResponseDto responseDto = commentService.createComment(commentRequestDto, user);

        // Then
        Assertions.assertNotNull(responseDto);
        // 적절한 반환 값 검증 등을 수행할 수 있습니다.
    }
    // 댓글 수정
    @Test
    public void testUpdateComment() {
        // Given
        Long commentId = 1L; // 수정할 댓글 ID 설정
        CommentRequestDto commentRequestDto = new CommentRequestDto();
        User user = new User(); // 적절한 사용자 정보 설정
        Comment comment = new Comment(); // 적절한 댓글 객체 설정

        Mockito.when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        // When
        CommentResponseDto responseDto = commentService.updateComment(commentId, commentRequestDto, user);
        // Then
        Assertions.assertNotNull(responseDto);
        // 적절한 반환 값 검증 등을 수행할 수 있습니다.
    }
    // 댓글 삭제
    @Test
    public void testDeleteComment() {
        // Given
        Long commentId = 1L; // 삭제할 댓글 ID 설정
        User user = new User(); // 적절한 사용자 정보 설정
        Comment comment = new Comment(); // 적절한 댓글 객체 설정

        Mockito.when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        // When
        commentService.deleteComment(commentId, user);

        // Then
        Mockito.verify(commentRepository, Mockito.times(1)).delete(comment);
        // 삭제된 댓글에 대한 추가적인 검증 등을 수행할 수 있습니다.
    }
}
