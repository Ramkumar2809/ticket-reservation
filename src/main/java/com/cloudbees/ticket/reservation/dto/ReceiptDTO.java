package com.cloudbees.ticket.reservation.dto;

public class ReceiptDTO {
    private String user;
    private String from;
    private String to;
    private Double fare;
    private String section;
    private Long seatNo;

    public ReceiptDTO() {

    }

    public ReceiptDTO(String user, String from, String to, Double fare, String section, Long seatNo) {
        this.user = user;
        this.from = from;
        this.to = to;
        this.fare = fare;
        this.section = section;
        this.seatNo = seatNo;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Double getFare() {
        return fare;
    }

    public void setFare(Double fare) {
        this.fare = fare;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Long getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(Long seatNo) {
        this.seatNo = seatNo;
    }
}
