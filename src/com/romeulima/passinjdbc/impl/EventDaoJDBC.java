package com.romeulima.passinjdbc.impl;

import com.romeulima.passinjdbc.dao.EventDao;
import com.romeulima.passinjdbc.db.exceptions.DbException;
import com.romeulima.passinjdbc.domain.event.Event;

import java.sql.*;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class EventDaoJDBC implements EventDao {

    private final Connection connection;

    public EventDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Event e) {
        try {
            PreparedStatement pstm = connection.prepareStatement(
                    "INSERT INTO events "
                    + "(title, details, slug, maximum_attendees) "
                    + "VALUES (?, ?, ?, ?);",
                    Statement.RETURN_GENERATED_KEYS);

            pstm.setString(1, e.getTitle());
            pstm.setString(2, e.getDetails());
            String slug =  createSlug(e.getTitle());
            pstm.setString(3, slug);
            pstm.setInt(4, e.getMaximumAttendees());

            int affectedRows = pstm.executeUpdate();

            if (affectedRows > 0) {
                ResultSet rs = pstm.getGeneratedKeys();

                if (rs.next()) {
                    int id = rs.getInt(1);
                    e.setId(id);
                    e.setSlug(slug);
                }

            } else {
                throw new DbException("Unexpected error! No rows affected");
            }

        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        }
    }

    @Override
    public Event findById(Integer id) {
        try {
            PreparedStatement pstm = connection.prepareStatement(
                    "SELECT * FROM events WHERE id = ?");

            pstm.setInt(1, id);

            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                return instantiateEvent(rs);
            }

            throw new DbException("Event not found with id: " + id);

        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        }
    }

    @Override
    public List<Event> findAllEvents() {
        try {
            PreparedStatement pstm = connection.prepareStatement(
                    "SELECT * FROM events ORDER BY id");

            ResultSet rs = pstm.executeQuery();

            List<Event> list = new ArrayList<>();

            while (rs.next()) {
                Event event = instantiateEvent(rs);
                list.add(event);
            }

            return list;
        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        }
    }

    @Override
    public void update(Event e) {
        try {
            PreparedStatement pstm = connection.prepareStatement(
                    "UPDATE events "
                    + "SET title = ?, details = ?, slug = ?, maximum_attendees = ? "
                    + "WHERE id = ?");

            pstm.setString(1, e.getTitle());
            pstm.setString(2, e.getDetails());
            String slug = createSlug(e.getTitle());
            pstm.setString(3, slug);
            pstm.setInt(4, e.getMaximumAttendees());
            pstm.setInt(5, e.getId());

            int affectedRows = pstm.executeUpdate();

            if (affectedRows == 0) {
                throw new DbException("No rows affected");
            } else {
                System.out.println("Updated!");
            }

        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {
        try {
            PreparedStatement pstm = connection.prepareStatement(
                    "DELETE FROM events WHERE id = ?");

            pstm.setInt(1, id);

            int affectedRows = pstm.executeUpdate();

            if (affectedRows == 0) {
                throw new DbException("No rows affected");
            } else {
                System.out.println("Deleted!");
            }

        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        }
    }

    private String createSlug(String text){
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "")
                .replaceAll("[^\\w\\s]", "")
                .replaceAll("\\s+", "")
                .toLowerCase();
    }

    private Event instantiateEvent(ResultSet rs) throws SQLException {
        Event event = new Event();
        event.setId(rs.getInt("id"));
        event.setTitle(rs.getString("title"));
        event.setDetails(rs.getString("details"));
        event.setSlug(rs.getString("slug"));
        event.setMaximumAttendees(rs.getInt("maximum_attendees"));

        return event;
    }
}
