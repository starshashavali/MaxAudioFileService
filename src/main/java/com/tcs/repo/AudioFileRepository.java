package com.tcs.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcs.domain.AudioFile;

@Repository
public interface AudioFileRepository extends JpaRepository<AudioFile, Long> {
}
