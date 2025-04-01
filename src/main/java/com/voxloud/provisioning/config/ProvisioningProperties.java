package com.voxloud.provisioning.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "provisioning")
public class ProvisioningProperties {
    private String domain;
    private String port;
    private String codecs;
}