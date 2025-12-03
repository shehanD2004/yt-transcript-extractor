package com.transcriptextractor.yttranscriptextractor.service;

import com.transcriptextractor.yttranscriptextractor.model.TranscriptResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class YoutubeService {

    private static final String YOUTUBE_TRANSCRIPT_URL = "https://www.youtube.com/watch?v=";
    private static final Pattern YOUTUBE_ID_PATTERN =
            Pattern.compile("^.*(?:youtu.be\\/|v\\/|e\\/|u\\/\\w+\\/|embed\\/|v=)([^#\\&\\?]*).*");

    public TranscriptResponse extractTranscript(String videoUrl) {
        try {
            String videoId = extractVideoId(videoUrl);
            if (videoId == null) {
                return new TranscriptResponse("Invalid YouTube URL");
            }

            Document doc = Jsoup.connect(YOUTUBE_TRANSCRIPT_URL + videoId)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .timeout(10000)
                    .get();

            String transcript = extractTranscriptFromHtml(doc);
            if (transcript == null || transcript.isEmpty()) {
                return new TranscriptResponse("No transcript available for this video");
            }

            String title = extractVideoTitle(doc);

            return new TranscriptResponse(transcript, videoId, title);

        } catch (IOException e) {
            return new TranscriptResponse("Error fetching video: " + e.getMessage());
        } catch (Exception e) {
            return new TranscriptResponse("Unexpected error: " + e.getMessage());
        }
    }

    private String extractVideoId(String url) {
        Matcher matcher = YOUTUBE_ID_PATTERN.matcher(url);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return null;
    }

    private String extractTranscriptFromHtml(Document doc) {
        // This is a simplified approach - you might need to adjust based on YouTube's current HTML structure
        Element script = doc.select("script:containsData(ytInitialData)").first();
        if (script != null) {
            String scriptData = script.data();
            // You'll need to implement proper JSON parsing to extract transcripts
            // This is a placeholder - real implementation requires handling YouTube's complex data structure
            return extractTranscriptFromJson(scriptData);
        }
        return null;
    }

    private String extractTranscriptFromJson(String jsonData) {
        // TODO: Implement proper JSON parsing for YouTube's data structure
        // This is a complex task that requires handling YouTube's specific data format
        // You might want to use a dedicated YouTube API library or more sophisticated parsing
        return "Transcript extraction logic to be implemented based on current YouTube structure";
    }

    private String extractVideoTitle(Document doc) {
        Element titleElement = doc.selectFirst("meta[name=title]");
        return titleElement != null ? titleElement.attr("content") : "Unknown Title";
    }
}