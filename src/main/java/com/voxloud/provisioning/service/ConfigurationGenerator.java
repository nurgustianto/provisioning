package com.voxloud.provisioning.service;

import com.voxloud.provisioning.config.ProvisioningProperties;
import com.voxloud.provisioning.entity.Device;

public interface ConfigurationGenerator {
    String generateConfiguration(Device device, ProvisioningProperties props);
    String mergeWithOverride(String baseConfig, String overrideFragment);
}