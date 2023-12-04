package com.example.todo.entity;

import com.example.todo.dto.TodoRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

public class TodoTest {
    private TodoRequestDto todoRequestDto = new TodoRequestDto();
    @Test
    public void givenTodoRequestDto() {
        // Given
        todoRequestDto.setTitle("Test Title");
        todoRequestDto.setContent("Test Content");

        // When
        Todo todo = new Todo(todoRequestDto);

        // Then
        assertNotNull(todo);
        assertEquals("Test Title", todo.getTitle());
        assertEquals("Test Content", todo.getContent());
        assertFalse(todo.getIsCompleted());
        assertNotNull(todo.getCreateDate());
    }
    // 제목 Test
    @DisplayName("Title update Test")
    @Test
    public void givenTodoTitleIsUpdated() {
        // Given
        Todo todo = new Todo();
        todo.setTitle("Initial Title");

        // When
        todo.setTitle("Updated Title");

        // Then
        assertEquals("Updated Title", todo.getTitle());
    }
    // 내용 test
    @DisplayName("Content Update Test")
    @Test
    public void givenTodoContent() {
        // Given
        Todo todo = new Todo();
        todo.setContent("Initial Content");

        // When
        todo.setContent("Updated Content");

        // Then
        assertEquals("Updated Content", todo.getContent());
    }
    // 성공 테스트
    @Test
    public void givenTodoCompletedIsTrue() {
        // Given
        Todo todo = new Todo();

        // When
        todo.complete();

        // Then
        assertTrue(todo.getIsCompleted());
    }
}