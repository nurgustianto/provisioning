package com.voxloud.provisioning.entity;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
public class Device {
    @Id
    private String macAddress;
    
    @Enumerated(EnumType.STRING)
    private DeviceModel model;
    
    private String username;
    private String password;
    
    @Column(length = 1000)
    private String overrideFragment;
    
    public enum DeviceModel {
        DESK,
        CONFERENCE
    }
}