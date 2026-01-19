package com.spring.demo.core.service;

import com.spring.demo.core.model.Video;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class VideosService {

    private static int VIDEO_SEQUENTIAL_INDEX = 1;

    private List<Video> videos = new ArrayList<>();

    public VideosService() {
        videos.add(new Video(VIDEO_SEQUENTIAL_INDEX++, "Youtube"));
        videos.add(new Video(VIDEO_SEQUENTIAL_INDEX++, "Vimeo"));
        videos.add(new Video(VIDEO_SEQUENTIAL_INDEX++, "Vbox"));
        videos.add(new Video(VIDEO_SEQUENTIAL_INDEX++, "Onion"));
    }

    public List<Video> getVideos() {
        return videos.stream().sorted(Comparator.comparing(Video::getId)).toList();
    }

    public Optional<Video> getVideo(Integer target) {
        return videos.stream().filter(vid -> vid.getId().equals(target)).findFirst();
    }

    public Optional<Video> updateVideo(Integer target, String newName) {
        Optional<Video> video = getVideo(target);
        video.ifPresent(vid -> vid.setName(newName));
        return video;
    }

    public boolean deleteVideos(String name) {
        int oldSize = videos.size();
        videos = videos.stream().filter(vid -> vid.getName().equalsIgnoreCase(name)).toList();
        int currentSize = videos.size();
        return oldSize > currentSize;
    }

    public boolean deleteVideo(Integer target) {
        Iterator<Video> iterator = videos.iterator();
        while (iterator.hasNext()) {
            Video video = iterator.next();
            if (video.getId().equals(target)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public Video addVideo(String name) {
        var video = new Video(VIDEO_SEQUENTIAL_INDEX++, name);
        videos.add(video);
        return video;
    }
}
