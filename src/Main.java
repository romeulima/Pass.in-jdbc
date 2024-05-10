import com.romeulima.passinjdbc.dao.EventDao;
import com.romeulima.passinjdbc.dao.factory.DaoFactory;
import com.romeulima.passinjdbc.domain.event.Event;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        EventDao eventDao = DaoFactory.createEventDao();

//        Event newEvent = new Event("Romeu Mentory", "Mentoria sobre carreira na programacao", 1000);
//        eventDao.insert(newEvent);
//        System.out.println("Novo evento registrado\n" + newEvent);

//        Event event = eventDao.findById(2);
//        System.out.println(event);
//
//        System.out.println();
//
//        List<Event> eventList = eventDao.findAllEvents();
//
//        eventList.forEach(System.out::println);

//        System.out.println();
//        newEvent.setMaximumAttendees(2);
//        newEvent.setTitle("Romeu - Mentoria");
//        eventDao.update(newEvent);

        Event event = eventDao.findById(2);
        event.setTitle("Romeu - Mentory");
        event.setMaximumAttendees(2);
        eventDao.update(event);
    }
}