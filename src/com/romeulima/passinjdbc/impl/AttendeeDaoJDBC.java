package com.romeulima.passinjdbc.impl;

import com.romeulima.passinjdbc.dao.AttendeeDao;
import com.romeulima.passinjdbc.dao.EventDao;
import com.romeulima.passinjdbc.dao.factory.DaoFactory;
import com.romeulima.passinjdbc.db.exceptions.DbException;
import com.romeulima.passinjdbc.domain.attendee.Attendee;
import com.romeulima.passinjdbc.domain.event.Event;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class AttendeeDaoJDBC implements AttendeeDao {

    private final Connection connection;
    private static final EventDao eventDao = DaoFactory.createEventDao();

    public AttendeeDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Attendee attendee) {
        try {
            PreparedStatement pstm = connection.prepareStatement(
                    "INSERT INTO attendees "
                            + "(name, email, event_id) "
                            + "VALUES (?, ?, ?)"
                    , Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, attendee.getName());
            pstm.setString(2, attendee.getEmail());
            pstm.setInt(3, attendee.getEvent().getId());

            int affectedRows = pstm.executeUpdate();

            if (affectedRows > 0) {
                ResultSet rs = pstm.getGeneratedKeys();

                if (rs.next()) {
                    int id = rs.getInt(1);
                    attendee.setId(id);
                    attendee.setCreatedAt(LocalDateTime.now());
                }
             } else {
                throw new DbException("Unexpected error! No rows affected");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public Attendee findById(Integer id) {
        try {
            PreparedStatement pstm = connection.prepareStatement(
                    "SELECT attendees.*, events.title as EventName " +
                            "FROM attendees INNER JOIN events " +
                            "ON attendees.event_id = events.id " +
                            "WHERE attendees.id = ?"
            );
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                Event event = eventDao.findById(rs.getInt("event_id"));
                Attendee attendee = instantiateAttendee(rs, event);

                return attendee;
            }

            return null;

        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        }
    }

    @Override
    public List<Attendee> findAllAttendees() {
        try {
            PreparedStatement pstm = connection.prepareStatement(
                    "SELECT a.*, e.title as EventName " +
                            "FROM attendees AS a INNER JOIN events AS e " +
                            "ON a.event_id = e.id " +
                            "ORDER BY a.name"
            );
            ResultSet rs = pstm.executeQuery();

            List<Attendee> attendeeList = new ArrayList<>();
            Map<Integer, Event> map = new HashMap<>();

            while (rs.next()) {
                Event event = map.get(rs.getInt("event_id"));
                if (Objects.isNull(event)) {
                    event = eventDao.findById(rs.getInt("event_id"));
                    map.put(rs.getInt("event_id"),event);
                }
                Attendee attendee = instantiateAttendee(rs, event);
                attendeeList.add(attendee);
            }

            return attendeeList;

        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        }
    }

    @Override
    public List<Attendee> findByEvent(Event e) {
        try {
            PreparedStatement pstm = connection.prepareStatement(
                    "SELECT a.*, e.title AS EventName " +
                            "FROM attendees AS a INNER JOIN events AS e " +
                            "ON a.event_id = e.id " +
                            "WHERE a.event_id = ? ORDER BY a.name;"
            );

            pstm.setInt(1, e.getId());
            ResultSet rs = pstm.executeQuery();

            List<Attendee> attendeeList = new ArrayList<>();
            Map<Integer, Event> map = new HashMap<>();

            while (rs.next()) {
                Event event = map.get(rs.getInt("event_id"));
                if (Objects.isNull(event)) {
                    event = eventDao.findById(rs.getInt("event_id"));
                    map.put(rs.getInt("event_id"),event);
                }
                Attendee attendee = instantiateAttendee(rs, event);
                attendeeList.add(attendee);
            }

            return attendeeList;
        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        }
    }

    @Override
    public void update(Attendee attendee) {
        try {
            PreparedStatement pstm = connection.prepareStatement(
                    "UPDATE attendees " +
                            "SET name = ?, email = ?, event_id = ? " +
                            "WHERE id = ?"
            );
            pstm.setString(1, attendee.getName());
            pstm.setString(2, attendee.getEmail());
            pstm.setInt(3, attendee.getEvent().getId());
            pstm.setInt(4, attendee.getId());

            int affectedRows = pstm.executeUpdate();

            if (affectedRows == 0) {
                throw new DbException("Unexpected error! No rows affected!");
            }

            System.out.println("Updated!");

        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        }

    }

    @Override
    public void deleteById(Integer id) {
        try {
            PreparedStatement pstm = connection.prepareStatement(
                    "DELETE FROM attendees WHERE id = ?"
            );
            pstm.setInt(1, id);
            int affectedRows = pstm.executeUpdate();

            if (affectedRows == 0) {
                throw new DbException("Unexpected error! No attendee was deleted");
            }

            System.out.println("Deleted");

        } catch (SQLException ex) {
            throw new DbException(ex.getMessage());
        }
    }

    private Attendee instantiateAttendee(ResultSet rs, Event event) throws SQLException {
        Attendee attendee = new Attendee();
        attendee.setId(rs.getInt("id"));
        attendee.setName(rs.getString("name"));
        attendee.setEmail(rs.getString("email"));
        attendee.setEvent(event);
        attendee.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return attendee;
    }

}
