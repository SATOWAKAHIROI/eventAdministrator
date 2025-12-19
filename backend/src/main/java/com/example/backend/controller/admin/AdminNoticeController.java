package com.example.backend.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.common.ApiResponse;
import com.example.backend.dto.requests.NoticeRequest;
import com.example.backend.dto.response.NoticeResponse;
import com.example.backend.service.admin.AdminNoticeService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/admin/notice")
public class AdminNoticeController {

    private final AdminNoticeService adminNoticeService;

    public AdminNoticeController(AdminNoticeService adminNoticeService) {
        this.adminNoticeService = adminNoticeService;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<NoticeResponse>>> getAllNotice() {
        List<NoticeResponse> responses = adminNoticeService.getAllNotice();
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NoticeResponse>> getNoticeById(@PathVariable Long id) {
        NoticeResponse noticeResponse = adminNoticeService.getNoticeById(id);

        return ResponseEntity.ok(ApiResponse.success(noticeResponse));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<ApiResponse<NoticeResponse>> createNotice(@Valid @RequestBody NoticeRequest noticeRequest,
            @PathVariable Long userId) {
        NoticeResponse noticeResponse = adminNoticeService.createNotice(noticeRequest, userId);

        return ResponseEntity.ok(ApiResponse.success(noticeResponse));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<NoticeResponse>> editNoticeById(@PathVariable Long id,
            @Valid @RequestBody NoticeRequest noticeRequest) {
        NoticeResponse noticeResponse = adminNoticeService.editNoticeById(noticeRequest, id);

        return ResponseEntity.ok(ApiResponse.success(noticeResponse));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteNoticeById(@PathVariable Long id) {
        adminNoticeService.deleteNoticeById(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

}
