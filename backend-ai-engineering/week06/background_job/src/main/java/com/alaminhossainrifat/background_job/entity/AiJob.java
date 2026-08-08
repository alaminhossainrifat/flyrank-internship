package com.alaminhossainrifat.background_job.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class AiJob {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String status;

    @Column(columnDefinition = "TEXT")
    private String result;
}