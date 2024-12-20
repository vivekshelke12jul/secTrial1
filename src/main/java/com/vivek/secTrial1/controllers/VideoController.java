package com.vivek.secTrial1.controllers;

import com.vivek.secTrial1.controllers.exchanges.VideoDto;
import com.vivek.secTrial1.model.Video;
import com.vivek.secTrial1.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    VideoService videoService;


    @GetMapping
    public List<Video> getAllVideos(){
        return videoService.getAllVideos();
    }

    @GetMapping("/{id}")
    public Video getVideoById(@PathVariable Long id){
        return videoService.getVideoById(id);
    }

    @PostMapping
    public Video createVideo(@RequestBody VideoDto videoDto){
        return videoService.createVideo(videoDto);
    }

    @PutMapping("/{id}")
    public Video updateVideo(@RequestBody VideoDto videoDto, @PathVariable Long id){
        return videoService.updateVideo(videoDto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteVideo(@PathVariable Long id){
        videoService.deleteVideo(id);
    }
}
