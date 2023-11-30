package pt.ipleiria.careline.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pt.ipleiria.careline.TestDataUtil;
import pt.ipleiria.careline.domain.dto.data.TemperatureDTO;
import pt.ipleiria.careline.domain.entities.data.TemperatureEntity;
import pt.ipleiria.careline.services.TemperatureService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class TemperatureControllerIntegrationTests {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private TemperatureService temperatureService;

    @Autowired
    public TemperatureControllerIntegrationTests(MockMvc mockMvc, TemperatureService temperatureService) {
        this.mockMvc = mockMvc;
        this.temperatureService = temperatureService;
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateTemperatureReturnsHttpStatus201Created() throws Exception {
        TemperatureDTO temperatureDTO = TestDataUtil.createTemperatureDTOA(null);
        temperatureDTO.setCreatedAt(null);
        String json = objectMapper.writeValueAsString(temperatureDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/patients/1/temperatures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateTemperatureReturnsCreatedTemperature() throws Exception {
        TemperatureDTO temperatureDTO = TestDataUtil.createTemperatureDTOA(null);
        temperatureDTO.setCreatedAt(null);
        String json = objectMapper.writeValueAsString(temperatureDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/patients/1/temperatures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.temperature").value(temperatureDTO.getTemperature())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.createdAt").isNotEmpty()
        );
    }

    @Test
    public void testThatListHeartbeatsReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/patients/1/heartbeats")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListTemperaturesReturnsTemperatures() throws Exception {
        TemperatureEntity temperatureEntity = TestDataUtil.createTemperatureEntityA(null);
        temperatureService.create(null, temperatureEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/patients/1/temperatures")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].heartbeat").value(temperatureEntity.getTemperature())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].createdAt").isNotEmpty()
        );
    }

    @Test
    public void testThatTemperatureReturnsHttpStatus200WhenTemperatureExists() throws Exception {
        TemperatureEntity temperatureEntity = TestDataUtil.createTemperatureEntityA(null);
        temperatureService.create(null, temperatureEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/patients/1/temperatures/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatTemperatureReturnsHttpStatus404WhenNoTemperatureExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/patients/1/temperatures/0")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatTemperatureReturnsHeartbeatWhenTemperatureExists() throws Exception {
        TemperatureEntity temperatureEntity = TestDataUtil.createTemperatureEntityA(null);
        temperatureService.create(null, temperatureEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/patients/1/temperatures/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.temperature").value(temperatureEntity.getTemperature())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.createdAt").isNotEmpty()
        );
    }

    @Test
    public void testThatDeleteTemperatureReturnsHttpStatus204WhenTemperatureExists() throws Exception {
        TemperatureEntity temperatureEntity = TestDataUtil.createTemperatureEntityA(null);
        temperatureService.create(null, temperatureEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/patients/1/temperatures/" + temperatureEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

    @Test
    public void testThatDeleteTemperaturesReturnsHttpStatus404WhenTemperaturesNotExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/patients/1/temperatures/0")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }
}
