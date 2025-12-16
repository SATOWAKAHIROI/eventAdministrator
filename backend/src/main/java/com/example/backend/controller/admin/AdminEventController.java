package com.example.backend.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.common.ApiResponse;
import com.example.backend.dto.requests.EventRequest;
import com.example.backend.dto.response.EventResponse;

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
@RequestMapping("/admin/event")
public class AdminEventController {
    @GetMapping
    public ResponseEntity<ApiResponse<List<EventResponse>>> getAllEvent() {
        // イベント取得処理は後で実装する
        List<EventResponse> responses = new ArrayList<>();
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EventResponse>> getEventById(@Valid @RequestBody EventRequest eventRequest,
            @PathVariable Long id) {
        // イベント取得処理は後で実装する
        EventResponse eventResponse = new EventResponse();
        return ResponseEntity.ok(ApiResponse.success(eventResponse));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EventResponse>> createEvent(@Valid @RequestBody EventRequest eventRequest) {
        // イベント作成処理は後で実装する
        EventResponse eventResponse = new EventResponse();

        return ResponseEntity.ok(ApiResponse.success(eventResponse));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EventResponse>> editEventById(@Valid @RequestBody EventRequest eventRequest,
            @PathVariable Long id) {
        // イベント編集処理は後で実装する
        EventResponse eventResponse = new EventResponse();
        return ResponseEntity.ok(ApiResponse.success(eventResponse));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEventById(@PathVariable Long id) {
        // イベント削除処理は後で実装する
        return ResponseEntity.ok(ApiResponse.success(null));
    }

}
