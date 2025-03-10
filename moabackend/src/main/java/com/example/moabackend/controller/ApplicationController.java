package com.example.moabackend.controller;

import com.example.moabackend.model.Application;
import com.example.moabackend.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
// Add these imports if you want logging
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/services/{serviceId}/applications")
public class ApplicationController {

    @Autowired
    private ApplicationRepository applicationRepository;

    // Logger (optional, included from your original standalone method)
    private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);

    // Receive application (POST /services/{serviceId}/applications)
    @PostMapping
    public ResponseEntity<Application> submitApplication(
            @PathVariable String serviceId,
            @RequestBody Application application) {
        logger.info("Received application: {}", application); // Optional logging
        application.setStatus("PENDING"); // Initial status
        Application savedApplication = applicationRepository.save(application);
        logger.info("Saved application: {}", savedApplication); // Optional logging
        return ResponseEntity.ok(savedApplication);
    }

    // List all applications for review (GET /services/{serviceId}/applications)
    @GetMapping
    public ResponseEntity<List<Application>> getAllApplications(@PathVariable String serviceId) {
        List<Application> applications = applicationRepository.findAll();
        return ResponseEntity.ok(applications);
    }

    // Approve or reject application (PUT /services/{serviceId}/applications/{id})
    @PutMapping("/{id}")
    public ResponseEntity<Application> updateApplicationStatus(
            @PathVariable String serviceId,
            @PathVariable Long id,
            @RequestBody String status) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        application.setStatus(status.toUpperCase()); // e.g., "APPROVED" or "REJECTED"
        Application updatedApplication = applicationRepository.save(application);

        // If approved, send to Farmland Registry (Step 3)
        if ("APPROVED".equals(status.toUpperCase())) {
            sendToFarmlandRegistry(updatedApplication);
        }
        return ResponseEntity.ok(updatedApplication);
    }

    // Placeholder for sending to Farmland Registry
    private void sendToFarmlandRegistry(Application application) {
  RestTemplate restTemplate = new RestTemplate();
  String url = "http://localhost:8040/api/v1/entries";
  restTemplate.postForObject(url, application, String.class);
  System.out.println("Sent to Farmland Registry: " + application);
}
}