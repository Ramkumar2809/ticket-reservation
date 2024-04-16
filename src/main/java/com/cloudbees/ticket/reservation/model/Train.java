package com.cloudbees.ticket.reservation.model;

import java.util.List;

public class Train {

    private Long trainNo;
    private String fromStationName;
    private String toStationName;
    private List<Section> sections;

    public Train() {
    }

    public Train(Long trainNo, String fromStationName, String toStationName) {
        this.trainNo = trainNo;
        this.fromStationName = fromStationName;
        this.toStationName = toStationName;
    }

    public Long getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(Long trainNo) {
        this.trainNo = trainNo;
    }

    public String getFromStationName() {
        return fromStationName;
    }

    public void setFromStationName(String fromStationName) {
        this.fromStationName = fromStationName;
    }

    public String getToStationName() {
        return toStationName;
    }

    public void setToStationName(String toStationName) {
        this.toStationName = toStationName;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }
}
