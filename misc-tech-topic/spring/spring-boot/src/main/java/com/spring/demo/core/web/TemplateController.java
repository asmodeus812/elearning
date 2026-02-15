package com.spring.demo.core.web;

import com.spring.demo.core.model.SearchCriteria;
import com.spring.demo.core.model.UserModel;
import com.spring.demo.core.model.VideoModel;
import com.spring.demo.core.service.AuthorityService;
import com.spring.demo.core.service.PrincipalService;
import com.spring.demo.core.service.RoleService;
import com.spring.demo.core.service.UserService;
import com.spring.demo.core.service.VideoService;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/ui")
public class TemplateController {

    private static final String TEMPLATE_USER_BASE = "user";
    private static final String TEMPLATE_USERS_BASE = "users";

    private static final String TEMPLATE_VIDEO_BASE = "video";
    private static final String TEMPLATE_VIDEOS_BASE = "videos";

    private static final String REDIRECT_TEMPLATE_INDEX = "redirect:/ui/";

    private final VideoService videoService;
    private final RoleService roleService;
    private final UserService userService;
    private final PrincipalService principalService;
    private final AuthorityService authorityService;

    public TemplateController(VideoService videoService, RoleService roleService, UserService userService,
                    PrincipalService principalService, AuthorityService authorityService) {
        this.videoService = videoService;
        this.roleService = roleService;
        this.userService = userService;
        this.principalService = principalService;
        this.authorityService = authorityService;
    }

    @GetMapping(path = {"/"})
    public String index() {
        return "index";
    }

    @GetMapping(path = {"/videos"})
    public String getVideos(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size, Model model) {
        final int pageNumber = Math.max(page, 1);
        final int pageSize = Math.min(Math.max(size, 1), 100);
        final Sort sortCriteria = Sort.by(Sort.Direction.ASC, "id");

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sortCriteria);
        Page<VideoModel> videosPage = videoService.getAll(pageable);

        model.addAttribute("videos", videosPage.getContent());
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", videosPage.getTotalPages());
        model.addAttribute("totalElements", videosPage.getTotalElements());
        model.addAttribute("hasPrev", videosPage.hasPrevious());
        model.addAttribute("hasNext", videosPage.hasNext());
        model.addAttribute("prevPage", Math.max(pageNumber - 1, 1));
        model.addAttribute("nextPage", Math.min(pageNumber + 1, videosPage.getTotalPages()));
        model.addAttribute("pages",
                        IntStream.rangeClosed(1, Math.max(videosPage.getTotalPages(), 1))
                                        .mapToObj(i -> new PageLink(i, i == pageNumber))
                                        .toList());

        return TEMPLATE_VIDEOS_BASE;
    }

    @GetMapping(path = "/find-videos", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String findVideos(Model model, @RequestParam Map<String, String> criteria) {
        model.addAttribute("videos", videoService.search(SearchCriteria.of(criteria), VideoModel.VideoCriteria.values()));
        return TEMPLATE_VIDEOS_BASE;
    }

    @PostMapping(path = "/new-video", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String addVideo(@ModelAttribute VideoModel video) {
        videoService.create(video);
        return REDIRECT_TEMPLATE_INDEX + TEMPLATE_VIDEOS_BASE;
    }

    @PostMapping(path = "/update-video", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String updateVideo(@ModelAttribute VideoModel video) {
        videoService.update(video.id(), video);
        return REDIRECT_TEMPLATE_INDEX + TEMPLATE_VIDEOS_BASE;
    }

    @PostMapping(path = "/delete-video/{id}", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String deleteVideo(@PathVariable("id") Long target) {
        videoService.delete(target);
        return REDIRECT_TEMPLATE_INDEX + TEMPLATE_VIDEOS_BASE;
    }

    @GetMapping(path = "/users")
    @PreAuthorize("hasAuthority('user:manage') or hasAuthority('user:list')")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getAll());
        return TEMPLATE_USERS_BASE;
    }

    @GetMapping(path = {"/user", "/user/{id}"})
    @PreAuthorize("#target == null or hasAuthority('user:manage') or hasAuthority('user:list')")
    public String getUser(@PathVariable(name = "id", required = false) Long target, Model model) {
        if (!Objects.isNull(target)) {
            model.addAttribute("user", userService.get(target));
        } else {
            UserDetails details = principalService.getPrincipal().orElseThrow();
            model.addAttribute("user", userService.findByUsername(details.getUsername()).orElseThrow());
        }
        return TEMPLATE_USER_BASE;
    }

    @PreAuthorize("#user.username == authentication.name or hasAuthority('user:manage')")
    @PostMapping(path = "/update-user", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String editUser(@ModelAttribute UserModel user) {
        userService.update(user.id(), user);
        return REDIRECT_TEMPLATE_INDEX + TEMPLATE_USERS_BASE;
    }

    @PreAuthorize("hasAuthority('user:manage')")
    @PostMapping(path = "/delete-user/{id}", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String deleteUser(@PathVariable("id") Long target) {
        userService.delete(target);
        return REDIRECT_TEMPLATE_INDEX + TEMPLATE_USERS_BASE;
    }

    record PageLink(int number, boolean current) {
    }
}
