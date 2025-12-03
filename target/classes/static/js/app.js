class TranscriptExtractor {
    constructor() {
        this.apiBaseUrl = 'http://localhost:8080/api/transcript';
        this.initializeEventListeners();
    }

    initializeEventListeners() {
        const getTranscriptBtn = document.getElementById('getTranscriptBtn');
        const copyTranscriptBtn = document.getElementById('copyTranscriptBtn');
        const downloadTranscriptBtn = document.getElementById('downloadTranscriptBtn');
        const videoUrlInput = document.getElementById('videoUrl');

        getTranscriptBtn.addEventListener('click', () => this.extractTranscript());
        copyTranscriptBtn.addEventListener('click', () => this.copyTranscript());
        downloadTranscriptBtn.addEventListener('click', () => this.downloadTranscript());

        videoUrlInput.addEventListener('keypress', (e) => {
            if (e.key === 'Enter') {
                this.extractTranscript();
            }
        });
    }

    async extractTranscript() {
        const videoUrl = document.getElementById('videoUrl').value.trim();
        const transcriptSection = document.getElementById('transcript-section');
        const loadingSpinner = document.getElementById('loadingSpinner');
        const errorMessage = document.getElementById('errorMessage');
        const transcriptContent = document.getElementById('transcriptContent');
        const videoTitle = document.getElementById('videoTitle');
        const videoId = document.getElementById('videoId');

        // Reset states
        errorMessage.style.display = 'none';
        transcriptSection.style.display = 'none';

        // Validate URL
        if (!this.isValidYouTubeUrl(videoUrl)) {
            this.showError('Please enter a valid YouTube URL');
            return;
        }

        // Show loading
        loadingSpinner.style.display = 'block';

        try {
            const response = await fetch(`${this.apiBaseUrl}/extract`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ url: videoUrl })
            });

            const data = await response.json();

            if (!response.ok) {
                throw new Error(data.error || 'Failed to extract transcript');
            }

            if (data.error) {
                throw new Error(data.error);
            }

            // Display transcript
            transcriptContent.textContent = data.transcript;
            videoTitle.textContent = data.title || 'Untitled Video';
            videoId.textContent = `Video ID: ${data.videoId}`;

            transcriptSection.style.display = 'block';

            // Smooth scroll to transcript
            transcriptSection.scrollIntoView({ behavior: 'smooth' });

        } catch (error) {
            this.showError(error.message);
        } finally {
            loadingSpinner.style.display = 'none';
        }
    }

    isValidYouTubeUrl(url) {
        const youtubeRegex = /^(https?:\/\/)?(www\.)?(youtube\.com|youtu\.?be)\/.+$/;
        return youtubeRegex.test(url);
    }

    showError(message) {
        const errorMessage = document.getElementById('errorMessage');
        errorMessage.textContent = message;
        errorMessage.style.display = 'block';

        // Scroll to error
        errorMessage.scrollIntoView({ behavior: 'smooth' });
    }

    copyTranscript() {
        const transcriptContent = document.getElementById('transcriptContent');
        const text = transcriptContent.textContent;

        if (!text) {
            this.showError('No transcript to copy');
            return;
        }

        navigator.clipboard.writeText(text).then(() => {
            this.showTemporaryMessage('Transcript copied to clipboard!', 'success');
        }).catch(() => {
            this.showError('Failed to copy transcript');
        });
    }

    downloadTranscript() {
        const transcriptContent = document.getElementById('transcriptContent');
        const videoTitle = document.getElementById('videoTitle');
        const text = transcriptContent.textContent;

        if (!text) {
            this.showError('No transcript to download');
            return;
        }

        const title = videoTitle.textContent.replace(/[^a-z0-9]/gi, '_').toLowerCase();
        const blob = new Blob([text], { type: 'text/plain' });
        const url = URL.createObjectURL(blob);
        const a = document.createElement('a');

        a.href = url;
        a.download = `${title}_transcript.txt`;
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
        URL.revokeObjectURL(url);

        this.showTemporaryMessage('Transcript downloaded!', 'success');
    }

    showTemporaryMessage(message, type) {
        const messageEl = document.createElement('div');
        messageEl.textContent = message;
        messageEl.style.cssText = `
            position: fixed;
            top: 20px;
            right: 20px;
            background: ${type === 'success' ? '#4CAF50' : '#f44336'};
            color: white;
            padding: 15px 20px;
            border-radius: 4px;
            z-index: 1000;
            box-shadow: 0 2px 10px rgba(0,0,0,0.2);
        `;

        document.body.appendChild(messageEl);

        setTimeout(() => {
            document.body.removeChild(messageEl);
        }, 3000);
    }
}

// Initialize the application when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    new TranscriptExtractor();
});