package com.example.todo.entity;

import com.example.todo.dto.CommentRequestDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommentTest {
    private CommentRequestDto commentRequestDto = new CommentRequestDto();

    @Test
    public void whenCreateComment() {
        // Given
        commentRequestDto.setText("Test Comment");

        // When
        Comment comment = new Comment(commentRequestDto);

        // Then
        assertNotNull(comment);
        assertEquals("Test Comment", comment.getText());
        assertNotNull(comment.getCreateDate());
    }

    @Test
    public void whenSetUser() {
        // Given
        Comment comment = new Comment();
        User user = new User();
        user.getUsername();

        // When
        comment.setUser(user);

        // Then
        assertEquals(user, comment.getUser());
    }

    @Test
    public void whenSetTodo() {
        // Given
        Comment comment = new Comment();
        Todo todo = new Todo();

        // When
        comment.setTodo(todo);

        // Then
        assertEquals(todo, comment.getTodo());
        assertTrue(todo.getComments().contains(comment));
    }

    @Test
    public void whenSetText() {
        // Given
        Comment comment = new Comment();
        comment.setText("Initial Text");

        // When
        comment.setText("Updated Text");

        // Then
        assertEquals("Updated Text", comment.getText());
    }
}

