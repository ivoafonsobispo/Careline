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
import pt.ipleiria.careline.domain.entities.users.PatientEntity;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class PatientControllerIntegrationTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public PatientControllerIntegrationTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreatePatientSuccessfullyReturnsHttp201Created() throws Exception {
        PatientEntity testPatientA = TestDataUtil.createPatientEntityA();
        testPatientA.setId(null);
        String patientJson = objectMapper.writeValueAsString(testPatientA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patientJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreatePatientSuccessfullyReturnsSavedAuthor() throws Exception {
        PatientEntity testPatientA = TestDataUtil.createPatientEntityA();
        testPatientA.setId(null);
        String patientJson = objectMapper.writeValueAsString(testPatientA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patientJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Ivo Bispo")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value("ivo.bispo@gmail.com")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.password").value("password")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.nus").value("123456789")
        );
    }
}
