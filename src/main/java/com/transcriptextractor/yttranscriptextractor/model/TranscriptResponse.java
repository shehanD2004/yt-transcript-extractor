package com.transcriptextractor.yttranscriptextractor.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TranscriptResponse {
    private String transcript;
    private String error;
    private String videoId;
    private String title;

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

    // Getters and Setters
    public String getTranscript() { return transcript; }
    public void setTranscript(String transcript) { this.transcript = transcript; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    public String getVideoId() { return videoId; }
    public void setVideoId(String videoId) { this.videoId = videoId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
}