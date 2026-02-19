package com.spring.demo.core.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.spring.demo.core.converter.UserConverter;
import com.spring.demo.core.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@Testable
@SpringJUnitConfig
@Import(UserService.class)
class UserServiceTest {

    @MockitoBean
    UserRepository userRepository;

    @MockitoBean
    UserConverter userConverter;

    @Autowired
    UserService userService;

    @Test
    void shouldCreateUserService() {
        assertNotNull(userService);
        assertNotNull(userService.entityClass());
        assertNotNull(userService.modelClass());

        assertNotNull(userService.repository());
        assertNotNull(userService.converter());
    }
}
