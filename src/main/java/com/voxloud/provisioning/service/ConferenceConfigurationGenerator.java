package com.voxloud.provisioning.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.stereotype.Component;

@Component
public class ConferenceConfigurationGenerator implements ConfigurationGenerator {
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public String generateConfiguration(Device device, ProvisioningProperties props) {
        try {
            ObjectNode config = objectMapper.createObjectNode();
            config.put("username", device.getUsername());
            config.put("password", device.getPassword());
            config.put("domain", props.getDomain());
            config.put("port", props.getPort());
            
            ArrayNode codecs = config.putArray("codecs");
            for (String codec : props.getCodecs().split(",")) {
                codecs.add(codec.trim());
            }
            
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(config);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate configuration", e);
        }
    }
    
    @Override
    public String mergeWithOverride(String baseConfig, String overrideFragment) {
        if (overrideFragment == null || overrideFragment.trim().isEmpty()) {
            return baseConfig;
        }
        
        try {
            ObjectNode base = (ObjectNode) objectMapper.readTree(baseConfig);
            ObjectNode override = (ObjectNode) objectMapper.readTree(overrideFragment);
            
            override.fields().forEachRemaining(entry -> 
                base.set(entry.getKey(), entry.getValue()));
                
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(base);
        } catch (Exception e) {
            throw new RuntimeException("Failed to merge configurations", e);
        }
    }
}