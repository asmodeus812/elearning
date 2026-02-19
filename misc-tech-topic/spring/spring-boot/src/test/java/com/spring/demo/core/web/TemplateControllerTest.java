package com.spring.demo.core.web;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.spring.demo.core.config.model.MutableUserDetails;
import com.spring.demo.core.model.RoleModel;
import com.spring.demo.core.model.UserModel;
import com.spring.demo.core.service.AuthorityService;
import com.spring.demo.core.service.PrincipalService;
import com.spring.demo.core.service.RoleService;
import com.spring.demo.core.service.UserService;
import com.spring.demo.core.service.VideoService;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@Testable
@WithMockUser(value = "test-user", username = "admin", roles = {"user:list", "user:manage"})
@WebMvcTest(controllers = TemplateController.class)
class TemplateControllerTest {

    @MockitoBean
    UserService userService;

    @MockitoBean
    VideoService videoService;

    @MockitoBean
    RoleService roleService;

    @MockitoBean
    PrincipalService principalService;

    @MockitoBean
    AuthorityService authorityService;

    @Autowired
    MockMvc mvc;

    @BeforeEach
    void prepareMockData() {
        when(userService.findByUsername("admin"))
                        .thenReturn(Optional.of(new UserModel(1L, "admin", "admin", new RoleModel(1L, "ADMIN", Collections.emptySet()))));
        when(userService.get(1L)).thenReturn(new UserModel(1L, "admin", "admin", new RoleModel(1L, "ADMIN", Collections.emptySet())));
        when(principalService.getPrincipal()).thenReturn(Optional.of(new MutableUserDetails("admin", "admin", Collections.emptySet())));
    }

    @Test
    void shouldReturnPrincipalUser() throws Exception {
        mvc.perform(get("/ui/user").accept(MediaType.TEXT_HTML_VALUE))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML_VALUE));

        verify(userService, times(0)).get(anyLong());
        verify(principalService, times(1)).getPrincipal();
        verify(userService, times(1)).findByUsername("admin");
    }

    @Test
    void shouldReturnTargetUser() throws Exception {
        mvc.perform(get("/ui/user/1").accept(MediaType.TEXT_HTML_VALUE))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML_VALUE));

        verify(principalService, times(0)).getPrincipal();
        verify(userService, times(0)).findByUsername(anyString());
        verify(userService, times(1)).get(1L);
    }
}
