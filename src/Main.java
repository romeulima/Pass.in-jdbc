import com.romeulima.passinjdbc.dao.AttendeeDao;
import com.romeulima.passinjdbc.dao.EventDao;
import com.romeulima.passinjdbc.dao.factory.DaoFactory;
import com.romeulima.passinjdbc.domain.attendee.Attendee;
import com.romeulima.passinjdbc.domain.event.Event;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        EventDao eventDao = DaoFactory.createEventDao();
        AttendeeDao attendeeDao = DaoFactory.createAttendeeDao();

        List<Attendee> attendees = attendeeDao.findAllAttendees();

        attendees.forEach(System.out::println);
        System.out.println();

        List<Attendee> attendeesByEvent = attendeeDao.findByEvent(eventDao.findById(1));

        attendeesByEvent.forEach(System.out::println);
    }
}