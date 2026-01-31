package com.example.backend.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.common.ApiResponse;
import com.example.backend.dto.requests.EventRequest;
import com.example.backend.dto.response.EventResponse;
import com.example.backend.security.JwtTokenProvider.UserPrincipal;
import com.example.backend.service.admin.AdminEventService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/admin/event")
public class AdminEventController {
    private final AdminEventService adminEventService;

    public AdminEventController(AdminEventService adminEventService) {
        this.adminEventService = adminEventService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EventResponse>>> getAllEvent() {
        List<EventResponse> responses = adminEventService.getAllEvent();
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EventResponse>> getEventById(@PathVariable Long id) {
        EventResponse eventResponse = adminEventService.getEventById(id);
        return ResponseEntity.ok(ApiResponse.success(eventResponse));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EventResponse>> createEvent(@Valid @RequestBody EventRequest eventRequest,
            Authentication authentication) {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        Long userId = principal.getUserId();
        EventResponse eventResponse = adminEventService.createEvent(eventRequest, userId);

        return ResponseEntity.ok(ApiResponse.success(eventResponse));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EventResponse>> editEventById(@Valid @RequestBody EventRequest eventRequest,
            @PathVariable Long id) {
        EventResponse eventResponse = adminEventService.editEventById(eventRequest, id);
        return ResponseEntity.ok(ApiResponse.success(eventResponse));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEventById(@PathVariable Long id) {
        adminEventService.deleteEventById(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

}
