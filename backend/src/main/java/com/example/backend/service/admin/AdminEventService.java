package com.example.backend.service.admin;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.backend.dto.requests.EventRequest;
import com.example.backend.dto.response.EventResponse;
import com.example.backend.entity.Event;
import com.example.backend.entity.User;
import com.example.backend.exception.BusinessException;
import com.example.backend.repository.EventRepository;
import com.example.backend.repository.UserRepository;

@Service
public class AdminEventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public AdminEventService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
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

    public EventResponse createEvent(EventRequest eventRequest, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException("該当のユーザーは存在しません"));

        Event event = new Event();

        event.setName(eventRequest.getName());
        event.setOverview(eventRequest.getOverview());
        event.setStartDate(eventRequest.getStartDate());
        event.setEndDate(eventRequest.getEndDate());
        event.setCreatedBy(user);

        eventRepository.save(event);

        return EventResponse.from(event);
    }

    public EventResponse editEventById(EventRequest eventRequest, Long id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new BusinessException("該当のイベントは存在しません"));

        event.setName(eventRequest.getName());
        event.setOverview(eventRequest.getOverview());
        event.setStartDate(eventRequest.getStartDate());
        event.setEndDate(eventRequest.getEndDate());

        eventRepository.save(event);

        return EventResponse.from(event);
    }

    public void deleteEventById(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new BusinessException("該当のイベントは既に存在していません");
        }
        eventRepository.deleteById(id);
    }
}
