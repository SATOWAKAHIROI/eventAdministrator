package com.example.backend.service.admin;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.backend.dto.requests.NoticeRequest;
import com.example.backend.dto.response.NoticeResponse;
import com.example.backend.entity.Notice;
import com.example.backend.entity.User;
import com.example.backend.exception.BusinessException;
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
        Notice notice = noticeRepository.findById(id).orElseThrow(() -> new BusinessException("該当のお知らせは存在しません"));

        return NoticeResponse.from(notice);
    }

    public NoticeResponse createNotice(NoticeRequest noticeRequest, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException("該当のユーザーは存在しません"));

        Notice notice = new Notice();

        notice.setSubject(noticeRequest.getSubject());
        notice.setBody(noticeRequest.getBody());
        notice.setCreatedBy(user);

        noticeRepository.save(notice);

        return NoticeResponse.from(notice);
    }

    public NoticeResponse editNoticeById(NoticeRequest noticeRequest, Long id) {
        Notice notice = noticeRepository.findById(id).orElseThrow(() -> new BusinessException("該当のお知らせは存在しません"));

        notice.setSubject(noticeRequest.getSubject());
        notice.setBody(noticeRequest.getBody());

        noticeRepository.save(notice);

        return NoticeResponse.from(notice);
    }

    public void deleteNoticeById(Long id) {
        if (!noticeRepository.existsById(id)) {
            throw new BusinessException("該当のお知らせは既に存在しません");
        }
        noticeRepository.deleteById(id);
    }
}
