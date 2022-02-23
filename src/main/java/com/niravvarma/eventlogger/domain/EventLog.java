package com.niravvarma.eventlogger.domain;

import lombok.AllArgsConstructor;
import javax.persistence.*;
import java.util.Objects;

@AllArgsConstructor
@Entity
public class EventLog {

    public EventLog(){}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private Long duration;
    private boolean alert;
    private String host;
    private String type;

    public Long getId() {
        return id;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + id +
                ", eventDuration='" + duration + '\'' +
                ", eventType='" + type + '\'' +
                ", eventHost='" + host + '\'' +
                ", eventAlert=" + alert +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventLog event = (EventLog) o;
        return duration.equals(event.duration) &&
                alert == event.alert &&
                id.equals(event.id) &&
                Objects.equals(type, event.type) &&
                Objects.equals(host, event.host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, duration, type, host, alert);
    }

}
