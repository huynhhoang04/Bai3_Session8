package com.example.appointmentservice.controller;

import com.example.appointmentservice.dto.ApiResponseError;
import com.example.appointmentservice.dto.AppointmentRequest;
import com.example.appointmentservice.model.Appointment;
import com.example.appointmentservice.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<?> createAppointment(@RequestBody AppointmentRequest request) {
        try {
            Object result = appointmentService.createAppointment(request);
            if (result instanceof ApiResponseError) {
                return new ResponseEntity<>(result, HttpStatus.SERVICE_UNAVAILABLE);
            }
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            ApiResponseError error = new ApiResponseError(
                    "ServiceUnavailable",
                    "Hiện tại không thể kiểm tra thông tin bệnh nhân, vui lòng thử lại sau vài giây",
                    java.time.format.DateTimeFormatter.ISO_INSTANT.format(java.time.Instant.now())
            );
            return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
