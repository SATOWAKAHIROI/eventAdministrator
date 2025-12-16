package com.example.backend.controller.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.common.ApiResponse;
import com.example.backend.dto.requests.NoticeRequest;
import com.example.backend.dto.response.NoticeResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/client/notice")
public class ClientNoticeController {
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
}
