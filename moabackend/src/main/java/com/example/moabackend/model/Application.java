package com.example.moabackend.model;

import java.io.Serializable;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Data // Lombok: generates getters, setters, toString, equals, hashCode
@Entity // JPA: maps this to a database table
public class Application implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String farmerDetails;    // JSON string or individual fields
    private String householdDetails;
    private String farmlandDetails;
    private String status;           // e.g., "PENDING", "APPROVED", "REJECTED"
    private String name;

    // Default constructor required by JPA
    public Application() {
    }

    // Optional: Custom constructor for convenience
    public Application(String name, String farmerDetails, String householdDetails, 
                       String farmlandDetails, String status) {
        this.name = name;
        this.farmerDetails = farmerDetails;
        this.householdDetails = householdDetails;
        this.farmlandDetails = farmlandDetails;
        this.status = status;
    }
}