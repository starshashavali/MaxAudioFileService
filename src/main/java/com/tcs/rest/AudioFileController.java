package com.tcs.rest;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tcs.service.AudioFileService;

@RestController
@RequestMapping("/api/audio")
public class AudioFileController {

	@Autowired
	private AudioFileService audioFileService;

	@PostMapping("/upload")
	public ResponseEntity<String> captureAndStoreAudio(@RequestParam("name") String name,
			@RequestParam("file") MultipartFile file) {
		try {
			// Process audio data if necessary
			byte[] audioData = file.getBytes();
			byte[] processedData = processAudioData(audioData);

			// Store audio data
			audioFileService.saveAudioFile(name, processedData);

			return ResponseEntity.ok().body("Audio data captured and stored successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to capture and store audio data");
		}
	}

	private byte[] processAudioData(byte[] audioData) {
		// Perform any necessary processing here (if needed)
		return audioData;

	}

	@GetMapping("/{id}")
	public ResponseEntity<?> downloadAudioFile(@PathVariable Long id) {
		try {
			byte[] audioData = audioFileService.getAudioFileDataById(id);

			// Create ByteArrayResource from the audio data
			ByteArrayResource resource = new ByteArrayResource(audioData);

			// Build HttpHeaders with appropriate content type and content disposition
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("audio/mpeg")); // Set appropriate content type for MP3
			headers.setContentDispositionFormData("filename", "audio.mp3"); // Set filename for download

			// Return ResponseEntity with ByteArrayResource and HttpHeaders
			return ResponseEntity.ok().headers(headers).contentLength(audioData.length).body(resource);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
}