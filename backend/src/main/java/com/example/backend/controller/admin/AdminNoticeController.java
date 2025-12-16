package com.example.backend.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.common.ApiResponse;
import com.example.backend.dto.requests.NoticeRequest;
import com.example.backend.dto.response.NoticeResponse;

import jakarta.validation.Valid;

import java.util.ArrayList;
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
    @GetMapping()
    public ResponseEntity<ApiResponse<List<NoticeResponse>>> getAllNotice() {
        // お知らせ取得処理は後で実装する
        List<NoticeResponse> responses = new ArrayList<>();
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NoticeResponse>> getNoticeById(@Valid @RequestBody NoticeRequest noticeRequest,
            @PathVariable Long id) {
        // お知らせ取得処理は後で実装する
        NoticeResponse noticeResponse = new NoticeResponse();

        return ResponseEntity.ok(ApiResponse.success(noticeResponse));
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<NoticeResponse>> createNotice(@Valid @RequestBody NoticeRequest noticeRequest) {
        // お知らせ作成処理は後で実装する
        NoticeResponse noticeResponse = new NoticeResponse();

        return ResponseEntity.ok(ApiResponse.success(noticeResponse));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<NoticeResponse>> editNoticeById(@PathVariable Long id,
            @Valid @RequestBody NoticeRequest noticeRequest) {
        // お知らせ編集処理は後で実装する
        NoticeResponse noticeResponse = new NoticeResponse();

        return ResponseEntity.ok(ApiResponse.success(noticeResponse));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteNoticeById(@PathVariable Long id) {
        // お知らせ削除処理は後で実装する
        return ResponseEntity.ok(ApiResponse.success(null));
    }

}
