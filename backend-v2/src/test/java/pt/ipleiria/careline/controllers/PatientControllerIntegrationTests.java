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
import pt.ipleiria.careline.services.PatientService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class PatientControllerIntegrationTests {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private PatientService patientService;

    @Autowired
    public PatientControllerIntegrationTests(MockMvc mockMvc, PatientService patientService) {
        this.mockMvc = mockMvc;
        this.patientService = patientService;
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

    @Test
    public void testThatListPatientsReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListPatientsReturnsListOfAuthors() throws Exception {
        PatientEntity testPatientA = TestDataUtil.createPatientEntityA();
        patientService.createPatient(testPatientA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value("Ivo Bispo")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].email").value("ivo.bispo@gmail.com")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].password").value("password")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].nus").value("123456789")
        );
    }
}
