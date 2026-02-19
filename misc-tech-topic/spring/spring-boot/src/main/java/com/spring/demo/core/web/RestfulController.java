package com.spring.demo.core.web;

import com.spring.demo.core.model.SearchCriteria;
import com.spring.demo.core.model.UserModel;
import com.spring.demo.core.model.VideoModel;
import com.spring.demo.core.service.AuthorityService;
import com.spring.demo.core.service.PrincipalService;
import com.spring.demo.core.service.RoleService;
import com.spring.demo.core.service.UserService;
import com.spring.demo.core.service.VideoService;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestfulController {

    private final VideoService videoService;
    private final RoleService roleService;
    private final UserService userService;
    private final PrincipalService principalService;
    private final AuthorityService authorityService;

    public RestfulController(VideoService videoService, RoleService roleService, UserService userService, PrincipalService principalService,
                    AuthorityService authorityService) {
        this.videoService = videoService;
        this.roleService = roleService;
        this.userService = userService;
        this.principalService = principalService;
        this.authorityService = authorityService;
    }

    @GetMapping(path = "/videos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<VideoModel>> getVideos(@RequestParam(defaultValue = "1") int page,
                    @RequestParam(defaultValue = "10") int size) {
        final int pageNumber = Math.max(page, 1);
        final int pageSize = Math.min(Math.max(size, 1), 100);
        final Sort sortCriteria = Sort.by(Sort.Direction.ASC, "id");

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sortCriteria);
        Page<VideoModel> videosPage = videoService.findAll(pageable);

        return ResponseEntity.ok(videosPage);
    }

    @GetMapping(path = "/find-videos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<VideoModel>> findVideos(@RequestParam Map<String, String> criteria) {
        return ResponseEntity.ok(videoService.search(SearchCriteria.of(criteria), VideoModel.VideoCriteria.values()));
    }

    @PostMapping(path = "/new-video", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VideoModel> addVideo(@RequestBody VideoModel video) {
        return ResponseEntity.ok(videoService.create(video));
    }

    @PostMapping(path = "/update-video", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VideoModel> updateVideo(@RequestBody VideoModel video) {
        return ResponseEntity.ok(videoService.update(video.id(), video));
    }

    @PostMapping(path = "/delete-video/{id}")
    public ResponseEntity<Object> deleteVideo(@PathVariable("id") Long target) {
        return ResponseEntity.ok(videoService.delete(target));
    }

    @GetMapping(path = "/users")
    @PreAuthorize("hasAuthority('user:manage') or hasAuthority('user:list')")
    public ResponseEntity<List<UserModel>> getUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping(path = {"/user", "/user/{id}"})
    @PreAuthorize("#target == null or hasAuthority('user:manage') or hasAuthority('user:list')")
    public ResponseEntity<UserModel> getUser(@PathVariable(name = "id", required = false) Long target) {
        if (!Objects.isNull(target)) {
            return ResponseEntity.ok(userService.get(target));
        } else {
            UserDetails details = principalService.getPrincipal().orElseThrow();
            return ResponseEntity.ok(userService.findByUsername(details.getUsername()).orElseThrow());
        }
    }

    @PreAuthorize("#user.username == authentication.name or hasAuthority('user:manage')")
    @PostMapping(path = "/update-user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> editUser(@RequestBody UserModel user) {
        return ResponseEntity.ok(userService.update(user.id(), user));
    }

    @PreAuthorize("hasAuthority('user:manage')")
    @PostMapping(path = "/delete-user/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") Long target) {
        return ResponseEntity.ok(userService.delete(target));
    }
}
