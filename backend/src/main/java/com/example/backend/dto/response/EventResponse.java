package com.example.backend.dto.response;

import com.example.backend.entity.Event;

public class EventResponse {
    private Long id;
    private String name;
    private String overview;
    private String startDate;
    private String endDate;
    private UserResponse createdBy;

    public static EventResponse from(Event event){
        EventResponse eventResponse = new EventResponse();
        eventResponse.setId(event.getId());
        eventResponse.setName(event.getName());
        eventResponse.setOverview(event.getOverview());
        eventResponse.setStartDate(event.getStartDate().toString());
        eventResponse.setEndDate(event.getEndDate().toString());
        eventResponse.setCreatedBy(UserResponse.from(event.getCreatedBy()));

        return eventResponse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public UserResponse getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserResponse createdBy) {
        this.createdBy = createdBy;
    }
}
