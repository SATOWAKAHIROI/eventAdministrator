package com.example.backend.service.admin;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.backend.dto.requests.NoticeRequest;
import com.example.backend.dto.response.NoticeResponse;
import com.example.backend.entity.Notice;
import com.example.backend.entity.User;
import com.example.backend.repository.NoticeRepository;
import com.example.backend.repository.UserRepository;

@Service
public class AdminNoticeService {
    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

    public AdminNoticeService(NoticeRepository noticeRepository, UserRepository userRepository) {
        this.noticeRepository = noticeRepository;
        this.userRepository = userRepository;
    }

    public List<NoticeResponse> getAllNotice() {
        List<Notice> notices = noticeRepository.findAll();
        List<NoticeResponse> noticeResponses = notices.stream().map(NoticeResponse::from).collect(Collectors.toList());

        return noticeResponses;
    }

    public NoticeResponse getNoticeById(Long id) {
        Notice notice = noticeRepository.findById(id).orElse(null);

        // エラー処理は後で実装
        if (notice == null) {
            return null;
        }

        return NoticeResponse.from(notice);
    }

    public NoticeResponse createNotice(NoticeRequest noticeRequest, Long userId) {
        User user = userRepository.findById(userId).orElse(null);

        // エラー処理は後で実装
        if (user == null) {
            return null;
        }

        Notice notice = new Notice();

        notice.setSubject(noticeRequest.getSubject());
        notice.setBody(noticeRequest.getBody());
        notice.setCreatedBy(user);

        noticeRepository.save(notice);

        return NoticeResponse.from(notice);
    }

    public NoticeResponse editNoticeById(NoticeRequest noticeRequest, Long id) {
        Notice notice = noticeRepository.findById(id).orElse(null);

        // エラー処理は後で実装
        if (notice == null) {
            return null;
        }

        notice.setSubject(noticeRequest.getSubject());
        notice.setBody(noticeRequest.getBody());

        noticeRepository.save(notice);

        return NoticeResponse.from(notice);
    }

    public void deleteNoticeById(Long id) {
        if (noticeRepository.existsById(id)) {
            noticeRepository.deleteById(id);
        }
    }
}
