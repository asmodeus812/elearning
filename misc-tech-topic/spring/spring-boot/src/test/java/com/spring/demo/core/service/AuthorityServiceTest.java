package com.spring.demo.core.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.spring.demo.core.converter.AuthorityConverter;
import com.spring.demo.core.repository.AuthorityRepository;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@Testable
@SpringJUnitConfig
@Import(AuthorityService.class)
class AuthorityServiceTest {

    @MockitoBean
    AuthorityRepository authorityRepository;

    @MockitoBean
    AuthorityConverter authorityConverter;

    @Autowired
    AuthorityService authorityService;

    @Test
    void shouldCreateAuthorityService() {
        assertNotNull(authorityService);
        assertNotNull(authorityService.entityClass());
        assertNotNull(authorityService.modelClass());

        assertNotNull(authorityService.repository());
        assertNotNull(authorityService.converter());
    }
}
