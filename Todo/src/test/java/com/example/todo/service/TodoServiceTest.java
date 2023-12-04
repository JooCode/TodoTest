package com.example.todo.service;

import com.example.todo.dto.TodoRequestDto;
import com.example.todo.dto.TodoResponseDto;
import com.example.todo.entity.Todo;
import com.example.todo.entity.User;
import com.example.todo.repository.TodoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    // GET
    @Test
    public void testGetTodo() {
        // given
        Todo Todo = new Todo();
        Todo.setId(1L);
        Todo.setTitle("Test Todo");

        when(todoRepository.findById(1L)).thenReturn(Optional.of(Todo));

        // when
        Todo result = todoService.getTodo(1L);

        // then
        assertEquals(Todo.getId(), result.getId());
        assertEquals(Todo.getTitle(), result.getTitle());
        verify(todoRepository, times(1)).findById(1L);
    }

    //Post
    @Test
    public void testPostTodo() {
        // given
        TodoRequestDto todoRequestDto = new TodoRequestDto();
        User user = new User(); // 적절한 유저 정보 입력

        // Mock 객체를 사용하여 save 메소드가 호출됐을 때 예상되는 행동 정의
        Todo expectedTodo = new Todo();
        Mockito.when(todoRepository.save(Mockito.any(Todo.class))).thenReturn(expectedTodo);

        // When
        TodoResponseDto responseDto = todoService.createTodo(todoRequestDto, user);

        // Then
        // 반환된 responseDto가 null이 아닌지 확인
        Assertions.assertNotNull(responseDto);

        // 반환된 responseDto의 내용이 예상한 Todo의 정보와 일치하는지 확인
        Assertions.assertEquals(expectedTodo.getId(), responseDto.getId());
    }
    //Update
    @Test
    public void testUpdateTodo() {
        //Given
        Long todoId = 1L; // 수정할 Todo의 ID
        TodoRequestDto todoRequestDto = new TodoRequestDto();
        todoRequestDto.setTitle("Updated Title");
        todoRequestDto.setContent("Updated Content");

        User user = new User(); // 적절한 유저 정보 입력

        // Mock 객체를 사용하여 findById와 save 메소드가 호출됐을 때 예상되는 행동 정의
        Todo existingTodo = new Todo();
        Mockito.when(todoRepository.findById(todoId)).thenReturn(Optional.of(existingTodo));
        Mockito.when(todoRepository.save(Mockito.any(Todo.class))).thenReturn(existingTodo);

        // When
        TodoResponseDto responseDto = todoService.updateTodo(todoId, todoRequestDto, user);

        // Then
        // 반환된 responseDto가 null이 아닌지 확인
        Assertions.assertNotNull(responseDto);

        // 반환된 responseDto의 내용이 예상한 Todo의 정보와 일치하는지 확인
        Assertions.assertEquals(todoRequestDto.getTitle(), responseDto.getTitle());
        Assertions.assertEquals(todoRequestDto.getContent(), responseDto.getContent());
    }

    //Complete
    @Test
    public void testCompleteTodo() {
        // Given
        Long todoId = 1L; // 완료할 Todo의 ID
        User user = new User(); // 사용자 정보 설정

        Todo existingTodo = new Todo();

        // Mock 객체를 사용하여 getUserTodo 메소드가 호출됐을 때 예상되는 행동 정의
        Mockito.when(todoService.getUserTodo(todoId, user)).thenReturn(existingTodo);

        // When
        TodoResponseDto responseDto = todoService.competeTodo(todoId, user);

        // Then
        // 반환된 responseDto가 null이 아닌지 확인
        Assertions.assertNotNull(responseDto);

    }
}
