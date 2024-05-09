package com.romeulima.passinjdbc.dao;

import com.romeulima.passinjdbc.domain.attendee.Attendee;
import com.romeulima.passinjdbc.domain.event.Event;

import java.util.List;

public interface AttendeeDao {
    void insert(Attendee event);
    Attendee findById(Integer id);
    List<Attendee> findAllAttendees();
    List<Attendee> findByEvent(Event event);
    void update(Attendee attendee);
    void deleteById(Integer id);
}
// Create, Read, Update, delete - CRUD
