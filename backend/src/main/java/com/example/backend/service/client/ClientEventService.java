package com.example.backend.service.client;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.backend.dto.response.EventResponse;
import com.example.backend.entity.Event;
import com.example.backend.exception.BusinessException;
import com.example.backend.repository.EventRepository;

@Service
public class ClientEventService {
    private final EventRepository eventRepository;

    public ClientEventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<EventResponse> getAllEvent() {
        List<Event> events = eventRepository.findAll();
        List<EventResponse> eventResponses = events.stream().map(EventResponse::from).collect(Collectors.toList());

        return eventResponses;
    }

    public EventResponse getEventById(Long id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new BusinessException("該当のイベントは存在しません"));
        EventResponse eventResponse = EventResponse.from(event);

        return eventResponse;
    }
}
