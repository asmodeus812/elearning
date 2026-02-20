package com.spring.demo.core.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.spring.demo.core.model.VideoModel;
import com.spring.demo.core.service.AuthorityService;
import com.spring.demo.core.service.PrincipalService;
import com.spring.demo.core.service.RoleService;
import com.spring.demo.core.service.UserService;
import com.spring.demo.core.service.VideoService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@Testable
@WithMockUser(value = "test-user", username = "admin", authorities = {"video:list"})
@WebMvcTest(controllers = RestfulController.class)
class RestfulControllerTest {

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
        Page<VideoModel> emptyPage = new PageImpl<>(List.of(), PageRequest.of(0, 20), 0);
        when(videoService.findAll(any(Pageable.class))).thenReturn(emptyPage);
    }

    @Test
    void shouldReturnPagedVideos() throws Exception {
        mvc.perform(get("/api/videos").param("page", "0").param("size", "20").accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.content", hasSize(0)))
                        .andExpect(jsonPath("$.totalElements").value(0));

        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);
        verify(videoService).findAll(captor.capture());

        Pageable pageable = captor.getValue();
        assertThat(pageable.getPageNumber()).isZero();
        assertThat(pageable.getPageSize()).isEqualTo(20);
    }
}
