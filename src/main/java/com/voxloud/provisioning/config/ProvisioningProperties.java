package com.voxloud.provisioning.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "provisioning")
public class ProvisioningProperties {
    private String domain;

    // Getter for domain
    public String getDomain() {
        return domain;
    }

    // Setter for domain
    public void setDomain(String domain) {
        this.domain = domain;
    }
    private String port;
    // Getter for     private String port;

    public String getPort() {
        return port;
    }

    // Setter for port
    public void setPort(String port) {
        this.port = port;
    }
    private String codecs;

    public String getCodecs() {
        return codecs;
    }

    public void setCodecs(String codecs) {
        this.codecs = codecs;
    }

}