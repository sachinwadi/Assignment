package com.cs.assignment.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Record implements Serializable {
    private static final long serialVersionUID = 1;

    private final String id;
    private final String state;
    private final String type;
    private final String host;
    private final long timestamp;

    @JsonCreator
    public Record(@JsonProperty(value = "id", required = true) String id,
                  @JsonProperty(value = "state", required = true) String state,
                  @JsonProperty(value = "type") String type,
                  @JsonProperty(value = "host") String host,
                  @JsonProperty(value = "timestamp", required = true) long timestamp) {
        this.id = id;
        this.state = state;
        this.type = type;
        this.host = host;
        this.timestamp = timestamp;
    }
}