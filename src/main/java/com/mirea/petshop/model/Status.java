package com.mirea.petshop.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Status {
    @JsonProperty
    private String status = null;

    @JsonProperty
    private String description = null;

    public Status(String status, String description) {
        this.status = status;
        this.description = description;
    }

    public Status() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Status{" +
                "status='" + status + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
