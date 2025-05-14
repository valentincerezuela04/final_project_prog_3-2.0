package com.musicspring.app.music_app.exceptions;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ErrorDetails {
    private Timestamp date;
    private String message;
    private String details;


    public ErrorDetails(String message, String details) {

        this.date = new Timestamp(System.currentTimeMillis());
        this.date.setNanos(0);
        this.message = message;
        this.details = details;
    }


}
