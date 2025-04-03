package com.voxloud.provisioning.service;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.voxloud.provisioning.config.ProvisioningProperties;
import com.voxloud.provisioning.entity.Device;

@Component
public class ConferenceConfigurationGenerator implements ConfigurationGenerator {
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public String generateConfiguration(Object device, Object props) {
        try {
            ObjectNode config = objectMapper.createObjectNode();
            Device castedDevice = (Device) device;
            config.put("username", castedDevice.getUsername());
            config.put("password", castedDevice.getPassword());
            MyPropsType castedProps = (MyPropsType) props;
            config.put("domain", castedProps.getDomain());
            config.put("port", castedProps.getPort());
            
            ArrayNode codecs = config.putArray("codecs");
            for (String codec : ((String) castedProps.getCodecs()).split(",")) {
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

    public String generateConfiguration(Device device, Object props) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String generateConfiguration(Device device, ProvisioningProperties props) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private static class MyPropsType {

        public MyPropsType() {
        }

        private JsonNode getDomain() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        private JsonNode getPort() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        private Object getCodecs() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}