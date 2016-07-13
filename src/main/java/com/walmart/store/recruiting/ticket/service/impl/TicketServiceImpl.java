package com.walmart.store.recruiting.ticket.service.impl;

import com.walmart.store.recruiting.ticket.domain.SeatBlock;
import com.walmart.store.recruiting.ticket.domain.SeatHold;
import com.walmart.store.recruiting.ticket.domain.Venue;
import com.walmart.store.recruiting.ticket.service.TicketService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A ticket service implementation.
 */
public class TicketServiceImpl implements TicketService {

    private Venue venue;
    private int seatsAvailable;
    private int seatsReserved;
    private Map<String, SeatHold> seatHoldMap = Collections.synchronizedMap( new HashMap<>());
    private Map<SeatHold, SeatBlock> seatBlockMap = Collections.synchronizedMap( new HashMap<>());

    public TicketServiceImpl(Venue venue) {
        this.venue = venue;
        seatsAvailable = venue.getMaxSeats();
    }

    @Override
    public int numSeatsAvailable() {
        return seatsAvailable;
    }

    public int numSeatsReserved() {
        return this.seatsReserved;
    }

    /**
     * made this method synchronized for the thread safe
     */
    @Override
    public synchronized Optional<SeatHold> findAndHoldSeats(int numSeats) {
        Optional<SeatHold> optionalSeatHold = Optional.empty();

        //implementation of the remove unused seats method
        removeExpiredSeatHold();

        if (seatsAvailable >= numSeats) {

            String holdId = generateId();
            SeatHold seatHold = new SeatHold(holdId, numSeats);
            optionalSeatHold = Optional.of(seatHold);
            seatHoldMap.put(holdId, seatHold);
            seatsAvailable -= numSeats;

            int counter = 0;
            boolean isInitialRow = false;
            int iniRow = 0, iniCol = 0, lastRow = 0, lastCol = 0;

            for (int i = 0; i < venue.getRows(); i++) {
                for (int j = 0; j < venue.getSeatsPerRow(); j++) {
                    if(venue.getSeats()[i][j] == 0){
                        if(!isInitialRow){ iniRow = i; iniCol = j; isInitialRow = true; }
                        counter++;
                        venue.getSeats()[i][j] = 1;
                    }
                    if(counter == numSeats){
                        lastRow = i;
                        lastCol = j;
                        break;
                    }
                }
                if(counter == numSeats){

                    SeatBlock sb = new SeatBlock(iniRow, iniCol, lastRow, lastCol);
                    seatBlockMap.put(seatHold, sb);
                    break;
                }
            }
        }
        return optionalSeatHold;
    }

    public void showSeatBlock(String seatHoldId){
        SeatHold seatHold = seatHoldMap.get(seatHoldId);
        System.out.println("Seat booked from : "+ seatBlockMap.get(seatHold));
    }


    @Override
    public void removeExpiredSeatHold(){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expired = now.minusSeconds(5);
        Instant instant = expired.atZone(ZoneId.systemDefault()).toInstant();

        List<SeatHold> seatHolds = getHoldTickets(instant);
        if(!seatHolds.isEmpty()){
            seatHoldMap.remove(seatHolds);
        }
        seatsAvailable+=seatHolds.size();
    }

    /**
     * Returns the list of seats which are hold and are more then the given time i.e 5 Seconds
     * @param instant
     * @return List of the seatHolds that are hold more then 5 seconds
     */
    private List<SeatHold> getHoldTickets(Instant instant) {
        List<SeatHold> seatHolds = seatHoldMap.values()
                                        .stream()
                                        .filter(seatHold -> seatHold.getDate()
                                        .isBefore(LocalDateTime.ofInstant(instant, ZoneId.systemDefault())))
                                        .collect(Collectors.toList());
        return seatHolds;
    }



    @Override
    public synchronized Optional<String> reserveSeats(String seatHoldId) {
        Optional<String> optionalReservation = Optional.empty();;
        SeatHold seatHold = seatHoldMap.get(seatHoldId);
        if (seatHold != null) {
            seatsReserved += seatHold.getNumSeats();
            optionalReservation =  Optional.of(seatHold.getId());
            reserveSeatBlocks(seatHold);
            seatHoldMap.remove(seatHoldId);
        }

        return optionalReservation;
    }

    @Override
    public synchronized void reserveSeatBlocks(SeatHold seatHold){
        SeatBlock seatBlock = seatBlockMap.get(seatHold);
        for (int i = seatBlock.getRowFrom(); i <= seatBlock.getRowTo(); i++ ) {
            //System.out.println(i);
            for (int j = seatBlock.getColumnFrom(); j <= seatBlock.getColumnTo() ; j++ ) {
                venue.setSeatAsFinal(i,j);
            }
        }

    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public Map<String, SeatHold> getSeatHoldMap() {
        return seatHoldMap;
    }

    @Override
    public Map<SeatHold, SeatBlock> getSeatBlockMap() {
        return seatBlockMap;
    }
}
