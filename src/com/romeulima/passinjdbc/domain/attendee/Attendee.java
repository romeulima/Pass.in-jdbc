package com.romeulima.passinjdbc.domain.attendee;

import com.romeulima.passinjdbc.domain.event.Event;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Attendee implements Serializable {

    private Integer id;

    private String name;

    private String email;

    private LocalDateTime createdAt;

    private Event event;

    public Attendee(){
    }

    public Attendee(Integer id, String name, String email, LocalDateTime createdAt, Event event) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.event = event;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attendee attendee = (Attendee) o;
        return Objects.equals(id, attendee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Attendee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", event=" + event +
                '}';
    }
}
