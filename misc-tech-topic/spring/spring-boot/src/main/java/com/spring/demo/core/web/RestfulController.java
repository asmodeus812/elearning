package com.spring.demo.core.web;

import com.spring.demo.core.model.Video;
import com.spring.demo.core.service.VideosService;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
public class RestfulController {

    private final VideosService videosService;

    public RestfulController(VideosService videosService) {
        this.videosService = videosService;
    }

    @GetMapping("/")
    public ResponseEntity<Object> getVidoes() {
        return ResponseEntity.of(Optional.of(videosService.getVideos()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteVideo(@PathVariable Integer id) {
        return ResponseEntity.of(Optional.of(videosService.deleteVideo(id)));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateVideo(@PathVariable Integer id, @RequestBody Video video) {
        return ResponseEntity.of(Optional.ofNullable(videosService.updateVideo(id, video.getName())));
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createVideo(@RequestBody Video video) {
        return ResponseEntity.of(Optional.ofNullable(videosService.addVideo(video.getName())));
    }
}
