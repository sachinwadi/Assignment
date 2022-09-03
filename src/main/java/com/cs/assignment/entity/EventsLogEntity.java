package com.cs.assignment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "EventsLog", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventsLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(name = "Event_Id")
    @NotNull
    private String eventId;

    @Column(name = "Event_Duration")
    private long eventDuration;

    @Column(name = "Type")
    private String type;

    @Column(name = "Host")
    private String host;

    @Column(name = "Alert")
    private boolean alert;
}
