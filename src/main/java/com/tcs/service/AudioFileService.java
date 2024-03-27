package com.tcs.service;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.domain.AudioFile;
import com.tcs.repo.AudioFileRepository;
@Service
public class AudioFileService {
    @Autowired
    private AudioFileRepository audioFileRepository;

    public byte[] saveAudioFile(String name, byte[] data) {
        AudioFile audioFile = new AudioFile();
        audioFile.setName(name);
        audioFile.setData(data);
        AudioFile savedAudioFile = audioFileRepository.save(audioFile);
        return savedAudioFile.getData();
    }

    public byte[] getAudioFileDataById(Long id) throws IOException {
        AudioFile audioFile = audioFileRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Audio file not found with id: " + id));

        byte[] audioData = audioFile.getData();
        if (audioData != null) {
            return audioData;
        } else {
            throw new IllegalArgumentException("Audio file data is null");
        }
    }
}



