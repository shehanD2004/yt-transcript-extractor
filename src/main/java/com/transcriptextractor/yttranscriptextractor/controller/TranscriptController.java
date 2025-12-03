package com.transcriptextractor.yttranscriptextractor.controller;

import com.transcriptextractor.yttranscriptextractor.model.TranscriptResponse;
import com.transcriptextractor.yttranscriptextractor.service.YoutubeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transcript")
@CrossOrigin(origins = "*") // For frontend development
public class TranscriptController {

    private final YoutubeService youtubeService;

    public TranscriptController(YoutubeService youtubeService) {
        this.youtubeService = youtubeService;
    }

    @PostMapping("/extract")
    public TranscriptResponse extractTranscript(@RequestBody TranscriptRequest request) {
        if (request.getUrl() == null || request.getUrl().trim().isEmpty()) {
            return new TranscriptResponse("URL is required");
        }
        return youtubeService.extractTranscript(request.getUrl());
    }

    public static class TranscriptRequest {
        private String url;

        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
    }
}