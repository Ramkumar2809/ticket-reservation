package com.cloudbees.ticket.reservation.model;

public class Passenger {

    private User user;
    private String fromStation;
    private String toStation;
    private Double fare;

    public Passenger(User user, String fromStation, String toStation, Double fare) {
        this.user = user;
        this.fromStation = fromStation;
        this.toStation = toStation;
        this.fare = fare;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFromStation() {
        return fromStation;
    }

    public void setFromStation(String fromStation) {
        this.fromStation = fromStation;
    }

    public String getToStation() {
        return toStation;
    }

    public void setToStation(String toStation) {
        this.toStation = toStation;
    }

    public Double getFare() {
        return fare;
    }

    public void setFare(Double fare) {
        this.fare = fare;
    }
}
