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
        return ErrorDetails.builder()
                .date(new Timestamp(System.currentTimeMillis()))
                .message(message)
                .details(details)
                .build();
    }


}
