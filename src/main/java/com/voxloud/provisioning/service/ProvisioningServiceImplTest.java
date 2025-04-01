package com.voxloud.provisioning.service;

import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.repository.DeviceRepository;
import com.voxloud.provisioning.config.ProvisioningProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;

class ProvisioningServiceImplTest {
    
    @Mock
    private DeviceRepository deviceRepository;
    
    private ProvisioningProperties properties;
    private ProvisioningServiceImpl service;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        properties = new ProvisioningProperties();
        properties.setDomain("sip.voxloud.com");
        properties.setPort("5060");
        properties.setCodecs("G711,G729,OPUS");
        
        service = new ProvisioningServiceImpl(
            deviceRepository,
            properties,
            new DeskConfigurationGenerator(),
            new ConferenceConfigurationGenerator()
        );
    }
    
    @Test
    void getConfiguration_DeskPhone_Success() {
        Device device = new Device();
        device.setMacAddress("aa-bb-cc-dd-ee-ff");
        device.setModel(Device.DeviceModel.DESK);
        device.setUsername("john");
        device.setPassword("doe");
        
        when(deviceRepository.findById("aa-bb-cc-dd-ee-ff"))
            .thenReturn(Optional.of(device));
            
        String config = service.getConfiguration("aa-bb-cc-dd-ee-ff");
        
        assertTrue(config.contains("username=john"));
        assertTrue(config.contains("password=doe"));
        assertTrue(config.contains("domain=sip.voxloud.com"));
    }
    
    @Test
    void getConfiguration_NotFound_ThrowsException() {
        when(deviceRepository.findById(anyString()))
            .thenReturn(Optional.empty());
            
        assertThrows(IllegalArgumentException.class, () ->
            service.getConfiguration("not-found"));
    }
}