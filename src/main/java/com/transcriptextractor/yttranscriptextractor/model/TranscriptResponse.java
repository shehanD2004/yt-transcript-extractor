package com.transcriptextractor.yttranscriptextractor.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TranscriptResponse {
    private String transcript;
    private String error;
    private String videoId;
    private String title;
    private String channelTitle;
    private String description;
    private String thumbnailUrl;
    private String duration;
    private String publishedAt;
    private List<Caption> captions;
    private Map<String, Object> rawData;
    private String warning;

    // New inner class for structured captions
    public static class Caption {
        private String text;
        private String start;
        private String duration;

        public Caption() {}

        public Caption(String text, String start, String duration) {
            this.text = text;
            this.start = start;
            this.duration = duration;
        }

        // Getters and setters
        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
        public String getStart() { return start; }
        public void setStart(String start) { this.start = start; }
        public String getDuration() { return duration; }
        public void setDuration(String duration) { this.duration = duration; }
    }

    // Constructors
    public TranscriptResponse() {}

    public TranscriptResponse(String transcript, String videoId, String title) {
        this.transcript = transcript;
        this.videoId = videoId;
        this.title = title;
    }

    public TranscriptResponse(String error) {
        this.error = error;
    }

    // Getters and setters for all fields
    public String getTranscript() { return transcript; }
    public void setTranscript(String transcript) { this.transcript = transcript; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    public String getVideoId() { return videoId; }
    public void setVideoId(String videoId) { this.videoId = videoId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getChannelTitle() { return channelTitle; }
    public void setChannelTitle(String channelTitle) { this.channelTitle = channelTitle; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getThumbnailUrl() { return thumbnailUrl; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public String getPublishedAt() { return publishedAt; }
    public void setPublishedAt(String publishedAt) { this.publishedAt = publishedAt; }

    public List<Caption> getCaptions() { return captions; }
    public void setCaptions(List<Caption> captions) { this.captions = captions; }

    public Map<String, Object> getRawData() { return rawData; }
    public void setRawData(Map<String, Object> rawData) { this.rawData = rawData; }

    public String getWarning() { return warning; }
    public void setWarning(String warning) { this.warning = warning; }
}