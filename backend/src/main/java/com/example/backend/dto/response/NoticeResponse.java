package com.example.backend.dto.response;

import com.example.backend.entity.Notice;

public class NoticeResponse {
    private Long id;
    private String subject;
    private String body;
    private UserResponse createdBy;
    private String updatedAt;

    public static NoticeResponse from(Notice notice){
        NoticeResponse noticeResponse = new NoticeResponse();

        noticeResponse.setId(notice.getId());
        noticeResponse.setBody(notice.getBody());
        noticeResponse.setCreatedBy(UserResponse.from(notice.getCreatedBy()));
        noticeResponse.setUpdatedAt(notice.getUpdatedAt().toLocalDate().toString());

        return noticeResponse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public UserResponse getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserResponse createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
