package com.example.backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    public List<Notice> findByCreatedAtAfter(LocalDateTime createdAt);
}
