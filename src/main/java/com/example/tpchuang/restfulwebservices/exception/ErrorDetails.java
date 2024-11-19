package com.example.tpchuang.restfulwebservices.exception;

import java.time.LocalDateTime;

/**
 * Represents details about an error of the Web Application.
 */
public record ErrorDetails(LocalDateTime timestamp, String message, String details) {

}
