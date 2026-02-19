package com.spring.demo.core.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.spring.demo.core.converter.RoleConverter;
import com.spring.demo.core.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@Testable
@SpringJUnitConfig
@Import(RoleService.class)
class RoleServiceTest {

    @MockitoBean
    RoleRepository roleRepository;

    @MockitoBean
    RoleConverter roleConverter;

    @Autowired
    RoleService roleService;

    @Test
    void shouldCreateRoleService() {
        assertNotNull(roleService);
        assertNotNull(roleService.entityClass());
        assertNotNull(roleService.modelClass());

        assertNotNull(roleService.repository());
        assertNotNull(roleService.converter());
    }
}
