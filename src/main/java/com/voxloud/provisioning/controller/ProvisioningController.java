package com.voxloud.provisioning.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.voxloud.provisioning.service.ProvisioningService;

@RestController
@RequestMapping("/api/v1")
public class ProvisioningController {

    private final ProvisioningService provisioningService;

    public ProvisioningController(ProvisioningService provisioningService) {
        this.provisioningService = provisioningService;
    }

    @GetMapping("/provisioning/{macAddress}")
    public ResponseEntity<String> getDeviceConfiguration(@PathVariable String macAddress) {
        try {
            String configuration = provisioningService.getConfiguration(macAddress);
            return ResponseEntity.ok(configuration);
        } catch (IllegalArgumentException e) {
            // Return 404 when device is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Return 500 for other unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}