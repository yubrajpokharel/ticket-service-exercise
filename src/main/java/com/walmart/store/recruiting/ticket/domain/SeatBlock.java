package com.walmart.store.recruiting.ticket.domain;

/**
 * Created by yubraj on 7/13/16.
 *
 */
public class SeatBlock {
    public int rowFrom;
    public int columnFrom;
    public int rowTo;
    public int columnTo;

    /**
     *
     * @param rowFrom initial place from where the row will start
     * @param columnFrom initial place from where the column will start
     * @param rowTo  initial place from where the row will stop
     * @param columnTo initial place from where the column will stop
     */
    public SeatBlock(int rowFrom, int columnFrom, int rowTo, int columnTo) {
        this.rowFrom = rowFrom;
        this.columnFrom = columnFrom;
        this.rowTo = rowTo;
        this.columnTo = columnTo;
    }

    public int getRowFrom() {
        return rowFrom;
    }

    public void setRowFrom(int rowFrom) {
        this.rowFrom = rowFrom;
    }

    public int getColumnFrom() {
        return columnFrom;
    }

    public void setColumnFrom(int columnFrom) {
        this.columnFrom = columnFrom;
    }

    public int getRowTo() {
        return rowTo;
    }

    public void setRowTo(int rowTo) {
        this.rowTo = rowTo;
    }

    public int getColumnTo() {
        return columnTo;
    }

    public void setColumnTo(int columnTo) {
        this.columnTo = columnTo;
    }

    @Override
    public String toString() {
        return "SeatBlock From ["+rowFrom +"]["+columnFrom +"] -- ["+rowTo +"]["+columnTo +"]";

    }
}
