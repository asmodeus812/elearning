package com.spring.demo.core.web;

import com.spring.demo.core.service.VideosService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TemplateController {

    private final VideosService videosService;

    public TemplateController(VideosService videosService) {
        this.videosService = videosService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("videos", videosService.getVideos());
        return "index";
    }

    @PostMapping("/new-video")
    public String add(@RequestParam("name") String newVideoName) {
        videosService.addVideo(newVideoName);
        return "redirect:/";
    }

    @PostMapping("/update-video/{id}")
    public String update(@PathVariable("id") Integer target, @RequestParam("name") String newVideoName) {
        videosService.updateVideo(target, newVideoName);
        return "redirect:/";
    }

    @PostMapping("/delete-video/{id}")
    public String delete(@PathVariable("id") Integer target) {
        videosService.deleteVideo(target);
        return "redirect:/";
    }
}
