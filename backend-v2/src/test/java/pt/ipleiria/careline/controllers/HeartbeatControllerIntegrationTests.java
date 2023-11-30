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
import pt.ipleiria.careline.domain.dto.data.HeartbeatDTO;
import pt.ipleiria.careline.domain.entities.data.HeartbeatEntity;
import pt.ipleiria.careline.services.HeartbeatService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class HeartbeatControllerIntegrationTests {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private HeartbeatService heartbeatService;

    @Autowired
    public HeartbeatControllerIntegrationTests(MockMvc mockMvc, HeartbeatService heartbeatService) {
        this.mockMvc = mockMvc;
        this.heartbeatService = heartbeatService;
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateHeartbeatReturnsHttpStatus201Created() throws Exception {
        HeartbeatDTO testHeartbeatDTO = TestDataUtil.createHeartbeatDTOA(null);
        testHeartbeatDTO.setCreatedAt(null);
        String createHeartbeatJSON = objectMapper.writeValueAsString(testHeartbeatDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/patients/1/heartbeats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createHeartbeatJSON)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateHeartbeatReturnsCreatedHeartbeat() throws Exception {
        HeartbeatDTO testHeartbeatDTO = TestDataUtil.createHeartbeatDTOA(null);
        testHeartbeatDTO.setCreatedAt(null);
        String createHeartbeatJSON = objectMapper.writeValueAsString(testHeartbeatDTO);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/patients/1/heartbeats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createHeartbeatJSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.heartbeat").value(testHeartbeatDTO.getHeartbeat())
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
    public void testThatListHeartbeatsReturnsHeartbeats() throws Exception {
        HeartbeatEntity testHeartbeatEntity = TestDataUtil.createHeartbeatEntityA(null);
        heartbeatService.create(null, testHeartbeatEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/patients/1/heartbeats")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].heartbeat").value(testHeartbeatEntity.getHeartbeat())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].createdAt").isNotEmpty()
        );
    }

    @Test
    public void testThatHeartbeatReturnsHttpStatus200WhenPatientExists() throws Exception {
        HeartbeatEntity testHeartbeatEntity = TestDataUtil.createHeartbeatEntityA(null);
        heartbeatService.create(null, testHeartbeatEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/patients/1/heartbeats/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatHeartbeatReturnsHttpStatus404WhenNoHeartbeatExists() throws Exception {
        HeartbeatEntity testHeartbeatEntity = TestDataUtil.createHeartbeatEntityA(null);
        heartbeatService.create(null, testHeartbeatEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/patients/1/heartbeats/0")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatHeartbeatReturnsHeartbeatWhenHeartbeatExists() throws Exception {
        HeartbeatEntity testHeartbeatEntity = TestDataUtil.createHeartbeatEntityA(null);
        heartbeatService.create(null, testHeartbeatEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/patients/1/heartbeats/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.heartbeat").value(testHeartbeatEntity.getHeartbeat())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.createdAt").isNotEmpty()
        );
    }

    @Test
    public void testThatDeleteHeartbeatReturnsHttpStatus204WhenHeartbeatExists() throws Exception {
        HeartbeatEntity testHeartbeatEntity = TestDataUtil.createHeartbeatEntityA(null);
        heartbeatService.create(null, testHeartbeatEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/patients/1/heartbeats/" + testHeartbeatEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

    @Test
    public void testThatDeleteHeartbeatReturnsHttpStatus404WhenHeartbeatNotExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/patients/1/heartbeats/0")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }
}
