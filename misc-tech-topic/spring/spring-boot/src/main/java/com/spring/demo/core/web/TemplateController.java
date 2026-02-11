package com.spring.demo.core.web;

import com.spring.demo.core.model.SearchCriteria;
import com.spring.demo.core.model.VideoModel;
import com.spring.demo.core.service.VideoService;
import java.util.Map;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
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

    private static final String TEMPLATE_INDEX_BASE = "index";
    private static final String REDIRECT_TEMPLATE_INDEX = "redirect:/";

    private final VideoService videoService;

    public TemplateController(VideoService videosService) {
        this.videoService = videosService;
    }

    @GetMapping(path = {"/", "/list"})
    public String list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size, Model model) {
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

        return TEMPLATE_INDEX_BASE;
    }


    @GetMapping(path = "/find", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String find(Model model, @RequestParam Map<String, String> criteria) {
        model.addAttribute("videos", videoService.search(SearchCriteria.of(criteria), VideoModel.VideoCriteria.values()));
        return TEMPLATE_INDEX_BASE;
    }

    @PostMapping(path = "/new-video", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String add(@ModelAttribute VideoModel video) {
        videoService.create(video);
        return REDIRECT_TEMPLATE_INDEX;
    }

    @PostMapping(path = "/update-video", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String update(@ModelAttribute VideoModel video) {
        videoService.update(video.id(), video);
        return REDIRECT_TEMPLATE_INDEX;
    }

    @PostMapping("/delete-video/{id}")
    public String delete(@PathVariable("id") Long target) {
        videoService.delete(target);
        return REDIRECT_TEMPLATE_INDEX;
    }

    record PageLink(int number, boolean current) {
    }
}
