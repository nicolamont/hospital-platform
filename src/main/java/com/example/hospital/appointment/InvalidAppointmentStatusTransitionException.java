package com.example.hospital.appointment;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InvalidAppointmentStatusTransitionException
    extends RuntimeException {

    public InvalidAppointmentStatusTransitionException(
        AppointmentStatus currentStatus,
        AppointmentStatus newStatus
    ) {
        super("Invalid status transition from "
            + currentStatus + " to " + newStatus);
    }

}
