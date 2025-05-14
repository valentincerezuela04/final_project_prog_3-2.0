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


    public static ErrorDetails from(String message, String details) {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        timestamp.setNanos(0);

        return ErrorDetails.builder()
                .date(timestamp)
                .message(message)
                .details(details)
                .build();
    }


}
