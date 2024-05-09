package com.romeulima.passinjdbc.dao;

import com.romeulima.passinjdbc.domain.event.Event;

import java.util.List;

public interface EventDao {
    void insert(Event event);
    Event findById(Integer id);
    List<Event> findAllEvents();
    void update(Event event);
    void deleteById(Integer id);
}

// Create, Read, Update, delete - CRUD