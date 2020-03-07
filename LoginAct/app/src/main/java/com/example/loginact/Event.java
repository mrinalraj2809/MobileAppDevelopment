package com.example.loginact;

public class Event {

    String agenda;
    String dateOfEvent;
    String timeOfEvent;
    String needsOfEvent;
    String havesOfEvent;
    String eventUrl;

    public Event(String agenda, String dateOfEvent, String timeOfEvent, String needsOfEvent, String havesOfEvent,String eventUrl) {
        this.agenda = agenda;
        this.dateOfEvent = dateOfEvent;
        this.timeOfEvent = timeOfEvent;
        this.needsOfEvent = needsOfEvent;
        this.havesOfEvent = havesOfEvent;
        this.eventUrl = eventUrl;
    }

    public String getAgenda() {
        return agenda;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    public String getDateOfEvent() {
        return dateOfEvent;
    }

    public void setDateOfEvent(String dateOfEvent) {
        this.dateOfEvent = dateOfEvent;
    }

    public String getTimeOfEvent() {
        return timeOfEvent;
    }

    public void setTimeOfEvent(String timeOfEvent) {
        this.timeOfEvent = timeOfEvent;
    }

    public String getNeedsOfEvent() {
        return needsOfEvent;
    }

    public void setNeedsOfEvent(String needsOfEvent) {
        this.needsOfEvent = needsOfEvent;
    }

    public String getEventUrl() {
        return eventUrl;
    }

    public void setEventUrl(String eventUrl) {
        this.eventUrl = eventUrl;
    }

    public String getHavesOfEvent() {
        return havesOfEvent;
    }

    public void setHavesOfEvent(String havesOfEvent) {
        this.havesOfEvent = havesOfEvent;
    }
}
