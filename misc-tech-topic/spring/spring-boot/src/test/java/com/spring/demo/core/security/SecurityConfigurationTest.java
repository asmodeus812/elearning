package com.spring.demo.core.security;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.spring.demo.core.config.SecurityConfiguration;
import com.spring.demo.core.config.model.MutableUserDetails;
import com.spring.demo.core.model.RoleModel;
import com.spring.demo.core.model.UserModel;
import com.spring.demo.core.service.AuthorityService;
import com.spring.demo.core.service.PrincipalService;
import com.spring.demo.core.service.RoleService;
import com.spring.demo.core.service.UserService;
import com.spring.demo.core.service.VideoService;
import com.spring.demo.core.web.TemplateController;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@Testable
@Import(SecurityConfiguration.class)
@WebMvcTest(controllers = TemplateController.class)
class SecurityConfigurationTest {

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
        when(userService.update(anyLong(), any())).thenReturn(
                        new UserModel(1L, "updated-user", "updated-password", new RoleModel(1L, "updated-role", Collections.emptySet())));
        when(principalService.getPrincipal()).thenReturn(Optional.of(new MutableUserDetails("admin", "admin", Collections.emptySet())));
    }

    @Test
    void shouldReportUnauthorizedForUserAccess() throws Exception {
        mvc.perform(get("/ui/user/1")).andExpect(status().is3xxRedirection()).andExpect(redirectedUrlPattern("**/ui/login"));
        verify(userService, times(0)).get(1L);
    }

    @Test
    @WithMockUser(value = "test-user", username = "regular-user", authorities = {"video:list"})
    void shouldReportForbiddenForUserAccess() throws Exception {
        mvc.perform(get("/ui/user/1")).andExpect(status().isForbidden());
        verify(userService, times(0)).get(1L);
    }

    @Test
    @WithMockUser(value = "test-user", username = "regular-user", authorities = {"user:manage"})
    void shouldReportAllowForUserAccess() throws Exception {
        mvc.perform(get("/ui/user/1")).andExpect(status().isOk());
        verify(userService, times(1)).get(1L);
    }

    @Test
    @WithMockUser(value = "test-user", username = "advanced-user", authorities = {"user:manage"})
    void shouldReportAllowForUserUpdate() throws Exception {
        UserModel updatedUser = new UserModel(1L, "regular-user", "new-password", null);

        mvc.perform(post("/ui/update-user").with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("id", "1")
                        .param("username", "regular-user")
                        .param("password", "new-password")).andExpect(status().is3xxRedirection());
        verify(userService, times(1)).update(1L, updatedUser);
    }

    @Test
    @WithMockUser(value = "test-user", username = "regular-user", authorities = {})
    void shouldReportForbiddenForUserUpdate() throws Exception {
        mvc.perform(post("/ui/update-user").with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("id", "1")
                        .param("username", "another-user")
                        .param("password", "new-password")).andExpect(status().isForbidden());
        verify(userService, times(0)).update(anyLong(), any());
    }

    @Test
    @WithMockUser(value = "test-user", username = "myself-user", authorities = {})
    void shouldReportAllowMyselfForUserUpdate() throws Exception {
        UserModel updatedUser = new UserModel(1L, "myself-user", "new-password", null);

        mvc.perform(post("/ui/update-user").with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("id", "1")
                        .param("username", "myself-user")
                        .param("password", "new-password")).andExpect(status().is3xxRedirection());
        verify(userService, times(1)).update(1L, updatedUser);
    }
}
