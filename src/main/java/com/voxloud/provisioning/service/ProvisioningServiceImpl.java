package com.voxloud.provisioning.service;

import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.repository.DeviceRepository;
import com.voxloud.provisioning.config.ProvisioningProperties;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class ProvisioningServiceImpl extends ProvisioningService {
    
    private final DeviceRepository deviceRepository;
    private final ProvisioningProperties properties;
    private final Map<Device.DeviceModel, ConfigurationGenerator> generators;
    
    public ProvisioningServiceImpl(
            DeviceRepository deviceRepository,
            ProvisioningProperties properties,
            DeskConfigurationGenerator deskGenerator,
            ConferenceConfigurationGenerator conferenceGenerator) {
        this.deviceRepository = deviceRepository;
        this.properties = properties;
        this.generators = Map.of(
            Device.DeviceModel.DESK, deskGenerator,
            Device.DeviceModel.CONFERENCE, conferenceGenerator
        );
    }
    
    public String getConfiguration(String macAddress) {
        Device device = deviceRepository.findById(macAddress)
            .orElseThrow(() -> new IllegalArgumentException("Device not found"));
            
        ConfigurationGenerator generator = generators.get(device.getModel());
        if (generator == null) {
            throw new IllegalArgumentException("Unsupported device model: " + device.getModel());
        }
        
        String baseConfig = generator.generateConfiguration(device, properties);
        return generator.mergeWithOverride(baseConfig, device.getOverrideFragment());
    }
}