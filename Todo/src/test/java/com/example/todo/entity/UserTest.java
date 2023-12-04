package com.example.todo.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    @Test
    @DisplayName("아이디 비밀번호 올바른지 확인")
    public void whenUserCreatedThenFieldsAreSetCorrectly() {
        // given
        String username = "testuser";
        String password = "password123";

        // when
        User user = new User(username, password);

        // then
        assertNotNull(user); // 유저 객체가 null이 아닌지 확인
        assertEquals(username, user.getUsername()); // 유저네임이 올바르게 설정되었는지 확인
        assertEquals(password, user.getPassword()); // 패스워드가 올바르게 설정되었는지 확인
    }
}
