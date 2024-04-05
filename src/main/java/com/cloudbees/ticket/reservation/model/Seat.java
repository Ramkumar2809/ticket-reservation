package com.cloudbees.ticket.reservation.model;

public class Seat {
    private Long seatNo;
    private Passenger passenger;

    public Seat() {
    }

    public Seat(Long seatNo) {
        this.seatNo = seatNo;
    }

    public Long getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(Long seatNo) {
        this.seatNo = seatNo;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }
}
