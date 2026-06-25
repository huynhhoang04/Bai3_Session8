package com.example.appointmentservice.controller;

import com.example.appointmentservice.dto.ApiResponseError;
import com.example.appointmentservice.dto.AppointmentRequest;
import com.example.appointmentservice.model.Appointment;
import com.example.appointmentservice.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping
    public ResponseEntity<?> createAppointment(@RequestBody AppointmentRequest request) {
        try {
            // Verify patient existence via patient-service
            restTemplate.getForObject("http://patient-service/api/v1/patients/" + request.getPatientId(), Object.class);
            // Verify doctor existence via doctor-service
            restTemplate.getForObject("http://doctor-service/api/v1/doctors/" + request.getDoctorId(), Object.class);
        } catch (Exception ex) {
            ApiResponseError error = new ApiResponseError(
                    "ExternalServiceError",
                    "Failed to verify patient or doctor: " + ex.getMessage(),
                    DateTimeFormatter.ISO_INSTANT.format(Instant.now())
            );
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // Save appointment
        Appointment appointment = new Appointment();
        appointment.setPatientId(request.getPatientId());
        appointment.setDoctorId(request.getDoctorId());
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setReason(request.getReason());
        appointment.setStatus("PENDING");
        Appointment saved = appointmentRepository.save(appointment);
        return ResponseEntity.ok(saved);
    }
}
