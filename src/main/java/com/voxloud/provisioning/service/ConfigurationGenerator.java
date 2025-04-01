package com.voxloud.provisioning.service;

public interface ConfigurationGenerator {
    String generateConfiguration(Device device, ProvisioningProperties props);
    String mergeWithOverride(String baseConfig, String overrideFragment);
}