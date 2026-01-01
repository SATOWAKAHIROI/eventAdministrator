package com.example.backend.controller.client;

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
import com.example.backend.service.client.ClientNoticeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/client/notice")
public class ClientNoticeController {
    private final ClientNoticeService clientNoticeService;

    public ClientNoticeController(ClientNoticeService clientNoticeService) {
        this.clientNoticeService = clientNoticeService;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<NoticeResponse>>> getAllNotice() {
        List<NoticeResponse> responses = clientNoticeService.getAllNotice();
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NoticeResponse>> getNoticeById(@Valid @RequestBody NoticeRequest noticeRequest,
            @PathVariable Long id) {
        NoticeResponse noticeResponse = clientNoticeService.getNoticeById(id);

        return ResponseEntity.ok(ApiResponse.success(noticeResponse));
    }
}
