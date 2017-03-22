package com.huangshihe.analysisweb.model;

/**
 * Created by root on 3/21/17.
 */
public class UserRecordPer {
    private double agent;
    private double ip;
    private double event;
    private double event_type;
    private String username;
    private double count;

    public UserRecordPer(){

    }

    public UserRecordPer(double agent, double ip, double event, double event_type, double count) {
        this.agent = agent;
        this.ip = ip;
        this.event = event;
        this.event_type = event_type;
        this.count = count;
    }

    public double getAgent() {
        return agent;
    }

    public void setAgent(double agent) {
        this.agent = agent;
    }

    public double getIp() {
        return ip;
    }

    public void setIp(double ip) {
        this.ip = ip;
    }

    public double getEvent() {
        return event;
    }

    public void setEvent(double event) {
        this.event = event;
    }

    public double getEvent_type() {
        return event_type;
    }

    public void setEvent_type(double event_type) {
        this.event_type = event_type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }
}
