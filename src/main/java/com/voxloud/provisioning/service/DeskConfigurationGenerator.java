package com.voxloud.provisioning.service;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Properties;

import org.springframework.stereotype.Component;

import com.voxloud.provisioning.config.ProvisioningProperties;
import com.voxloud.provisioning.entity.Device;

@Component
public class DeskConfigurationGenerator implements ConfigurationGenerator {
    
    public String generateConfiguration(Device device, ProvisioningProperties props) {
        return String.format(
            "username=%s\n" +
            "password=%s\n" +
            "domain=%s\n" +
            "port=%s\n" +
            "codecs=%s",
            device.getUsername(),
            device.getPassword(),
            props.getDomain(),
            props.getPort(),
            props.getCodecs()
        );
    }
    
    @Override
    public String mergeWithOverride(String baseConfig, String overrideFragment) {
        if (overrideFragment == null || overrideFragment.trim().isEmpty()) {
            return baseConfig;
        }
        
        Properties base = new Properties();
        Properties override = new Properties();
        try {
            base.load(new StringReader(baseConfig));
            override.load(new StringReader(overrideFragment));
            
            // Merge properties
            base.putAll(override);
            
            // Write back to string
            StringWriter writer = new StringWriter();
            base.store(writer, null);
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to merge configurations", e);
        }
    }

    public String generateConfiguration(Device device, Object props) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}