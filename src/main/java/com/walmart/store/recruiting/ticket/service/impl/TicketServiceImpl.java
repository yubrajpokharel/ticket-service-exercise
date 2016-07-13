package com.walmart.store.recruiting.ticket.service.impl;

import com.walmart.store.recruiting.ticket.domain.SeatHold;
import com.walmart.store.recruiting.ticket.domain.Venue;
import com.walmart.store.recruiting.ticket.service.TicketService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * A ticket service implementation.
 */
public class TicketServiceImpl implements TicketService {

    private int seatsAvailable;
    private int seatsReserved;
    private Map<String, SeatHold> seatHoldMap = new HashMap<>();

    public TicketServiceImpl(Venue venue) {
        seatsAvailable = venue.getMaxSeats();
    }

    @Override
    public int numSeatsAvailable() {
        return seatsAvailable;
    }

    public int numSeatsReserved() {
        return this.seatsReserved;
    }

    @Override
    public Optional<SeatHold> findAndHoldSeats(int numSeats) {
        Optional<SeatHold> optionalSeatHold = Optional.empty();

        if (seatsAvailable >= numSeats) {
            String holdId = generateId();
            SeatHold seatHold = new SeatHold(holdId, numSeats);
            optionalSeatHold = Optional.of(seatHold);
            seatHoldMap.put(holdId, seatHold);
            seatsAvailable -= numSeats;
        }

        return optionalSeatHold;
    }

    @Override
    public Optional<String> reserveSeats(String seatHoldId) {
        Optional<String> optionalReservation = Optional.empty();;
        SeatHold seatHold = seatHoldMap.get(seatHoldId);
        if (seatHold != null) {
            seatsReserved += seatHold.getNumSeats();
            optionalReservation =  Optional.of(seatHold.getId());
            seatHoldMap.remove(seatHoldId);
        }

        return optionalReservation;
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }

}
