package com.voxloud.provisioning.entity;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
public class Device {
    @Id
    private String macAddress;

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
    
    @Enumerated(EnumType.STRING)
    private DeviceModel model;
    
    private String username;
    private String password;
    
    @Column(length = 1000)
    private String overrideFragment;
    
    // Getter for username
    public String getUsername() {
        return username;
    }

    // Setter for username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }

    // Setter for password
    public void setPassword(String password) {
        this.password = password;
    }

    public Object getModel() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getOverrideFragment() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public enum DeviceModel {
        DESK,
        CONFERENCE
    }


    public void setModel(DeviceModel model) {
        this.model = model;
    }
  
}