package com.example.backend.service.client;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.backend.dto.response.NoticeResponse;
import com.example.backend.entity.Notice;
import com.example.backend.exception.BusinessException;
import com.example.backend.repository.NoticeRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ClientNoticeService {
    private final NoticeRepository noticeRepository;

    public ClientNoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    public List<NoticeResponse> getAllNotice() {
        List<Notice> notices = noticeRepository.findAll();
        List<NoticeResponse> noticeResponses = notices.stream().map(NoticeResponse::from).collect(Collectors.toList());

        return noticeResponses;
    }

    public NoticeResponse getNoticeById(Long id) {
        Notice notice = noticeRepository.findById(id).orElseThrow(() -> new BusinessException("該当のお知らせは存在しません"));

        return NoticeResponse.from(notice);
    }
}
