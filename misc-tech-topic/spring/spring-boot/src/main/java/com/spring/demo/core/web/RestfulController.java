package com.spring.demo.core.web;

import com.spring.demo.core.model.SearchCriteria;
import com.spring.demo.core.model.VideoModel;
import com.spring.demo.core.service.VideoService;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RestfulController {

    private final VideoService videosService;

    public RestfulController(VideoService videosService) {
        this.videosService = videosService;
    }

    @GetMapping("/list")
    public ResponseEntity<Object> getVidoes(Pageable pageable) {
        return ResponseEntity.of(Optional.of(videosService.getVideos(pageable)));
    }

    @PostMapping("/find")
    public ResponseEntity<Object> getVidoes(@RequestBody Map<String, Object> criteria) {
        return ResponseEntity.of(Optional.of(videosService.getVideos(SearchCriteria.of(criteria))));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteVideo(@PathVariable Long id) {
        return ResponseEntity.of(Optional.of(videosService.deleteVideo(id)));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateVideo(@PathVariable Long id, @RequestBody VideoModel video) {
        return ResponseEntity.of(Optional.ofNullable(videosService.updateVideo(video)));
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createVideo(@RequestBody VideoModel video) {
        return ResponseEntity.of(Optional.ofNullable(videosService.addVideo(video)));
    }
}
