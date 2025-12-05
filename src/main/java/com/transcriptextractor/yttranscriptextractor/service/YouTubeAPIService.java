package com.transcriptextractor.yttranscriptextractor.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.HashMap;

@Service
public class YouTubeAPIService {

    private final RestTemplate restTemplate;

    @Value("${youtube.api.key}")
    private String apiKey;

    @Value("${youtube.api.base-url}")
    private String baseUrl;

    public YouTubeAPIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Get video details including title, description, etc.
     */
    public Map<String, Object> getVideoDetails(String videoId) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/videos")
                .queryParam("part", "snippet,contentDetails")
                .queryParam("id", videoId)
                .queryParam("key", apiKey)
                .toUriString();

        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch video details: " + e.getMessage(), e);
        }
    }

    /**
     * Get available captions for a video
     * Note: This only lists captions, doesn't download them
     */
    public Map<String, Object> getVideoCaptions(String videoId) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/captions")
                .queryParam("part", "snippet")
                .queryParam("videoId", videoId)
                .queryParam("key", apiKey)
                .toUriString();

        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch captions: " + e.getMessage(), e);
        }
    }

    /**
     * Search for videos (useful for bulk processing)
     */
    public Map<String, Object> searchVideos(String query, int maxResults) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/search")
                .queryParam("part", "snippet")
                .queryParam("q", query)
                .queryParam("maxResults", maxResults)
                .queryParam("type", "video")
                .queryParam("key", apiKey)
                .toUriString();

        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Failed to search videos: " + e.getMessage(), e);
        }
    }
}
