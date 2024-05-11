import com.romeulima.passinjdbc.dao.AttendeeDao;
import com.romeulima.passinjdbc.dao.EventDao;
import com.romeulima.passinjdbc.dao.factory.DaoFactory;
import com.romeulima.passinjdbc.domain.attendee.Attendee;
import com.romeulima.passinjdbc.domain.event.Event;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AttendeeDao dao = DaoFactory.createAttendeeDao();
        EventDao dao2 = DaoFactory.createEventDao();

        List<Attendee> attendees = dao.findAllAttendees();

        attendees.forEach(System.out::println);

        System.out.println();
        System.out.print("Enter the attendee id that you want delete: ");
        int id = sc.nextInt();
        dao.deleteById(id);


    }
}