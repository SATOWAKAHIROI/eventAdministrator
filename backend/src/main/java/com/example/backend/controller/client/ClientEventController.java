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
import com.example.backend.dto.requests.EventRequest;
import com.example.backend.dto.response.EventResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/client/event")
public class ClientEventController {
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
}
