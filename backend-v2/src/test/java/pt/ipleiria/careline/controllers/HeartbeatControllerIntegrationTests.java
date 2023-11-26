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
import pt.ipleiria.careline.domain.dto.HeartbeatDTO;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class HeartbeatControllerIntegrationTests {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public HeartbeatControllerIntegrationTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateBookReturnsHttpStatus201Created() throws Exception {
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
    public void testThatCreateBookReturnsCreatedHeartbeat() throws Exception {
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
}
