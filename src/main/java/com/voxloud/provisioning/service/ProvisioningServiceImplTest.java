package com.voxloud.provisioning.service;

import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.repository.DeviceRepository;
import com.voxloud.provisioning.config.ProvisioningProperties;
import java.util.Optional;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;


class ProvisioningServiceImplTest {
    
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

    private void assertTrue(boolean contains) {
        throw new UnsupportedOperationException("Not supported yet.");
}   // Removed the custom 'when' method as it is replaced by Mockito's 'when'.
    }
