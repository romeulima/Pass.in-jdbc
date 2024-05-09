package com.romeulima.passinjdbc.domain.event;

import java.util.Objects;

public class Event {

    private Integer id;

    private String title;

    private String details;

    private String slug;

    private Integer maximumAttendees;

    public Event(){
    }

    public Event(Integer id, String title, String details, String slug, Integer maximumAttendees) {
        this.id = id;
        this.title = title;
        this.details = details;
        this.slug = slug;
        this.maximumAttendees = maximumAttendees;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Integer getMaximumAttendees() {
        return maximumAttendees;
    }

    public void setMaximumAttendees(Integer maximumAttendees) {
        this.maximumAttendees = maximumAttendees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", details='" + details + '\'' +
                ", slug='" + slug + '\'' +
                ", maximumAttendees=" + maximumAttendees +
                '}';
    }
}
