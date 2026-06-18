package com.NikhilCreates.practiceProject01.ExceptionHandler;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    String message;
    LocalDateTime time;
    String details;

    public ErrorResponse(LocalDateTime ti, String mes, String det)
    {
        this.message=mes;
        this.time=ti;
        this.details=det;
    }
}
