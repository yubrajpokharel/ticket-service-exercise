import com.walmart.store.recruiting.ticket.domain.SeatBlock;
import com.walmart.store.recruiting.ticket.domain.SeatHold;
import com.walmart.store.recruiting.ticket.domain.Venue;
import com.walmart.store.recruiting.ticket.service.TicketService;
import com.walmart.store.recruiting.ticket.service.impl.TicketServiceImpl;

import java.util.Map;

/**
 * Created by yubraj on 7/13/16.
 */
public class App {
    public static void main(String[] args) {
        Venue venue = new Venue(0, 2, 5);

        System.out.println("Initial Seats");
        for (int i =0; i < venue.getRows(); i++){
            for (int j = 0; j < venue.getSeatsPerRow(); j++) {
                System.out.print("\t "+venue.getSeats()[i][j]);
            }
            System.out.println();
        }
        System.out.println();

        TicketService ticketService = new TicketServiceImpl(venue);
        System.out.println("Initial Available Tickets : "+ticketService.numSeatsAvailable());

        ticketService.findAndHoldSeats(3);
        ticketService.findAndHoldSeats(2);
        ticketService.findAndHoldSeats(5);
        System.out.println("Available tickets after hold : "+ticketService.numSeatsAvailable());

        System.out.println();
        for (int i =0; i < venue.getRows(); i++){
            for (int j = 0; j < venue.getSeatsPerRow(); j++) {
                System.out.print("\t "+venue.getSeats()[i][j]);
            }
            System.out.println();
        }
        System.out.println();
        String key = null;
        Map<String, SeatHold> seatHoldMap = ticketService.getSeatHoldMap();
        Map<SeatHold, SeatBlock> holdMap = ticketService.getSeatBlockMap();
        for (String s : seatHoldMap.keySet()){
            System.out.println("Seats for seatID ["+s+"] :: "+holdMap.get(seatHoldMap.get(s)));
            key = s;
        }

        ticketService.reserveSeats(key);

        System.out.println("Final reserved is Demoted as 2");
        for (int i =0; i < venue.getRows(); i++){
            for (int j = 0; j < venue.getSeatsPerRow(); j++) {
                System.out.print("\t "+venue.getSeats()[i][j]);
            }
            System.out.println();
        }
        System.out.println();

//---------------------OUTPUT----------------------------------------
//        Initial Seats
//        0	 0	 0	 0	 0
//        0	 0	 0	 0	 0
//
//        Initial Available Tickets : 10
//        Available tickets after hold : 0
//
//        1	 1	 1	 1	 1
//        1	 1	 1	 1	 1
//
//        Seats for seatID [d695fdb3-3e04-4aa8-9934-175fd32219cc] :: SeatBlock From [0][3] -- [0][4]
//        Seats for seatID [c82a8d11-790d-4a36-8c31-b19a31b5081d] :: SeatBlock From [1][0] -- [1][4]
//        Seats for seatID [f8281d82-d15a-4115-8dc7-89244a3d569c] :: SeatBlock From [0][0] -- [0][2]
//        Final reserved is Demoted as 2
//        2	 2	 2	 1	 1
//        1	 1	 1	 1	 1
//------------------------------------------------------------------


    }
}
