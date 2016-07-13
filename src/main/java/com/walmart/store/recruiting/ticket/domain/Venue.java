package com.walmart.store.recruiting.ticket.domain;

/**
 * This class represents a venue where customers can buy tickets to see a show.
 * Our Venue seating is rectangular to simplify things.
 */
public class Venue {

    private final int id;
    private final int rows;
    private final int seatsPerRow;

    //used to make the seats representing in the form of matrix
    private int[][] seats;

    public Venue(int id, int rows, int seatsPerRow) {
        this.id = id;
        this.rows = rows;
        this.seatsPerRow = seatsPerRow;

        // making the matrix of the seats
        seats = new int[rows][seatsPerRow];

    }


    /**
     * @return the number of rows of seats in the venue
     */
    public int getRows() {
        return rows;
    }

    /**
     * @return the number of seats in each row
     */
    public int getSeatsPerRow() {
        return seatsPerRow;
    }

    /**
     * @return the total number of seats in the venue
     */
    public int getMaxSeats() {
        return rows * seatsPerRow;
    }

    /**
     * @return the array of seats in the form of matrix
     */
    public int[][] getSeats() {
        return seats;
    }

    public void setSeatAsFinal(int i , int j){
        seats[i][j] = 2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Venue venue = (Venue) o;

        return id == venue.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

}
