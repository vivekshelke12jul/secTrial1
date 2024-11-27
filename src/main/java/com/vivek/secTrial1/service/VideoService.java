package com.vivek.secTrial1.service;

import com.vivek.secTrial1.controllers.exchanges.VideoDto;
import com.vivek.secTrial1.model.Video;
import com.vivek.secTrial1.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }

    public Video getVideoById(Long id) {
        return videoRepository.findById(id).get();
    }

    public Video createVideo(VideoDto videoDto){
        Video video = new Video(videoDto.getTitle(), videoDto.getContent());
        return videoRepository.save(video);
    }


    public Video updateVideo(VideoDto videoDto, Long id) {
        Video video = videoRepository.findById(id).get();
        video.setTitle(videoDto.getTitle());
        video.setContent(videoDto.getContent());
        return videoRepository.save(video);
    }

    public void deleteVideo(Long id) {
        videoRepository.deleteById(id);
    }
}
