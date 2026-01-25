package com.spring.demo.core.web;

import com.spring.demo.core.model.SearchCriteria;
import com.spring.demo.core.model.VideoModel;
import com.spring.demo.core.service.VideoService;
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

    private final VideoService videosService;

    public RestfulController(VideoService videosService) {
        this.videosService = videosService;
    }

    @GetMapping("/")
    public ResponseEntity<Object> getVidoes() {
        return ResponseEntity.of(Optional.of(videosService.getVideos()));
    }

    @GetMapping("/find/{name}")
    public ResponseEntity<Object> getVidoes(@PathVariable String name) {
        return ResponseEntity.of(Optional.of(videosService.getVideos(SearchCriteria.of(VideoModel.VideoCriteria.NAME, name))));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteVideo(@PathVariable Long id) {
        return ResponseEntity.of(Optional.of(videosService.deleteVideo(id)));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateVideo(@PathVariable Long id, @RequestBody VideoModel video) {
        return ResponseEntity.of(Optional.ofNullable(videosService.updateVideo(new VideoModel(id, video.name(), ""))));
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createVideo(@RequestBody VideoModel video) {
        return ResponseEntity.of(Optional.ofNullable(videosService.addVideo(video.name())));
    }
}
