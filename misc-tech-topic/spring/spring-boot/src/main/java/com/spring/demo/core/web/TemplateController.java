package com.spring.demo.core.web;

import com.spring.demo.core.model.VideoModel;
import com.spring.demo.core.service.VideoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TemplateController {

    private static final String TEMPLATE_INDEX_BASE = "index";
    private static final String REDIRECT_TEMPLATE_INDEX = "redirect:/";

    private final VideoService videoService;

    public TemplateController(VideoService videosService) {
        this.videoService = videosService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("videos", videoService.getVideos());
        return TEMPLATE_INDEX_BASE;
    }

    @GetMapping("/find")
    public String find(Model model, @RequestParam("name") String findByName) {
        model.addAttribute("videos", videoService.getVideos());
        return TEMPLATE_INDEX_BASE;
    }

    @PostMapping("/new-video")
    public String add(@RequestParam("name") String newVideoName) {
        videoService.addVideo(newVideoName);
        return REDIRECT_TEMPLATE_INDEX;
    }

    @PostMapping("/update-video/{id}")
    public String update(@PathVariable("id") Long target, @RequestParam("name") String newVideoName) {
        videoService.updateVideo(new VideoModel(target, newVideoName, ""));
        return REDIRECT_TEMPLATE_INDEX;
    }

    @PostMapping("/delete-video/{id}")
    public String delete(@PathVariable("id") Long target) {
        videoService.deleteVideo(target);
        return REDIRECT_TEMPLATE_INDEX;
    }
}
