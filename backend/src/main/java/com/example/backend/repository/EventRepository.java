package com.example.backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.entity.Event;
import com.example.backend.entity.User;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    public List<Event> findAllByStartDateBetweenOrderByStartDateDesc(LocalDate from, LocalDate to);
    public List<Event> findAllByEndDateBetweenOrderByEndDateDesc(LocalDate from, LocalDate to);
    public long countByCreatedBy(User createdBy);
}
